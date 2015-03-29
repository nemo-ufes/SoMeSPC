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

package org.medcep.model.organizacao;

import java.util.*;

import javax.persistence.*;

import org.medcep.calculators.*;
import org.medcep.model.medicao.planejamento.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="Projeto [dataInicio, dataFim; nome; tipoDeEntidadeMensuravel]; equipe; objetivo; elementoMensuravel;"),
	@View(name="Simple", members="nome"),
	})
@Tab(properties="nome, dataInicio, dataFim", defaultOrder="${nome} asc")
public class Projeto extends EntidadeMensuravel {
    
	private Date dataInicio;
	 
	private Date dataFim;

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@OneToMany(mappedBy="projeto")
	private Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto;

    @ManyToMany 
    @JoinTable(
  	      name="objetivo_projeto"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivo_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="projeto_id")
  	       }
  	      )
	private Collection<Objetivo> objetivo;

	public Collection<PlanoDeMedicaoDoProjeto> getPlanoDeMedicaoDoProjeto() {
		return planoDeMedicaoDoProjeto;
	}

	public void setPlanoDeMedicaoDoProjeto(
			Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto) {
		this.planoDeMedicaoDoProjeto = planoDeMedicaoDoProjeto;
	}

	public Collection<Objetivo> getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Collection<Objetivo> objetivo) {
		this.objetivo = objetivo;
	}
    
    @ManyToMany 
    @JoinTable(
  	      name="equipe_projeto"
  	      , joinColumns={
  	    		  @JoinColumn(name="projeto_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="equipe_id")
  	       }
  	      )
	private Collection<Equipe> equipe;

	public Collection<Equipe> getEquipe() {
		return equipe;
	}

	public void setEquipe(Collection<Equipe> equipe) {
		this.equipe = equipe;
	}
	
/*	@ManyToOne 
	@Required
	@Transient
	@NoModify
	@NoCreate
	@DescriptionsList(descriptionProperties="nome"
						//,condition="${nome} = 'Projeto'"
						)
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;

	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return super.tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		super.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}*/
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	//A solução para setar por padrçao o tipo de entidade de projeto/outros pode ser dada pelo
	//uso de uma consulta/atribuição do tipo ao objeto a ser salvo. (ex: projeto ou usar reflexão) 
/*	public Collection getFellowCarriers() {
		 Query query = XPersistence.getManager().createQuery("from Carrier c where " +
		 "c.warehouse.zoneNumber = :zone AND " +
		 "c.warehouse.number = :warehouseNumber AND " +
		 "NOT (c.number = :number) ");
		 query.setParameter("zone", getWarehouse().getZoneNumber());
		 query.setParameter("warehouseNumber", getWarehouse().getNumber());
		 query.setParameter("number", getNumber());
		 return query.getResultList();
		}*/
	
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Projeto")
			 }
	)
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}
	
	/*private ProcessoDeSoftwareDeProjeto processoDeSoftwareDeProjeto;*/

	
}
 
