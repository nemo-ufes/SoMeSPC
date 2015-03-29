package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 

public class GoAddAtividadeInstanciadaAction extends GoAddElementsToCollectionAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		super.execute(); 
		
		String id = getPreviousView().getValueString("id");
		String idProcessoPadrao = getPreviousView().getValueString("baseadoEm.id");

		getTab().setBaseCondition( 
			"'" + idProcessoPadrao + "'" + " IN (SELECT p.id from ProcessoPadrao p JOIN p.atividadePadrao a WHERE a.id = ${baseadoEm.id}) AND ${id} NOT IN (SELECT aa.id from ProcessoInstanciado pp JOIN pp.atividadeInstanciada aa WHERE pp.id = '"+ id +"')"
		);
	}
	
}