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

import org.openxava.actions.*;

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