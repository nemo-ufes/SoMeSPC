package org.medcep.actions;

import org.openxava.actions.GoAddElementsToCollectionAction;

public class GoAddAtividadePadraoAction extends GoAddElementsToCollectionAction{
	
	public void execute() throws Exception {
		super.execute();
		getCollectionElementView().setTitle("Adicionar Subatividade a coleção");
		getTab().setBaseCondition(
			"${id} IN (SELECT id FROM AtividadePadrao)"
		);
	}

}