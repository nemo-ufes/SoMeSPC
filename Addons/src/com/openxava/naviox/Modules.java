package com.openxava.naviox;

import java.io.*;
import java.util.*;
import java.util.prefs.*;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.openxava.application.meta.*;
import org.openxava.util.*;

import com.openxava.naviox.impl.*;
import com.openxava.naviox.model.*;
import com.openxava.naviox.util.*;

/**
 * 
 * @author Javier Paniza 
 */
public class Modules implements Serializable {
	
	public final static String FIRST_STEPS = "FirstSteps";
	
	private static Log log = LogFactory.getLog(Modules.class);
	private final static int MODULES_ON_TOP = 20; 
	private final static int BOOKMARK_MODULES = 100;
	private final static ModuleComparator comparator = new ModuleComparator();
	private static String preferencesNodeName = null; 
	private List<MetaModule> all;
	private List<MetaModule> topModules = null;
	private List<MetaModule> bookmarkModules = null; 
	
	private MetaModule current; 

	public static void init(String applicationName) {
		MetaModuleFactory.setApplication(applicationName);
		DB.init();
		createFirstStepsModule(applicationName);  
	}	
	
	private static void createFirstStepsModule(String applicationName) {
		MetaApplication app = MetaApplications.getMetaApplication(applicationName);
		MetaModule firstStepsModule = new MetaModule();
		firstStepsModule.setName(FIRST_STEPS); 
		firstStepsModule.setModelName("SignIn"); // The model does not matter
		firstStepsModule.setWebViewURL("/naviox/firstSteps.jsp");
		firstStepsModule.setModeControllerName("Void");
		app.addMetaModule(firstStepsModule);		
	}
	
	public void reset() {
		all = null;
		topModules = null;
		bookmarkModules = null; 
		current = null; 		
	}
	
	public boolean hasModules() { 
		return !getAll().isEmpty();
	}
	
	private MetaModule createWelcomeModule(MetaApplication app) {
		MetaModule result = new MetaModule();
		result.setName("Welcome");				
		result.setWebViewURL("naviox/welcome");		
		return result;
	}


	public void setCurrent(String application, String module, boolean retainOrder) { 
		this.current = MetaModuleFactory.create(application, module);
		if (topModules == null) loadTopModules();	
		int idx = indexOf(topModules, current); 
		if (idx < 0) {
			if (topModules.size() >= MODULES_ON_TOP) {
				topModules.remove(topModules.size() - 1); 
			}				
			topModules.add(0, current);
		}		
		else if (!retainOrder) {
			topModules.remove(idx);
			topModules.add(0, current);
		}
		storeTopModules();
	}
		

	public String getCurrent() {
		try {
			return getPreferences().get("current", FIRST_STEPS);
		}
		catch (Exception ex) {
			log.warn(XavaResources.getString("current_module_problem"), ex); 
			return FIRST_STEPS;
		}
	}
	
	public String getCurrentModuleDescription(HttpServletRequest request) { 
		try {
			String organization = Organizations.getCurrentName(request); 																				
			String prefix = organization == null?"":organization + " - ";																				
			return prefix + current.getMetaApplication().getLabel() + " - " +  current.getLabel(); 
		}
		catch (Exception ex) {
			log.warn(XavaResources.getString("module_description_problem"), ex);			
			return XavaResources.getString("unknow_module");
		}
	}
	
	public void bookmarkCurrentModule() { 
		if (bookmarkModules == null) loadBookmarkModules();	
		int idx = indexOf(bookmarkModules, current); 
		if (idx < 0) {
			bookmarkModules.add(current);
		}		
		storeBookmarkModules();
	}
	
	public void unbookmarkCurrentModule() { 
		if (bookmarkModules == null) loadBookmarkModules();	
		int idx = indexOf(bookmarkModules, current); 
		if (idx >= 0) {
			bookmarkModules.remove(idx);
		}		
		storeBookmarkModules();
	}
	
	public boolean isCurrentBookmarked() {
		return isBookmarked(current);
	}
	
	public boolean isBookmarked(MetaModule module) {
		if (bookmarkModules == null) loadBookmarkModules();
		return indexOf(bookmarkModules, module) >= 0;
	}
	
	private void loadTopModules() {
		topModules = loadModulesFromPreferences("", MODULES_ON_TOP);
	}
	
	private void loadBookmarkModules() {  
		bookmarkModules = loadModulesFromPreferences("bookmark.", BOOKMARK_MODULES);  
	}
	
	private List<MetaModule> loadModulesFromPreferences(String prefix, int limit) {
		List<MetaModule> modules = new ArrayList<MetaModule>(); 
		try {
			Preferences preferences = getPreferences();
			for (int i = 0; i < limit; i++) { 
				String applicationName = preferences.get(prefix + "application." + i, null);
				if (applicationName == null) break;
				String moduleName = preferences.get(prefix + "module." + i, null);
				if (moduleName == null) break;				
				try {
					MetaModule module = MetaModuleFactory.create(applicationName, moduleName);
					if (isModuleAuthorized(module)) {
						modules.add(module);
					}
				}
				catch (Exception ex) {					
					log.warn(XavaResources.getString("module_not_loaded", moduleName, applicationName), ex); 
				}							
			}		
		}
		catch (Exception ex) {
			log.warn(XavaResources.getString("loading_modules_problem"), ex); 
		}
		return modules;
	}
	
				
	public boolean isModuleAuthorized(HttpServletRequest request) {
		try {
			if (request.getRequestURI().contains("module.jsp")) return false;
			if (Users.getCurrent() == null && request.getRequestURI().contains("/phone/")) return false; 
			if (!(request.getRequestURI().startsWith(request.getContextPath() + "/m/") ||
					request.getRequestURI().startsWith(request.getContextPath() + "/p/") || 
					request.getRequestURI().startsWith(request.getContextPath() + "/modules/"))) return true;
			String [] uri = request.getRequestURI().split("/");
			if (uri.length < 4) return false;			
			return isModuleAuthorized(MetaModuleFactory.create(uri[1], uri[3]));
		}
		catch (Exception ex) {			
			log.warn(XavaResources.getString("module_not_authorized"), ex); 
			return false;
		}
			
	}
	
	public String getModuleURI(HttpServletRequest request, MetaModule module) { 
		String organization = Organizations.getCurrent(request); 
		String prefix = organization == null?"":"/o/" + organization;
		return "/" + module.getMetaApplication().getName() + prefix + "/m/" + module.getName();
	}
	
	boolean isModuleAuthorized(MetaModule module) {
		if (module.getName().equals(FIRST_STEPS)) return true; 
		return Collections.binarySearch(getAll(), module, comparator) >= 0;
	}

	private void storeTopModules() {
		storeModulesInPreferences(topModules, "", MODULES_ON_TOP, true);
	}
	
	private void storeBookmarkModules() { 
		storeModulesInPreferences(bookmarkModules, "bookmark.", BOOKMARK_MODULES, false); 
	}
	
	private void storeModulesInPreferences(Collection<MetaModule> modules, String prefix, int limit, boolean storeCurrent) { 
		try {			
			Preferences preferences = getPreferences();
			int i=0;
			for (MetaModule module: modules) {				
				preferences.put(prefix + "application." + i, module.getMetaApplication().getName());
				preferences.put(prefix + "module." + i, module.getName());
				i++;
			}
			for (; i < limit; i++) {				
				preferences.remove(prefix + "application." + i);
				preferences.remove(prefix + "module." + i);
			}
			if (storeCurrent && !"SignIn".equals(current.getName())) {
				preferences.put("current", current.getName());
			}
			
			preferences.flush();
		}
		catch (Exception ex) {
			log.warn(XavaResources.getString("storing_modules_problem"), ex);  
		}
	}


	private Preferences getPreferences() throws BackingStoreException { 
		return Users.getCurrentPreferences().node(getPreferencesNodeName());
	}
	
	private static String getPreferencesNodeName() { 
		if (preferencesNodeName == null) {
			Collection<MetaApplication> apps = MetaApplications.getMetaApplications();
			for (MetaApplication app: apps) {
				preferencesNodeName = "naviox." + app.getName();
				break;
			}
			if (preferencesNodeName == null) preferencesNodeName = "naviox.UNKNOWN"; 
		}
		return preferencesNodeName;
	}

	public Collection getTopModules() {
		return topModules;	
	}
	
	
	public Collection getBookmarkModules() { 
		if (bookmarkModules == null) loadBookmarkModules();
		return bookmarkModules;
	}
	
	public List getAll() {
		if (all == null) {			
			all = ModulesProvider.getAll();
			Collections.sort(all, comparator);
		}
		return all;
	}

	private int indexOf(Collection<MetaModule> topModules, MetaModule current) { 
		int idx = 0;
		for (MetaModule module: topModules) {
			if (module.getName().equals(current.getName()) &&
					module.getMetaApplication().getName().equals(current.getMetaApplication().getName())) return idx;
			idx++;
		}
		return -1;
	}
	
	
	private static class ModuleComparator implements Comparator<MetaModule> {

		public int compare(MetaModule a, MetaModule b) {			
			return a.getName().compareTo(b.getName());
		}
		
	}

}
