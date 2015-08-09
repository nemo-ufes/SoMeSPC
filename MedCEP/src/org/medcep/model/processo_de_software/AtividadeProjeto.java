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

package org.medcep.model.processo_de_software;

import java.util.*;

import javax.persistence.*;

import org.medcep.calculators.*;
import org.medcep.model.entidades_e_medidas.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;

@Entity
@Views({
	@View(members = "nome; baseadoEm; requer; produz; dependeDe;"),
	@View(name = "Simple", members = "nome")
})
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc")
})
@EntityValidator(
	value = AtividadeProjetoValidator.class,
	properties = {
		@PropertyValue(name = "tipoDeEntidadeMensuravel")
	})
public class AtividadeProjeto extends EntidadeMensuravel
{
    @ManyToOne
    @ReferenceView("Simple")
    @Required
    private AtividadePadrao baseadoEm;

    @OneToMany(mappedBy = "atividadeProjetoOcorrida")
    private Collection<OcorrenciaAtividade> ocorrenciaAtividade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "ProcessoProjeto_AtividadeProjeto"
	    , joinColumns = {
		    @JoinColumn(name = "atividadeProjeto_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "processoProjeto_id")
	    })
    @ListProperties("nome")
    private Collection<ProcessoProjeto> processoDeProjeto;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "AtividadeProjeto_produz_Artefato"
	    , joinColumns = {
		    @JoinColumn(name = "atividadeProjeto_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "artefato_id")
	    })
    @ListProperties("nome")
    private Collection<Artefato> produz;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "AtividadeProjeto_requer_Artefato"
	    , joinColumns = {
		    @JoinColumn(name = "ocorrenciaAtividade_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "artefato_id")
	    })
    @ListProperties("nome")
    private Collection<Artefato> requer;

    @ManyToMany(fetch = FetchType.LAZY)
    @ListAction("ManyToMany.new")
    @NewAction("AtividadeProjeto.add")
    @JoinTable(
	    name = "AtividadeProjeto_dependeDe_AtividadeProjeto"
	    , joinColumns = {
		    @JoinColumn(name = "atividadeProjeto_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "atividadeProjeto_id2")
	    })
    @ListProperties("nome")
    private Collection<AtividadeProjeto> dependeDe;

    public AtividadePadrao getBaseadoEm()
    {
	return baseadoEm;
    }

    public void setBaseadoEm(AtividadePadrao baseadoEm)
    {
	this.baseadoEm = baseadoEm;
    }

    public Collection<ProcessoProjeto> getProcessoDeProjeto()
    {
	return processoDeProjeto;
    }

    public void setProcessoDeProjeto(Collection<ProcessoProjeto> processoDeProjeto)
    {
	this.processoDeProjeto = processoDeProjeto;
    }

    @ManyToOne
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Atividade de Projeto")
	    })
    public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel()
    {
	return tipoDeEntidadeMensuravel;
    }

    public void setTipoDeEntidadeMensuravel(TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel)
    {
	this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
    }

    public Collection<Artefato> getProduz()
    {
	return produz;
    }

    public void setProduz(Collection<Artefato> produz)
    {
	this.produz = produz;
    }

    public Collection<Artefato> getRequer()
    {
	return requer;
    }

    public void setRequer(Collection<Artefato> requer)
    {
	this.requer = requer;
    }

    @PreCreate
    @PreUpdate
    public void ajustaElementosMensuraveis()
    {
	if(tipoDeEntidadeMensuravel != null){
    	
    	String nomeEntidade = "Atividade de Projeto";
    	Query query = XPersistence.getManager().createQuery("from TipoDeEntidadeMensuravel t where t.nome = '" + nomeEntidade + "'");
    	TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = (TipoDeEntidadeMensuravel) query.getSingleResult();
    	
    	this.setTipoDeEntidadeMensuravel(tipoDeEntidadeMensuravel);
    }
	if (getElementoMensuravel() == null)
	    setElementoMensuravel(new ArrayList<ElementoMensuravel>());

	if (tipoDeEntidadeMensuravel != null && tipoDeEntidadeMensuravel.getElementoMensuravel() != null)
	{
	    boolean add;
	    for (ElementoMensuravel elemTipo : tipoDeEntidadeMensuravel.getElementoMensuravel())
	    {
		add = true;
		for (ElementoMensuravel elem : getElementoMensuravel())
		{
		    if (elem.getNome().compareTo(elemTipo.getNome()) == 0)
		    {
			add = false;
			break;
		    }
		}
		if (add)
		    getElementoMensuravel().add(elemTipo);
	    }//elemTipo
	}
    }//ajusta

    public Collection<AtividadeProjeto> getDependeDe()
    {
	return dependeDe;
    }

    public void setDependeDe(Collection<AtividadeProjeto> dependeDe)
    {
	this.dependeDe = dependeDe;
    }

    public Collection<OcorrenciaAtividade> getOcorrenciaAtividade()
    {
	return ocorrenciaAtividade;
    }

    public void setOcorrenciaAtividade(Collection<OcorrenciaAtividade> ocorrenciaAtividade)
    {
	this.ocorrenciaAtividade = ocorrenciaAtividade;
    }

}
