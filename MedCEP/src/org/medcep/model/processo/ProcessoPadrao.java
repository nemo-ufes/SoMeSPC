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
import org.openxava.jpa.XPersistence;

@Entity
@Views({
	@View(members = "nome, versao;  descricao; atividadePadrao; capacidadeDeProcesso; Subprocesso;"),
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

    @JoinTable(name = "ProcessoPadrao_dependeDe_ProcessoPadrap",
	    joinColumns = {
		    @JoinColumn(name = "processo1", referencedColumnName = "id", nullable = false)
	    },
	    inverseJoinColumns = {
		    @JoinColumn(name = "processo2", referencedColumnName = "id", nullable = false)
	    })
    @ManyToMany(fetch = FetchType.LAZY)
    @ListProperties("nome")
    private Collection<ProcessoPadrao> Subprocesso;
    
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
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Processo de Software Padrão")
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
	if(tipoDeEntidadeMensuravel != null){
    	
    	String nomeEntidade = "Processo de Software Padrão";
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
}
