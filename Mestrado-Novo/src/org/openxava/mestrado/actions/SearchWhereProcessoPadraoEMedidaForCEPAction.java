package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.view.*;

//import static org.openxava.jpa.XPersistence.*;

public class SearchWhereProcessoPadraoEMedidaForCEPAction extends ReferenceSearchAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		
		super.execute(); 
		
		Stack idMedida2 = getPreviousViews();
		View v = (View)idMedida2.get(0);
		
		//String idMedida = getPreviousView().getValueString("medida.id");
		String idMedida = v.getValueString("medida.id");
		
		if(idMedida != null && idMedida.isEmpty() == false)
		{
			if(idMedida != null && idMedida.isEmpty() == false)
			{
				getTab().setBaseCondition( 
					"'" + idMedida + "' = ${medida.id}) " 
				);
			}
			return;
		}
		
		throw new Exception("Primeiro selecione o processo padrão e a medida. Retorne a tela anterior de cadastro.");
		
	}
	
}