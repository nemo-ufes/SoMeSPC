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

import org.medcep.model.medicao.*;
import org.medcep.model.medicao.planejamento.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;

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