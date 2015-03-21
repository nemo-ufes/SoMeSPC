package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 

public class GoAddElementoMensuravelAction extends GoAddElementsToCollectionAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		super.execute(); 
		
		String id = getPreviousView().getValueString("id");
		String idTipoDeEntidadeMensuravel = getPreviousView().getValueString("tipoDeEntidadeMensuravel.id"); 

		getTab().setBaseCondition( 
			"'" + idTipoDeEntidadeMensuravel + "' IN (SELECT id from ${tipoDeEntidadeMensuravel}) AND '" + id + "' NOT IN (SELECT id from ${entidadeMensuravel})" 
		);
	}
	
}
