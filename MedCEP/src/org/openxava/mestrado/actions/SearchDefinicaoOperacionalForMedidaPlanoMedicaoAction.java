package org.openxava.mestrado.actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;

//import static org.openxava.jpa.XPersistence.*;

public class SearchDefinicaoOperacionalForMedidaPlanoMedicaoAction extends ReferenceSearchAction { 
	
	// adding collection elements list
	public void execute() throws Exception {
		
		super.execute(); 
		
		String idMedidaPlanoDeMedicao = getPreviousView().getValueString("medidaPlanoDeMedicao.id");
		
		if(idMedidaPlanoDeMedicao != null && idMedidaPlanoDeMedicao.isEmpty() == false)
		{
			MedidaPlanoDeMedicao medidaPlanoDeMedicao = XPersistence.getManager().find(MedidaPlanoDeMedicao.class, idMedidaPlanoDeMedicao);
			
			String idMedida = medidaPlanoDeMedicao.getMedida().getId();
			
			if(idMedida != null && idMedida.isEmpty() == false)
			{
				getTab().setBaseCondition( 
					"'" + idMedida + "' IN (SELECT id from ${medida}) " 
				);
			}
			return;
		}
		
		throw new Exception("Para selecionar a Definição Operacional selecione primeiramente o Plano de Medição e a Medida do plano. Retorne a tela anterior de cadastro.");
		
	}
	
}