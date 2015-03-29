package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;

//import static org.openxava.jpa.XPersistence.*;

public class SearchMedidaAction extends ReferenceSearchAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		
		super.execute(); 
		
		String idProcessoPadrao = getPreviousView().getValueString("processoPadrao.id");
		
		if(idProcessoPadrao != null && idProcessoPadrao.isEmpty() == false)
		{
			if(idProcessoPadrao != null && idProcessoPadrao.isEmpty() == false)
			{
				getTab().setBaseCondition( 
					"${id} IN (SELECT m.id FROM Medida m " +
												"JOIN m.elementoMensuravel el " +
												"JOIN el.entidadeMensuravel en " +
								"WHERE en.id = '" + idProcessoPadrao + "') " 
				);
			}
			return;
		}
		
		throw new Exception("Para selecionar a medida escolha primeiro o processo padrão.");
		
	}
	
}