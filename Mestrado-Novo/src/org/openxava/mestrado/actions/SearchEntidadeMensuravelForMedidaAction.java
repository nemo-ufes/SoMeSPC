package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;

//import static org.openxava.jpa.XPersistence.*;

public class SearchEntidadeMensuravelForMedidaAction extends ReferenceSearchAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		
		super.execute(); 
		
		String idTipoDeEntidadeMedida = getPreviousView().getValueString("tipoDeEntidadeMedida.id");
		
		if(idTipoDeEntidadeMedida != null && idTipoDeEntidadeMedida.isEmpty() == false)
		{
			TipoDeEntidadeMensuravel tipoDeentidadeMensuravel = XPersistence.getManager().find(TipoDeEntidadeMensuravel.class, idTipoDeEntidadeMedida);
			
			String id = tipoDeentidadeMensuravel.getId();
			
			if(id != null && id.isEmpty() == false)
			{
				getTab().setBaseCondition( 
					"'" + id + "' IN (SELECT id from ${tipoDeEntidadeMensuravel}) " 
				);
			}
			return;
		}else{
			throw new Exception("Retorne e selecione primeiro o Tipo de Entidade Mensurável.");
		}
	}//execute
	
}