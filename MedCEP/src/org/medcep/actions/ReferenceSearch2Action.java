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

import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.organizacao.*;
import org.openxava.actions.*;
import org.openxava.view.*;

public class ReferenceSearch2Action extends ReferenceSearchAction
{

    public void execute() throws Exception
    {
	super.execute();

	//Caso seja a Tree do plano de medição executa os filtros na listagem dependendo do elemento selecionado
	if (getView() != null
		&& getPreviousView() != null
		&& getPreviousView().getMemberName() != null
		&& getPreviousView().getMemberName().compareTo("planoTree") == 0)
	{
	    //Se tem algum item selecionado
	    if (NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject != null)
	    {
		TreeItemPlanoMedicao pti = (TreeItemPlanoMedicao) NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject;

		TreeItemPlanoMedicaoBase pt = pti.getItem();

		if (pt instanceof ObjetivoEstrategico)
		{
		    //Exibe apenas ObjetivoDeSoftware
		    getTab().setBaseCondition("(${id} IN (SELECT os.id FROM ObjetivoDeSoftware os " +
			    "JOIN os.objetivoEstrategico oe " +
			    "WHERE oe.id = '" + pt.getId() + "') " +

			    //ou seja uma necessidade de informação do objetivo
			    //"OR ${id} IN (SELECT ni.id FROM ObjetivoEstrategico o " +
			    //					"JOIN o.necessidadeDeInformacao ni " +
			    //			 "WHERE o.id = '" + pt.getId() + "') " +

			    //ou seja um objetivo de medicao
			    "OR ${id} IN (SELECT om.id FROM ObjetivoEstrategico o " +
			    "JOIN o.objetivoDeMedicao om " +
			    "WHERE o.id = '" + pt.getId() + "') " +
			    " ) " +
			    //TODO: permitir que uma medida/necessidade/os/om seja adicionada mais de uma vez (um vez em cada objetvo estrategico)
			    //Tentativa de
			    //e não tenha sido adicionado para aquele objetivo estrategico
			    /*
			     * "AND ${id} NOT IN (SELECT om.id FROM TreeItemPlanoMedicao tipm " +
			     * "JOIN tipm.item tipmb " +
			     * "JOIN tipm.planoDeMedicaoContainer pm " +
			     * "JOIN pm.objetivoEstrategico oe " +
			     * "JOIN oe.objetivoDeMedicao om " +
			     * "WHERE pm.id = '"+ pti.getPlanoDeMedicaoContainer().getId() +"'" +
			     * "AND oe.id = '" + pt.getId() + "')) " +
			     */
			    //ou seja uma medida do objetivo
			    //"OR ${id} IN (SELECT m.id FROM ObjetivoEstrategico o " +
			    //					"JOIN o.indicadores m " +
			    //			  "WHERE o.id = '" + pt.getId() + "')) " +

			    "AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
			    "JOIN tipm.item tipmb " +
			    "JOIN tipm.planoDeMedicaoContainer pm " +
			    "WHERE pm.id = '" + pti.getPlanoDeMedicaoContainer().getId() + "')");
		}
		else if (pt instanceof ObjetivoDeSoftware)
		{
		    //Exibe apenas ObjetivoDeMedicao
		    getTab().setBaseCondition("(${id} IN (SELECT om.id FROM ObjetivoDeMedicao om " +
			    "JOIN om.objetivoDeSoftware os " +
			    "WHERE os.id = '" + pt.getId() + "') " +
			    " ) " +
			    //ou seja uma necessidade de informação do objetivo
			    //"OR ${id} IN (SELECT ni.id FROM ObjetivoDeSoftware o " +
			    //					"JOIN o.necessidadeDeInformacao ni " +
			    //			 "WHERE o.id = '" + pt.getId() + "') " +
			    //ou seja uma medida do objetivo
			    //"OR ${id} IN (SELECT m.id FROM ObjetivoDeSoftware o " +
			    //					"JOIN o.indicadores m " +
			    //			  "WHERE o.id = '" + pt.getId() + "')) " +
			    //e ja não esteja presente no plano
			    "AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
			    "JOIN tipm.item tipmb " +
			    "JOIN tipm.planoDeMedicaoContainer pm " +
			    "WHERE pm.id = '" + pti.getPlanoDeMedicaoContainer().getId() + "')");
		}
		else if (pt instanceof ObjetivoDeMedicao)
		{
		    //Exibe apenas NecessidadeDeInformacao
		    getTab().setBaseCondition("(${id} IN (SELECT ni.id FROM ObjetivoDeMedicao om " +
			    "JOIN om.necessidadeDeInformacao ni " +
			    "WHERE om.id = '" + pt.getId() + "') " +
			    " ) " +
			    //ou seja uma necessidade de informação do objetivo
			    //"OR ${id} IN (SELECT ni.id FROM ObjetivoDeMedicao o " +
			    //					"JOIN o.necessidadeDeInformacao ni " +
			    //			 "WHERE o.id = '" + pt.getId() + "') " +
			    //ou seja uma medida do objetivo
			    //"OR ${id} IN (SELECT m.id FROM ObjetivoDeMedicao o " +
			    //					"JOIN o.indicadores m " +
			    //			  "WHERE o.id = '" + pt.getId() + "')) " +
			    //e ja não esteja presente no plano
			    "AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
			    "JOIN tipm.item tipmb " +
			    "JOIN tipm.planoDeMedicaoContainer pm " +
			    "WHERE pm.id = '" + pti.getPlanoDeMedicaoContainer().getId() + "')");
		}
		else if (pt instanceof NecessidadeDeInformacao)
		{
		    //Exibe apenas NecessidadeDeInformacao e é subnecessidade da selecionada //TODO:

		    //Exibe apenas Medidas
		    getTab().setBaseCondition("${id} IN (SELECT m.id FROM NecessidadeDeInformacao ni " +
			    "JOIN ni.medidas m " +
			    "WHERE ni.id = '" + pt.getId() + "') " +
			    "AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
			    "JOIN tipm.item tipmb " +
			    "JOIN tipm.planoDeMedicaoContainer pm " +
			    "WHERE pm.id = '" + pti.getPlanoDeMedicaoContainer().getId() + "')");
		}
		else
		{
		    //
		}

	    }
	    else
	    {
		Stack<?> stack = getPreviousViews();
		View v = (View) stack.get(0);

		Integer id = v.getValueInt("id");

		getTab().setBaseCondition("${id} IN (SELECT oe.id FROM ObjetivoEstrategico oe) " +
			"AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
			"JOIN tipm.item tipmb " +
			"JOIN tipm.planoDeMedicaoContainer pm " +
			"WHERE pm.id = " + id + ")");
	    }
	}//if PlanoMedicao

    }//execute

}//Class