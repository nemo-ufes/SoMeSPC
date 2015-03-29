package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;

//import static org.openxava.jpa.XPersistence.*;

public class SearchEntidadeMensuravelActionForMedicao extends ReferenceSearchAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		
		super.execute(); 
		
		String idMedidaPlanoDeMedicao = getPreviousView().getValueString("medidaPlanoDeMedicao.id");
		
		if(idMedidaPlanoDeMedicao != null && idMedidaPlanoDeMedicao.isEmpty() == false)
		{
			MedidaPlanoDeMedicao medidaPlanoDeMedicao = XPersistence.getManager().find(MedidaPlanoDeMedicao.class, idMedidaPlanoDeMedicao);
			
			//String idMedida = medidaPlanoDeMedicao.getMedida().getId();
			String idTipoEntidade = medidaPlanoDeMedicao.getMedida().getTipoDeEntidadeMedida().getId();
			
			if(idTipoEntidade != null && idTipoEntidade.isEmpty() == false)
			{
				getTab().setBaseCondition( 
					"'" + idTipoEntidade + "' = ${tipoDeEntidadeMensuravel} "
					//+ "'" + idMedida + "' NOT IN (SELECT medida.id FROM ${medidaPlanoDeMedicao})" 
				);
			}
			return;
		}
		
		throw new Exception("A medição deve ser feita para Entidades que sejam do mesmo Tipo de Entidade Mensurável da Medida.");
		
	}//execute
	
}