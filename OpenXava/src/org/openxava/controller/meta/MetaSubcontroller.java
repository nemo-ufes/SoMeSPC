package org.openxava.controller.meta;

import java.util.*;

import org.openxava.util.*;

/**
 * 
 * Create on 25/04/2013 (08:30:35)
 * @author Ana Andres
 */
public class MetaSubcontroller {
	
	private String image;
	private String controllerName;
	private String mode;

	public boolean hasActionsInThisMode(String mode){
		Collection<MetaAction> actions = getMetaActions();
		for (MetaAction action : actions){
			if (action.appliesToMode(mode)) return true;
		}
		return false;
	}
	
	public boolean appliesToMode(String mode) {
		if ("NONE".equals(getMode())) return false;
		return Is.emptyString(getMode()) || getMode().equals(mode);
	}
	
	public Collection<MetaAction> getMetaActions(){
		MetaController mc = MetaControllers.getMetaController(getControllerName());
		return mc.getMetaActions();
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
