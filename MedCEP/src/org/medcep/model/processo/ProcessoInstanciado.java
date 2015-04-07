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
package org.medcep.model.processo;

import java.util.*;

import javax.persistence.*;

import org.medcep.calculators.*;
import org.medcep.model.medicao.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; baseadoEm; atividadeInstanciada; elementoMensuravel;"),
	@View(name="Simple", members="nome"),
	})
@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc", baseCondition="TYPE(e) = ProcessoInstanciado")
@EntityValidator(
		value=ProcessoInstanciadoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class ProcessoInstanciado extends EntidadeMensuravel {
    
	//@Required
	private String versao;
	
	@ManyToOne @Required
	@ReferenceView("Simple")
	private ProcessoPadrao  baseadoEm;
	
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoInstanciado_AtividadeInstanciada"
	      , joinColumns={
	    		  @JoinColumn(name="processoInstanciado_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      )
	@ListProperties("nome")
	@NewAction("ProcessoInstanciado.add")
	private Collection<AtividadeInstanciada> atividadeInstanciada;
	
	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public ProcessoPadrao getProcessoDeSoftwarePadrao() {
		return baseadoEm;
	}

	public void setProcessoDeSoftwarePadrao(
			ProcessoPadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

	public ProcessoPadrao getBaseadoEm() {
		return baseadoEm;
	}

	public void setBaseadoEm(ProcessoPadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

	public Collection<AtividadeInstanciada> getAtividadeInstanciada() {
		return atividadeInstanciada;
	}

	public void setAtividadeInstanciada(
			Collection<AtividadeInstanciada> atividadeInstanciada) {
		this.atividadeInstanciada = atividadeInstanciada;
	}
	 
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Ocorrência de Processo de Software")
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
	
}
 
