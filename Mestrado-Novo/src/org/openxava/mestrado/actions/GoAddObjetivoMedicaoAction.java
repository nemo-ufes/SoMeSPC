package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 

public class GoAddObjetivoMedicaoAction extends GoAddElementsToCollectionAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		super.execute(); 
		
		//String id = getPreviousView().getValueString("id");
		//String idTipoDeEntidadeMensuravel = getPreviousView().getValueString("tipoDeEntidadeMensuravel.id"); 

		getTab().setBaseCondition(
			"${id} IN (SELECT id FROM ObjetivoDeMedicao)"
		);
	}
	
}
