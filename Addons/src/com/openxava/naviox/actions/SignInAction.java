package com.openxava.naviox.actions;

import org.openxava.actions.*;
import org.openxava.util.*;

import com.openxava.naviox.impl.*;

/**
 * 
 * @author Javier Paniza 
 */
public class SignInAction extends ViewBaseAction implements IForwardAction {
	
	private String forwardURI = null;

	public void execute() throws Exception {		
		SignInHelper.init(getRequest(), getView()); 
		String userName = getView().getValueString("usuario");
		String password = getView().getValueString("senha");
		if (Is.emptyString(userName, password)) {
			addError("unauthorized_user"); 
			return;
		}		
		if (!SignInHelper.isAuthorized(userName, password)) {
			addError("unauthorized_user"); 
			return;									
		}
		SignInHelper.signIn(getRequest().getSession(), userName);
		getView().reset();
		String originalURI = getRequest().getParameter("originalURI");
		if (originalURI == null) {
			forwardURI = "/naviox/painel.jsp";
		}
		else {
			int idx = originalURI.indexOf("/", 1);			
			if (!originalURI.endsWith("/SignIn") && idx > 0 && idx < originalURI.length()) {
				forwardURI = originalURI.substring(idx);
			}
			else {
				forwardURI = "/naviox/painel.jsp";
			}
		}
		forwardURI = SignInHelper.refineForwardURI(getRequest(), forwardURI); 
	}
	
	public String getForwardURI() {
		return forwardURI;
	}

	public boolean inNewWindow() {
		return false;
	}

}
