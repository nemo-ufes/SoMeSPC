package org.medcep.actions;

import org.openxava.actions.GoAddElementsToCollectionAction;

public class GoAddProcessoPadraoAction extends GoAddElementsToCollectionAction
{
    public void execute() throws Exception
    {
		super.execute();
		
		getCollectionElementView().setTitle("Adicionar Atividade Padrão");
	
		getTab().setBaseCondition(
				"${id} IN (SELECT id FROM AtividadePadrao)"
		);
    }

}