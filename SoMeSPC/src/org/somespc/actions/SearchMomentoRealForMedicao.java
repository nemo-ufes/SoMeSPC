/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.somespc.actions;

import org.openxava.actions.*;

public class SearchMomentoRealForMedicao extends ReferenceSearchAction
{

    // adding collection elements list
    public void execute() throws Exception
    {
	super.execute();

	getTab().setBaseCondition("e_tipoDeEntidadeMensuravel.id IN "
		+ "(SELECT t "
		+ "FROM org.SoMeSPC.model.medicao.TipoDeEntidadeMensuravel t "
		+ "WHERE t.nome = 'Ocorrência de Atividade' OR t.nome = 'Ocorrência de Processo de Software' )");
	
	return;
    }//execute

}