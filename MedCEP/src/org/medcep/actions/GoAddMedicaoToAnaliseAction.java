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

import org.medcep.model.plano_de_medicao.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;

public class GoAddMedicaoToAnaliseAction extends GoAddElementsToCollectionAction
{

    public void execute() throws Exception
    {
	super.execute();

	Integer idMedidaPlanoDeMedicao = getPreviousView().getValueInt("medidaPlanoDeMedicao.id");
	Integer idEntidadeMensuravel = getPreviousView().getValueInt("entidadeMensuravel.id");
	Integer id = getPreviousView().getValueInt("id");

	if (idMedidaPlanoDeMedicao != 0)
	{
	    MedidaPlanoDeMedicao medidaPlanoDeMedicao = XPersistence.getManager().find(MedidaPlanoDeMedicao.class, idMedidaPlanoDeMedicao);

	    getTab().setBaseCondition(medidaPlanoDeMedicao.getMedida().getId() + " = ${medidaPlanoDeMedicao.medida.id} "
		    + "AND " + idEntidadeMensuravel + " = e.entidadeMensuravel.id "
		    + "AND ${id} NOT IN "
		    + "(SELECT me.id FROM AnaliseDeMedicao am JOIN am.medicao me WHERE am.id = " + id + ")"
		    );
	    return;
	}

	getTab().setBaseCondition("e.entidadeMensuravel.id = 0");
	return;

    }

}
