package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;

//import static org.openxava.jpa.XPersistence.*;

public class SearchAnaliseDeMedicaoForCEPAction extends ReferenceSearchAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		
		super.execute(); 
		
		String idMedida = getPreviousView().getValueString("medida.id");
		
		if(idMedida != null && idMedida.isEmpty() == false)
		{
			if(idMedida != null && idMedida.isEmpty() == false)
			{
				getTab().setBaseCondition( 
					"'" + idMedida + "' IN (SELECT medida.id from ${medidaPlanoDeMedicao}) " 
				);
			}
			return;
		}
		
		throw new Exception("Para selecionar uma Análise primeiro selecione o processo padrão e a medida. Retorne a tela anterior de cadastro.");
		
	}
	
}