package org.openxava.actions;




/**
 * @author Javier Paniza
 */

public class SearchAction extends ViewBaseAction implements IChainAction {

	
	
	public void execute() throws Exception {
		getView().refresh();
	}
	
	public String getNextAction() throws Exception {
		return getEnvironment().getValue("XAVA_SEARCH_ACTION");		
	} 		

}
