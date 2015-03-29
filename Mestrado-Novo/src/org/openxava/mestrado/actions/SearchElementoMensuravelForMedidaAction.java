package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;

//import static org.openxava.jpa.XPersistence.*;

//Exibe para busca a junção de todos os elementos mensuráveis do tipo de entidade mensuráveis e da entiade mensurável
public class SearchElementoMensuravelForMedidaAction extends ReferenceSearchAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		
		super.execute(); 
		
		//String idEntidadeMedida = getPreviousView().getValueString("entidadeMedida.id");
		String idTipoDeEntidadeMensuravel = getPreviousView().getValueString("tipoDeEntidadeMedida.id");
		
		if(idTipoDeEntidadeMensuravel != null && idTipoDeEntidadeMensuravel.isEmpty() == false)
		{
			getTab().setBaseCondition("'" + idTipoDeEntidadeMensuravel + "' IN (SELECT id from ${tipoDeEntidadeMensuravel}) "); 
		}
		else
		{
			throw new Exception("Retorne e selecione primeiro ao menos o Tipo de Entidade Mensurável.");
		}
		
/*		String condition = "";
		if(idEntidadeMedida != null && idEntidadeMedida.isEmpty() == false)
		{			
			EntidadeMensuravel entidadeMensuravel = XPersistence.getManager().find(EntidadeMensuravel.class, idEntidadeMedida);
			
			String id = entidadeMensuravel.getId();
			
			if(id != null && id.isEmpty() == false)
			{
				condition += "'" + id + "' IN (SELECT id from ${entidadeMensuravel}) "; 
			}
		}
		else
		{
			throw new Exception("Retorne e selecione primeiro a Entidade Mensurável.");
		}*/
		/*if((idEntidadeMedida != null && idEntidadeMedida.isEmpty() == false)
			&& (idTipoDeEntidadeMensuravel != null && idTipoDeEntidadeMensuravel.isEmpty() == false))
		{
			condition += "OR ";
		}
		if(idTipoDeEntidadeMensuravel != null && idTipoDeEntidadeMensuravel.isEmpty() == false)
		{			
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = XPersistence.getManager().find(TipoDeEntidadeMensuravel.class, idTipoDeEntidadeMensuravel);
			
			String id = tipoDeEntidadeMensuravel.getId();
			
			if(id != null && id.isEmpty() == false)
			{
				condition += "'" + id + "' IN (SELECT id from ${tipoDeEntidadeMensuravel}) "; 
			}
		}*/
				
		//getTab().setBaseCondition(condition);
		
	}//execute
	
}