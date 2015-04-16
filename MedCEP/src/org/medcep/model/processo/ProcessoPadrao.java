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
import org.medcep.model.processo.comportamento.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome, versao;  tipoDeEntidadeMensuravel; descricao; atividadePadrao; elementoMensuravel; capacidadeDeProcesso;"),
	@View(name = "Simple", members = "nome"),
	@View(name = "SimpleNoFrame", members = "nome")
})
@Tab(properties = "nome, versao", defaultOrder = "${nome} asc, ${versao} desc")
@EntityValidator(
	value = ProcessoPadraoValidator.class,
	properties = {
		@PropertyValue(name = "tipoDeEntidadeMensuravel")
	})
public class ProcessoPadrao extends EntidadeMensuravel
{

    @Required
    private String versao;

    @OneToMany(mappedBy = "processoPadrao")
    private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;

    @OneToMany(mappedBy = "processoPadrao")
    private Collection<DesempenhoDeProcessoEspecificado> desempenhoDeProcessoEspecificado;

    @OneToMany(mappedBy = "baseadoEm")
    private Collection<ProcessoProjeto> processoProjeto;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
	    name = "ProcessoPadrao_AtividadePadrao"
	    , joinColumns = {
		    @JoinColumn(name = "processoPadrao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "atividadePadrao_id")
	    })
    @ListProperties("nome")
    private Collection<AtividadePadrao> atividadePadrao;

    public String getVersao()
    {
	return versao;
    }

    public void setVersao(String versao)
    {
	this.versao = versao;
    }

    public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso()
    {
	return baselineDeDesempenhoDeProcesso;
    }

    public void setBaselineDeDesempenhoDeProcesso(
	    Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso)
    {
	this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
    }

    public Collection<DesempenhoDeProcessoEspecificado> getDesempenhoDeProcessoEspecificado()
    {
	return desempenhoDeProcessoEspecificado;
    }

    public void setDesempenhoDeProcessoEspecificado(
	    Collection<DesempenhoDeProcessoEspecificado> desempenhoDeProcessoEspecificado)
    {
	this.desempenhoDeProcessoEspecificado = desempenhoDeProcessoEspecificado;
    }

    public Collection<ProcessoProjeto> getProcessoDeSoftwareDeProjeto()
    {
	return processoProjeto;
    }

    public void setProcessoDeSoftwareDeProjeto(
	    Collection<ProcessoProjeto> processoProjeto)
    {
	this.processoProjeto = processoProjeto;
    }

    public Collection<AtividadePadrao> getAtividadePadrao()
    {
	return atividadePadrao;
    }

    public void setAtividadePadrao(Collection<AtividadePadrao> atividadePadrao)
    {
	this.atividadePadrao = atividadePadrao;
    }

    @ReadOnly
    @OneToMany(mappedBy = "processoPadrao")
    @ListProperties("medida.nome, data, capaz")
    private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;

    public Collection<CapacidadeDeProcesso> getCapacidadeDeProcesso()
    {
	return capacidadeDeProcesso;
    }

    public void setCapacidadeDeProcesso(
	    Collection<CapacidadeDeProcesso> capacidadeDeProcesso)
    {
	this.capacidadeDeProcesso = capacidadeDeProcesso;
    }

    @ManyToOne
    @Required
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Processo de Software Padrão")
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
