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

package org.medcep.model.processo;

import java.util.*;

import javax.persistence.*;

import org.medcep.calculators.*;
import org.medcep.model.medicao.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome; tipoDeEntidadeMensuravel; tipoDeArtefato; descricao; elementoMensuravel;"),
	@View(name = "Simple", members = "nome"),
	@View(name = "SimpleNoFrame", members = "nome")
})
@Tabs({
	@Tab(properties = "nome, tipoDeArtefato.nome", defaultOrder = "${nome} asc")
})
@EntityValidator(
	value = ArtefatoValidator.class,
	properties = {
		@PropertyValue(name = "tipoDeEntidadeMensuravel")
	})
public class Artefato extends EntidadeMensuravel
{

    @ManyToOne
    @Required
    @ReferenceView("Simple")
    private TipoDeArtefato tipoDeArtefato;

    public TipoDeArtefato getTipoDeArtefato()
    {
	return tipoDeArtefato;
    }

    public void setTipoDeArtefato(TipoDeArtefato tipoDeArtefato)
    {
	this.tipoDeArtefato = tipoDeArtefato;
    }

    @ManyToOne
    @Required
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Artefato")
	    })
    private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;

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

	if (tipoDeArtefato != null && tipoDeArtefato.getElementoMensuravel() != null)
	{
	    boolean add;
	    for (ElementoMensuravel elemTipo : tipoDeArtefato.getElementoMensuravel())
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
