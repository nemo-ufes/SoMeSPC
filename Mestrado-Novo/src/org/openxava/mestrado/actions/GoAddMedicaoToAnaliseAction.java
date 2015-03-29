package org.openxava.mestrado.actions;

import java.util.*;
import org.openxava.jpa.*;
import org.openxava.actions.*; 
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;

public class GoAddMedicaoToAnaliseAction extends GoAddElementsToCollectionAction { 
	
	public void execute() throws Exception {
		super.execute(); 
		
		String idMedidaPlanoDeMedicao = getPreviousView().getValueString("medidaPlanoDeMedicao.id");
		String id = getPreviousView().getValueString("id");
		
		MedidaPlanoDeMedicao medidaPlanoDeMedicao = XPersistence.getManager().find(MedidaPlanoDeMedicao.class, idMedidaPlanoDeMedicao);
		
		//Exibe apenas medicoes para a medida do plano em questao
/*		getTab().setBaseCondition( 
			"'" + idMedidaPlanoDeMedicao + "' = ${medidaPlanoDeMedicao.id}"
			+ " AND ${id} NOT IN (SELECT me.id FROM AnaliseDeMedicao am JOIN am.medicao me WHERE am.id = '" + id + "')"
		);*/
		
		//Exibe medições que tenham sido feitas para a medida da medida plano medição
		getTab().setBaseCondition( 
				//"'" + idMedidaPlanoDeMedicao + "' = ${medidaPlanoDeMedicao.id} " 
				"'" + medidaPlanoDeMedicao.getMedida().getId() + "' = ${medidaPlanoDeMedicao.medida.id} "
				+ "AND ${id} NOT IN (SELECT me.id FROM AnaliseDeMedicao am JOIN am.medicao me WHERE am.id = '" + id + "')"
			);
	}
	
}
