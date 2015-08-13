package org.somespc.actions;

import org.openxava.actions.GoAddElementsToCollectionAction;

public class GoAddAtividadeProjetoAction extends GoAddElementsToCollectionAction{
	
	public void execute() throws Exception {
		super.execute();
		getCollectionElementView().setTitle("Adicionar Subatividade a coleção");
		getTab().setBaseCondition(
			"${id} IN (SELECT id FROM AtividadeProjeto)"
		);
	}

}