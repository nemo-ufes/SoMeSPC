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
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome; tipoDeEntidadeMensuravel; inicio, fim; recursoHumano; papelRecursoHumano; elementoMensuravel"),
	@View(name = "Simple", members = "nome")
})
@Tab(properties = "recursoHumano.nome, equipe.nome, papelRecursoHumano.nome, inicio, fim")
public class AlocacaoEquipe extends EntidadeMensuravel
{

    private Date inicio;
    private Date fim;

    @ManyToOne
    @Required
    @ReferenceView("Simple")
    private Equipe equipe;

    @ManyToOne
    @Required
    @NoCreate
    @NoModify
    @ReferenceView("Simple")
    @SearchAction("AlocacaoEquipe.searchRecursoHumano")
    private RecursoHumano recursoHumano;

    @ManyToOne
    @Required
    @NoCreate
    @NoModify
    @ReferenceView("Simple")
    private PapelRecursoHumano papelRecursoHumano;

    public Date getInicio()
    {
	return inicio;
    }

    public void setInicio(Date inicio)
    {
	this.inicio = inicio;
    }

    public Date getFim()
    {
	return fim;
    }

    public void setFim(Date fim)
    {
	this.fim = fim;
    }

    public Equipe getEquipe()
    {
	return equipe;
    }

    public void setEquipe(Equipe equipe)
    {
	this.equipe = equipe;
    }

    public RecursoHumano getRecursoHumano()
    {
	return recursoHumano;
    }

    public void setRecursoHumano(RecursoHumano recursoHumano)
    {
	this.recursoHumano = recursoHumano;
    }

    public PapelRecursoHumano getPapelRecursoHumano()
    {
	return papelRecursoHumano;
    }

    public void setPapelRecursoHumano(PapelRecursoHumano papelRecursoHumano)
    {
	this.papelRecursoHumano = papelRecursoHumano;
    }

    @ManyToOne
    @Required
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Alocação de Recurso Humano")
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
    public void ajustar()
    {
	this.setNome(String.format("%s %s em %s", papelRecursoHumano.getNome(), recursoHumano.getNome(), equipe.getNome()));

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

}
