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
	@View(members = "nome; tipoDeEntidadeMensuravel; atividadeProjetoOcorrida"),
	@View(name = "Simple", members = "nome")
})
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc", baseCondition = "TYPE(e) = OcorrenciaAtividade")
})
@EntityValidator(
	value = AtividadeInstanciadaValidator.class,
	properties = {
		@PropertyValue(name = "tipoDeEntidadeMensuravel")
	})
public class OcorrenciaAtividade extends EntidadeMensuravel
{

    @ManyToOne
    @ReferenceView("Simple")
    @Required
    private AtividadeProjeto atividadeProjetoOcorrida;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "OcorrenciaProcesso_OcorrenciaAtividade"
	    , joinColumns = {
		    @JoinColumn(name = "ocorrenciaAtividade_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "ocorrenciaProcesso_id")
	    })
    private Collection<OcorrenciaProcesso> ocorrenciaProcesso;

    @ManyToOne
    @Required
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Ocorrência de Atividade")
	    })
    private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;

   

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "OcorrenciaAtividade_produz_Artefato"
	    , joinColumns = {
		    @JoinColumn(name = "ocorrenciaAtividade_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "artefato_id")
	    })
    @ListProperties("nome")
    private Collection<Artefato> produz;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "OcorrenciaAtividade_requer_Artefato"
	    , joinColumns = {
		    @JoinColumn(name = "ocorrenciaAtividade_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "artefato_id")
	    })
    @ListProperties("nome")
    private Collection<Artefato> requer;

    public Collection<OcorrenciaProcesso> getOcorrenciaProcesso()
    {
	return ocorrenciaProcesso;
    }

    public void setOcorrenciaProcesso(
	    Collection<OcorrenciaProcesso> ocorrenciaProcesso)
    {
	this.ocorrenciaProcesso = ocorrenciaProcesso;
    }

   
    public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel()
    {
	return tipoDeEntidadeMensuravel;
    }

    public void setTipoDeEntidadeMensuravel(
	    TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel)
    {
	this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
    }

    public AtividadeProjeto getAtividadeProjetoOcorrida()
    {
	return atividadeProjetoOcorrida;
    }

    public void setAtividadeProjetoOcorrida(AtividadeProjeto atividadeProjetoOcorrida)
    {
	this.atividadeProjetoOcorrida = atividadeProjetoOcorrida;
    }

//    @PreCreate
//    @PreUpdate
//    public void ajustaElementosMensuraveis()
//    {
//	if (elementoMensuravel == null)
//	    elementoMensuravel = new ArrayList<ElementoMensuravel>();
//
//	if (tipoDeEntidadeMensuravel != null && tipoDeEntidadeMensuravel.getElementoMensuravel() != null)
//	{
//	    boolean add;
//	    for (ElementoMensuravel elemTipo : tipoDeEntidadeMensuravel.getElementoMensuravel())
//	    {
//		add = true;
//		for (ElementoMensuravel elem : elementoMensuravel)
//		{
//		    if (elem.getNome().compareTo(elemTipo.getNome()) == 0)
//		    {
//			add = false;
//			break;
//		    }
//		}
//		if (add)
//		    elementoMensuravel.add(elemTipo);
//	    }//elemTipo
//	}
//    }//ajusta

}
