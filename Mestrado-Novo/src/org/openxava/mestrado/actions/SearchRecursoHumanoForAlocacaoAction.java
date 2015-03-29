package org.openxava.mestrado.actions;

import java.util.*;

import javax.inject.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.model.meta.*;
import org.openxava.tab.*;
import org.openxava.util.*;
import org.openxava.view.*;


public class SearchRecursoHumanoForAlocacaoAction extends ReferenceSearchAction { 
	
	public void execute() throws Exception 
	{				
		super.execute();

		//Exibe apenas medidas adicionado ao plano de medicao (na tree)
		Stack stack = getPreviousViews();
		View v = (View)stack.get(0);
		
		String id = v.getValueString("id");
		
		getTab().setBaseCondition("${id} IN (SELECT rh.id FROM Equipe eq " +
		  										"JOIN eq.recursoHumano rh " +
		  									"WHERE eq.id = '"+ id +"') "	  									
								);
			
	}

}