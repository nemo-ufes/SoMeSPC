/*
 * MedCEP - A powerful tool for measure
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique N�spoli Castro, Vin�cius Soares Fonseca
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
package org.medcep.model.processo;

import java.util.*;

import javax.persistence.*;

import org.medcep.calculators.*;
import org.medcep.model.medicao.*;
import org.medcep.model.organizacao.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;

@Entity
@Views({
	@View(members = "nome; descricao; baseadoEm; projeto; atividadeProjeto; Subprocesso;"),
	@View(name = "Simple", members = "nome"),
})
@Tab(properties = "nome", defaultOrder = "${nome} asc")
@EntityValidator(
	value = ProcessoDeProjetoValidator.class,
	properties = {
		@PropertyValue(name = "tipoDeEntidadeMensuravel")
	})
public class ProcessoProjeto extends EntidadeMensuravel
{

    @ManyToOne
    @Required
    @ReferenceView("Simple")
    private ProcessoPadrao baseadoEm;

    @OneToOne
    @ReferenceView("Simple")
    private Projeto projeto;
    
    @JoinTable(name = "ProcessoProjeto_dependeDe_ProcessoProjeto",
	    joinColumns = {
		    @JoinColumn(name = "processo1", referencedColumnName = "id", nullable = false)
	    },
	    inverseJoinColumns = {
		    @JoinColumn(name = "processo2", referencedColumnName = "id", nullable = false)
	    })
    @ManyToMany(fetch = FetchType.LAZY)
    @ListProperties("nome")
    private Collection<ProcessoProjeto> Subprocesso;

    public Collection<ProcessoProjeto> getSubprocesso() {
		return Subprocesso;
	}

	public void setSubprocesso(Collection<ProcessoProjeto> subprocesso) {
		Subprocesso = subprocesso;
	}

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "ProcessoProjeto_AtividadeProjeto"
	    , joinColumns = {
		    @JoinColumn(name = "processoProjeto_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "atividadeProjeto_id")
	    })
    @ListProperties("nome")
    @NewAction("ProcessoDeProjeto.add")
    private Collection<AtividadeProjeto> atividadeProjeto;

    public Collection<AtividadeProjeto> getAtividadeProjeto()
    {
	return atividadeProjeto;
    }

    public void setAtividadeProjeto(
	    Collection<AtividadeProjeto> atividadeProjeto)
    {
	this.atividadeProjeto = atividadeProjeto;
    }

    public Projeto getProjeto()
    {
	return projeto;
    }

    public void setProjeto(Projeto projeto)
    {
	this.projeto = projeto;
    }

    @ManyToOne
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Processo de Software em Projeto")
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

    public ProcessoPadrao getBaseadoEm()
    {
	return baseadoEm;
    }

    public void setBaseadoEm(ProcessoPadrao baseadoEm)
    {
	this.baseadoEm = baseadoEm;
    }

    @PreCreate
    @PreUpdate
    public void ajustaElementosMensuraveis()
    {
	if(tipoDeEntidadeMensuravel != null){
    	
    	String nomeEntidade = "Processo de Software em Projeto";
    	Query query = XPersistence.getManager().createQuery("from TipoDeEntidadeMensuravel t where t.nome = '" + nomeEntidade + "'");
    	TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = (TipoDeEntidadeMensuravel) query.getSingleResult();
    	
    	this.setTipoDeEntidadeMensuravel(tipoDeEntidadeMensuravel);
    }
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
