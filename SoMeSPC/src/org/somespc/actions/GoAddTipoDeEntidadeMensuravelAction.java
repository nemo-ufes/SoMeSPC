package org.somespc.actions;

import org.openxava.actions.GoAddElementsToCollectionAction;

public class GoAddTipoDeEntidadeMensuravelAction extends GoAddElementsToCollectionAction{
    public void execute() throws Exception
    {
	super.execute();
	
	Integer idElementoMensuravel = getPreviousView().getValueInt("elementoMensuravel.id");
	
	System.out.println(idElementoMensuravel);

	getTab().setBaseCondition("${id} IN (SELECT t.id FROM TipoDeEntidadeMensuravel t JOIN t.elementoMensuravel em " +
			" WHERE em.id = " + idElementoMensuravel + ")");
			//" WHERE t.elementoMensuravel.id = " + idElementoMensuravel +")");
    }

}