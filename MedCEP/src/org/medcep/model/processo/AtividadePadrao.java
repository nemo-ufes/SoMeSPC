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
import org.medcep.model.medicao.planejamento.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;

/**
 * Atividade Analisar Dados de Monitoramento do Projeto; Atividade Homologar Especificação de Requisitos
 */
@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; descricao; requerTipoDeArtefato; produzTipoDeArtefato; dependeDe; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
@EntityValidator(
		value=AtividadePadraoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class AtividadePadrao extends EntidadeMensuravel {
 
	@OneToMany(mappedBy="momentoDeMedicao")
	private Collection<DefinicaoOperacionalDeMedida> momentoDeMedicao;
	
	@OneToMany(mappedBy="momentoDeAnaliseDeMedicao")
	private Collection<DefinicaoOperacionalDeMedida> momentoDeAnaliseDeMedicao;
	
	@OneToMany(mappedBy="baseadoEm")
	private Collection<AtividadeInstanciada> atividadeDeProjeto;
	
	//private Collection<ProcessoDeSoftwarePadrao> processoDeSoftwarePadrao;

	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Atividade Padrão")
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

	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
	      name="AtividadePadrao_dependeDe_AtividadePadrao"
	      , joinColumns={
	    		  @JoinColumn(name="atividadePadrao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="atividadePadrao_id2")
	       }
	      )
	@ListProperties("nome") 
	private Collection<AtividadePadrao> dependeDe;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadePadrao_produz_TipoArtefato"
	      , joinColumns={
	    		  @JoinColumn(name="atividadePadrao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="tipoArtefato_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<TipoDeArtefato> produzTipoDeArtefato;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadePadrao_requer_TipoArtefato"
	      , joinColumns={
	    		  @JoinColumn(name="atividadePadrao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="tipoArtefato_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<TipoDeArtefato> requerTipoDeArtefato;

	public Collection<AtividadeInstanciada> getAtividadeDeProjeto() {
		return atividadeDeProjeto;
	}

	public void setAtividadeDeProjeto(
			Collection<AtividadeInstanciada> atividadeDeProjeto) {
		this.atividadeDeProjeto = atividadeDeProjeto;
	}

/*	public Collection<ProcessoDeSoftwarePadrao> getProcessoDeSoftwarePadrao() {
		return processoDeSoftwarePadrao;
	}

	public void setProcessoDeSoftwarePadrao(
			Collection<ProcessoDeSoftwarePadrao> processoDeSoftwarePadrao) {
		this.processoDeSoftwarePadrao = processoDeSoftwarePadrao;
	}*/

	public Collection<AtividadePadrao> getDependeDe() {
		return dependeDe;
	}

	public void setDependeDe(Collection<AtividadePadrao> dependeDe) {
		this.dependeDe = dependeDe;
	}

	public Collection<TipoDeArtefato> getProduzTipoDeArtefato() {
		return produzTipoDeArtefato;
	}

	public void setProduzTipoDeArtefato(
			Collection<TipoDeArtefato> produzTipoDeArtefato) {
		this.produzTipoDeArtefato = produzTipoDeArtefato;
	}

	public Collection<TipoDeArtefato> getRequerTipoDeArtefato() {
		return requerTipoDeArtefato;
	}

	public void setRequerTipoDeArtefato(
			Collection<TipoDeArtefato> requerTipoDeArtefato) {
		this.requerTipoDeArtefato = requerTipoDeArtefato;
	}

	public Collection<DefinicaoOperacionalDeMedida> getMomentoDeMedicao() {
		return momentoDeMedicao;
	}

	public void setMomentoDeMedicao(
			Collection<DefinicaoOperacionalDeMedida> momentoDeMedicao) {
		this.momentoDeMedicao = momentoDeMedicao;
	}

	public Collection<DefinicaoOperacionalDeMedida> getMomentoDeAnaliseDeMedicao() {
		return momentoDeAnaliseDeMedicao;
	}

	public void setMomentoDeAnaliseDeMedicao(
			Collection<DefinicaoOperacionalDeMedida> momentoDeAnaliseDeMedicao) {
		this.momentoDeAnaliseDeMedicao = momentoDeAnaliseDeMedicao;
	}
	
}
 
