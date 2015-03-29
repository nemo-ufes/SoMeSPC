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
import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.organizacao.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; baseadoEm; dependeDe; requer; produz; realizadoPor; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc", baseCondition="TYPE(e) = AtividadeInstanciada")	
})
@EntityValidator(
		value=AtividadeInstanciadaValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class AtividadeInstanciada extends EntidadeMensuravel {
 
	@ManyToOne
	@ReferenceView("Simple")
	//@SearchAction("AtividadeInstanciada.search")
	private AtividadePadrao baseadoEm;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoInstanciado_AtividadeInstanciada"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="processoInstanciado_id")
	       }
	      )
	private Collection<ProcessoInstanciado> processoInstanciado;
		
	public Collection<ProcessoInstanciado> getProcessoInstanciado() {
		return processoInstanciado;
	}

	public void setProcessoInstanciado(
			Collection<ProcessoInstanciado> processoInstanciado) {
		this.processoInstanciado = processoInstanciado;
	}

	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_dependeDe_AtividadeInstanciada"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id2")
	       }
	      )
	@ListProperties("nome") 
	private Collection<AtividadeInstanciada> dependeDe;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_RealizadoPor_RecursoHumano"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="recursoHumano_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<RecursoHumano> realizadoPor;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_produz_Artefato"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="artefato_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<Artefato> produz;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_requer_Artefato"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="artefato_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<Artefato> requer;
	 
/*	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_adota_Procedimento"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="procedimento_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<Procedimento> adotaProcedimento;*/

	public AtividadePadrao getBaseadoEm() {
		return baseadoEm;
	}

	public void setBaseadoEm(AtividadePadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

		public Collection<AtividadeInstanciada> getDependeDe() {
		return dependeDe;
	}

	public void setDependeDe(Collection<AtividadeInstanciada> dependeDe) {
		this.dependeDe = dependeDe;
	}

	public Collection<RecursoHumano> getRealizadoPor() {
		return realizadoPor;
	}

	public void setRealizadoPor(Collection<RecursoHumano> realizadoPor) {
		this.realizadoPor = realizadoPor;
	}

	public Collection<Artefato> getProduz() {
		return produz;
	}

	public void setProduz(Collection<Artefato> produz) {
		this.produz = produz;
	}

	public Collection<Artefato> getRequer() {
		return requer;
	}

	public void setRequer(Collection<Artefato> requer) {
		this.requer = requer;
	}

	public Collection<Artefato> getProduto() {
		return produz;
	}

	public void setProduto(Collection<Artefato> produto) {
		this.produz = produto;
	}

	public Collection<Artefato> getArtefato() {
		return requer;
	}

	public void setArtefato(Collection<Artefato> artefato) {
		this.requer = artefato;
	}

/*	public Collection<Procedimento> getAdotaProcedimento() {
		return adotaProcedimento;
	}

	public void setAdotaProcedimento(Collection<Procedimento> adotaProcedimento) {
		this.adotaProcedimento = adotaProcedimento;
	}*/
	 	
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Atividade Instanciada")
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
 
