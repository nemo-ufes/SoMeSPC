package org.medcep.actions;

import org.openxava.actions.GoAddElementsToCollectionAction;

public class GoAddNecessidadeDeInformacao extends GoAddElementsToCollectionAction{
	
	public void execute() throws Exception {
		super.execute(); 
			
		getTab().setBaseCondition(
			"${id} IN (SELECT id FROM NecessidadeDeInformacao)"
		);
	}

}
