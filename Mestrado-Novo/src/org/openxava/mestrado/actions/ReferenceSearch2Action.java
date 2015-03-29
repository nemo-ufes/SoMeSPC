package org.openxava.mestrado.actions;

import java.util.*;

import javax.inject.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.model.meta.*;
import org.openxava.tab.*;
import org.openxava.util.*;
import org.openxava.view.*;


public class ReferenceSearch2Action extends ReferenceSearchAction { 

	
	public void execute() throws Exception 
	{
		super.execute();
		
		//Caso seja a Tree do plano de medição executa os filtros na listagem dependendo do elemento selecionado
		if(
			getView() != null
			&& getPreviousView() != null 
			&& getPreviousView().getMemberName() != null 
			&& getPreviousView().getMemberName().compareTo("planoTree") == 0
		  )
		{
			//Se tem algum item selecionado
			if(NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject != null)
			{
				TreeItemPlanoMedicao pti = (TreeItemPlanoMedicao)NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject;
				
				TreeItemPlanoMedicaoBase pt = pti.getItem();
				
				if(pt instanceof ObjetivoEstrategico)
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
															 " ) "+
												//TODO: permitir que uma medida/necessidade/os/om seja adicionada mais de uma vez (um vez em cada objetvo estrategico)
												//Tentativa de
												//e não tenha sido adicionado para aquele objetivo estrategico
												/*"AND ${id} NOT IN (SELECT om.id FROM TreeItemPlanoMedicao tipm " +
													  			"JOIN tipm.item tipmb " +
													  			"JOIN tipm.planoDeMedicaoContainer pm " +
													  			"JOIN pm.objetivoEstrategico oe " +
													  			"JOIN oe.objetivoDeMedicao om " +
												  			"WHERE pm.id = '"+ pti.getPlanoDeMedicaoContainer().getId() +"'" +
												  					"AND oe.id = '" + pt.getId() + "')) " +*/
												//ou seja uma medida do objetivo
												//"OR ${id} IN (SELECT m.id FROM ObjetivoEstrategico o " +
												//					"JOIN o.indicadores m " +
												//			  "WHERE o.id = '" + pt.getId() + "')) " +
																	
												"AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
															  			"JOIN tipm.item tipmb " +
															  			"JOIN tipm.planoDeMedicaoContainer pm " +
														  			"WHERE pm.id = '"+ pti.getPlanoDeMedicaoContainer().getId() +"')");
				}
				else if(pt instanceof ObjetivoDeSoftware)
				{
					//Exibe apenas ObjetivoDeMedicao
					getTab().setBaseCondition("(${id} IN (SELECT om.id FROM ObjetivoDeMedicao om " +
														"JOIN om.objetivoDeSoftware os " +
														"WHERE os.id = '"  + pt.getId() + "') " +
														" ) "+
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
													  			"WHERE pm.id = '"+ pti.getPlanoDeMedicaoContainer().getId() +"')");
				}
				else if(pt instanceof ObjetivoDeMedicao)
				{
					//Exibe apenas NecessidadeDeInformacao
					getTab().setBaseCondition("(${id} IN (SELECT ni.id FROM ObjetivoDeMedicao om " +
														"JOIN om.necessidadeDeInformacao ni " +
														"WHERE om.id = '"  + pt.getId() + "') " +
														" ) "+
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
													  			"WHERE pm.id = '"+ pti.getPlanoDeMedicaoContainer().getId() +"')");
				}
				else if(pt instanceof NecessidadeDeInformacao)
				{
					//Exibe apenas NecessidadeDeInformacao e é subnecessidade da selecionada //TODO:
					
					//Exibe apenas Medidas
					getTab().setBaseCondition("${id} IN (SELECT m.id FROM NecessidadeDeInformacao ni " +
															"JOIN ni.medidas m " +
														"WHERE ni.id = '"  + pt.getId() + "') " +
											   "AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
													  				"JOIN tipm.item tipmb " +
													  				"JOIN tipm.planoDeMedicaoContainer pm " +
													  			"WHERE pm.id = '"+ pti.getPlanoDeMedicaoContainer().getId() +"')");
				}
				else
				{
					//
				}
				
			}
			else
			{
				Stack stack = getPreviousViews();
				View v = (View)stack.get(0);
				
				String id = v.getValueString("id");
				
				getTab().setBaseCondition("${id} IN (SELECT oe.id FROM ObjetivoEstrategico oe) " +
													"AND ${id} NOT IN (SELECT tipmb.id FROM TreeItemPlanoMedicao tipm " +
																			"JOIN tipm.item tipmb " +
															  				"JOIN tipm.planoDeMedicaoContainer pm " +
															  			"WHERE pm.id = '"+ id +"')");
			}
		}//if PlanoMedicao
		
	}//execute
	
}//Class