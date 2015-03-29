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

package org.medcep.model.medicao.planejamento;

import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.medcep.model.organizacao.*;
import org.openxava.annotations.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View (members="nome, versao, date; descricao; recursoHumano; objetivos { objetivoEstrategico; objetivoDeSoftware; objetivoDeMedicao; necessidadeDeInformacao; } MedidasDoPlano { medidaPlanoDeMedicao }"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")	
})
public class PlanoDeMedicao {

	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
    @Column(length=500, unique=true) @Required
	private String nome;

	private Date date;
	 
	@Stereotype("TEXT_AREA")
	private String descricao;
	 
	private String versao;
	 
    @ManyToMany 
    @JoinTable(
  	      name="recursoHumano_planoDeMedicao"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="recursoHumano_id")
  	       }
  	      )
	private Collection<RecursoHumano> recursoHumano;
    
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoDeMedicao"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivoDeMedicao_id")
  	       }
  	      )
    private Collection<ObjetivoDeMedicao> objetivoDeMedicao;
	 
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoEstrategico"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivoEstrategico_id")
  	       }
  	      )
	private Collection<ObjetivoEstrategico> objetivoEstrategico;
	 
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoDeSoftware"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivoDeSoftware_id")
  	       }
  	      )
	private Collection<ObjetivoDeSoftware> objetivoDeSoftware;
	 
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_necessidadeDeInformacao"
  	      , joinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="necessidadeDeInformacao_id")
  	       }
  	      )
	private Collection<NecessidadeDeInformacao> necessidadeDeInformacao;
	 
    @OneToMany(mappedBy="planoDeMedicao", cascade=CascadeType.REMOVE)
    @ListProperties("medida.nome, definicaoOperacionalDeMedida.nome")
    @CollectionView("PlanoMedicao")
	private Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Collection<RecursoHumano> getRecursoHumano() {
		return recursoHumano;
	}

	public void setRecursoHumano(Collection<RecursoHumano> recursoHumano) {
		this.recursoHumano = recursoHumano;
	}

	public Collection<ObjetivoDeMedicao> getObjetivoDeMedicao() {
		return objetivoDeMedicao;
	}

	public void setObjetivoDeMedicao(Collection<ObjetivoDeMedicao> objetivoDeMedicao) {
		this.objetivoDeMedicao = objetivoDeMedicao;
	}

	public Collection<ObjetivoEstrategico> getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(
			Collection<ObjetivoEstrategico> objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

	public Collection<ObjetivoDeSoftware> getObjetivoDeSoftware() {
		return objetivoDeSoftware;
	}

	public void setObjetivoDeSoftware(
			Collection<ObjetivoDeSoftware> objetivoDeSoftware) {
		this.objetivoDeSoftware = objetivoDeSoftware;
	}

	public Collection<NecessidadeDeInformacao> getNecessidadeDeInformacao() {
		return necessidadeDeInformacao;
	}

	public void setNecessidadeDeInformacao(
			Collection<NecessidadeDeInformacao> necessidadeDeInformacao) {
		this.necessidadeDeInformacao = necessidadeDeInformacao;
	}

	public Collection<MedidaPlanoDeMedicao> getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(
			Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
    
    
	/*private Collection<Medicao> medicao;
	
	private Collection<AnaliseDeMedicao> analiseDeMedicao;*/
	 
}
 
