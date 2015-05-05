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
package org.medcep.model.organizacao;

import java.util.*;

import javax.persistence.*;

import org.medcep.calculators.*;
import org.medcep.model.medicao.*;
import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.projeto.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome; tipoDeEntidadeMensuravel; dataInicio, dataFim; equipe; objetivo; elementoMensuravel; criterioDeProjeto;"),
	@View(name = "Simple", members = "nome"),
	@View(name = "SimpleNoFrame", members = "nome")
})
@Tab(properties = "nome, dataInicio, dataFim", defaultOrder = "${nome} asc")
public class Projeto extends EntidadeMensuravel
{

    public Collection<CriterioDeProjeto> getCriterioDeProjeto()
    {
	return criterioDeProjeto;
    }

    public void setCriterioDeProjeto(Collection<CriterioDeProjeto> criterioDeProjeto)
    {
	this.criterioDeProjeto = criterioDeProjeto;
    }

    private Date dataInicio;

    private Date dataFim;

    public Date getDataInicio()
    {
	return dataInicio;
    }

    public void setDataInicio(Date dataInicio)
    {
	this.dataInicio = dataInicio;
    }

    public Date getDataFim()
    {
	return dataFim;
    }

    public void setDataFim(Date dataFim)
    {
	this.dataFim = dataFim;
    }

    @OneToMany(mappedBy = "projeto")
    private Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto;

    @ManyToMany
    @JoinTable(
	    name = "objetivo_projeto"
	    , joinColumns = {
		    @JoinColumn(name = "objetivo_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "projeto_id")
	    })
    @ListProperties("nome")
    private Collection<Objetivo> objetivo;

    public Collection<PlanoDeMedicaoDoProjeto> getPlanoDeMedicaoDoProjeto()
    {
	return planoDeMedicaoDoProjeto;
    }

    public void setPlanoDeMedicaoDoProjeto(
	    Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto)
    {
	this.planoDeMedicaoDoProjeto = planoDeMedicaoDoProjeto;
    }

    public Collection<Objetivo> getObjetivo()
    {
	return objetivo;
    }

    public void setObjetivo(Collection<Objetivo> objetivo)
    {
	this.objetivo = objetivo;
    }

    @ManyToMany
    @JoinTable(
	    name = "equipe_projeto"
	    , joinColumns = {
		    @JoinColumn(name = "projeto_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "equipe_id")
	    })
    private Collection<Equipe> equipe;

    public Collection<Equipe> getEquipe()
    {
	return equipe;
    }

    public void setEquipe(Collection<Equipe> equipe)
    {
	this.equipe = equipe;
    }

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.REMOVE)
    @ListProperties("criterio.nome, valorMedido.valorMedido")
    @CollectionView("Projeto")
    private Collection<CriterioDeProjeto> criterioDeProjeto;

    @ManyToOne
    @Required
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Projeto")
	    })
    public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel()
    {
	return tipoDeEntidadeMensuravel;
    }

    public void setTipoDeEntidadeMensuravel(
	    TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel)
    {
	this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
    }

    @PreCreate
    @PreUpdate
    public void ajustaElementosMensuraveis()
    {
	if (elementoMensuravel == null)
	    elementoMensuravel = new ArrayList<ElementoMensuravel>();

	if (tipoDeEntidadeMensuravel != null && tipoDeEntidadeMensuravel.getElementoMensuravel() != null)
	{
	    boolean add;
	    for (ElementoMensuravel elemTipo : tipoDeEntidadeMensuravel.getElementoMensuravel())
	    {
		add = true;
		for (ElementoMensuravel elem : elementoMensuravel)
		{
		    if (elem.getNome().compareTo(elemTipo.getNome()) == 0)
		    {
			add = false;
			break;
		    }
		}
		if (add)
		    elementoMensuravel.add(elemTipo);
	    }//elemTipo
	}
    }//ajusta

}
