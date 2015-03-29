/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */
package org.medcep.actions;

import org.medcep.model.medicao.planejamento.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;

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
