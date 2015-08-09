package org.medcep.actions;

import org.openxava.actions.GoAddElementsToCollectionAction;

public class GoAddProcessoPadraoActionSimple extends GoAddElementsToCollectionAction{
	
	public void execute() throws Exception {
		super.execute(); 
		getCollectionElementView().setTitle("Adicionar Subprocesso a coleção");
		getTab().setBaseCondition(
			"${id} IN (SELECT id FROM ProcessoPadrao)"
		);
	}

}
