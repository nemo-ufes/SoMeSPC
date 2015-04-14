/*
 * MedCEP - A powerful tool for measure
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
package org.medcep.actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.view.*;

public class SearchRecursoHumanoForAlocacaoAction extends ReferenceSearchAction
{

    public void execute() throws Exception
    {
	super.execute();

	//Exibe apenas medidas adicionado ao plano de medicao (na tree)
	Stack<?> stack = getPreviousViews();
	View v = (View) stack.get(0);

	Integer id = v.getValueInt("id");

	getTab().setBaseCondition("${id} IN (SELECT rh.id FROM Equipe eq " +
		"JOIN eq.recursoHumano rh " +
		"WHERE eq.id = " + id + ") "
		);

    }

}