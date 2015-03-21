package org.openxava.web.dwr;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

/**
 * For accessing to the Tab from DWR. <p>
 * 
 * @author Javier Paniza
 */

public class Tab extends DWRBase {
	
	private static class TableId { // Table means HTML TABLE
		
		private String application;
		private String module;
		private String tabObject;
		private String collection;
		private boolean valid;
		
		public TableId(String tableId, int additionalTokens) {
			String [] id = tableId.split("_+");
			if (!"ox".equals(id[0])) {
				// Bad format. This method relies in the id format by Ids class
				valid = false; 
				return;
			}
			application = id[1];
			module = id[2];
			StringBuffer collectionSB = new StringBuffer();
			for (int i=3; i<id.length-additionalTokens; i++) { // To work with collections inside @AsEmbedded references 
				if (i>3) collectionSB.append("_");
				collectionSB.append(id[i]);				 
			}
			this.collection = collectionSB.toString();
			tabObject = "list".equals(collection)?"xava_tab":"xava_collectionTab_" + collection;									
			valid = true;
		}

		public String getApplication() {
			return application;
		}

		public String getModule() {
			return module;
		}

		public String getTabObject() {
			return tabObject;
		}
		
		public String getCollection() {
			return collection;
		}
		
		public boolean isValid() {
			return valid;
		}
		
	}
	
	private static Log log = LogFactory.getLog(Tab.class);

	public static void setFilterVisible(HttpServletRequest request, String application, String module, boolean filterVisible, String tabObject) {
		checkSecurity(request, application, module);
		Users.setCurrent(request);
		org.openxava.tab.Tab tab = getTab(request, application, module, tabObject); 
		tab.setFilterVisible(filterVisible);
	}
	
	public static void removeProperty(HttpServletRequest request, String application, String module, String property, String tabObject) {  
		checkSecurity(request, application, module);
		Users.setCurrent(request);
		org.openxava.tab.Tab tab = getTab(request, application, module, tabObject);
		tab.removeProperty(property);
	}
	
	/**
	 * 
	 * @since 5.2
	 */
	public static void moveProperty(HttpServletRequest request, String tableId, int from, int to) {
		TableId id = new TableId(tableId, 0);
		if (!id.isValid()) {
			log.warn(XavaResources.getString("impossible_store_column_movement"));  
			return;			
		}
		checkSecurity(request, id.getApplication(), id.getModule());
		Users.setCurrent(request);		
		org.openxava.tab.Tab tab = getTab(request, id.getApplication(), id.getModule(), id.getTabObject());		
		tab.moveProperty(from, to);
	}
	
	public static void setColumnWidth(HttpServletRequest request, String columnId, int index, int width) {
		try {
			TableId id = new TableId(columnId, 1);
			if (!id.isValid()) {
				log.warn(XavaResources.getString("impossible_store_column_width"));  
				return;			
			}
			checkSecurity(request, id.getApplication(), id.getModule());
			Users.setCurrent(request);
			try {
				org.openxava.tab.Tab tab = getTab(request, id.getApplication(), id.getModule(), id.getTabObject()); 
				tab.setColumnWidth(index, width);
			}
			catch (ElementNotFoundException ex) { 
				// If it has not tab maybe it's a calculated collection
				org.openxava.view.View view = (org.openxava.view.View) getContext(request).get(id.getApplication(), id.getModule(), "xava_view");
				org.openxava.view.View collectionView = view.getSubview(id.getCollection());
				if (collectionView.isCollectionCalculated() || collectionView.isRepresentsElementCollection()) {
					String column=columnId.substring(columnId.lastIndexOf("_col") + 4);
					int columnIndex = Integer.parseInt(column);
					collectionView.setCollectionColumnWidth(columnIndex, width);
				}
				else {
					collectionView.setCollectionColumnWidth(index, width);	
				}
				
				
			}
		}
		catch (Exception ex) {
			log.warn(XavaResources.getString("impossible_store_column_width"), ex);
		}		
	}
 
	private static org.openxava.tab.Tab getTab(HttpServletRequest request, String application, String module, String tabObject) {
		org.openxava.tab.Tab tab = (org.openxava.tab.Tab)		
		  getContext(request).get(application, module, tabObject);
		request.setAttribute("xava.application", application);
		request.setAttribute("xava.module", module);
		tab.setRequest(request);
		return tab;
	}
	
}
