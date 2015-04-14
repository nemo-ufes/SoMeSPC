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

// import static org.openxava.jpa.XPersistence.*;

public class SearchWhereProcessoPadraoEMedidaForCEPAction extends ReferenceSearchAction
{

    // adding collection elements list
    public void execute() throws Exception
    {

	super.execute();

	Stack<?> idMedida2 = getPreviousViews();
	View v = (View) idMedida2.get(0);

	//String idMedida = getPreviousView().getValueString("medida.id");
	Integer idMedida = v.getValueInt("medida.id");

	if (idMedida != null && idMedida != 0)
	{
	    getTab().setBaseCondition(idMedida + " = ${medida.id}) ");
	    return;
	}

	throw new Exception("Primeiro selecione o processo padrão e a medida. Retorne a tela anterior de cadastro.");

    }

}