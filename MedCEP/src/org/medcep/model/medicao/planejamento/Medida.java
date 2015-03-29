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
@Views({
	@View(members="Medida [nome; mnemonico; "
			+"tipoMedida,  			"
			+"tipoDeEntidadeMedida; 		"
			+"unidadeDeMedida,   		" 
			+"entidadeMedida;   		"
			+"elementoMensuravel, 		"
			+"escala];"
			+"medidasCorrelatas; "
			//+"listaObjetivos; "
			+"Para Medidas Derivadas: [ calculadaPor; "
			+"derivaDe; ]"
	),
	@View(name="ForMedicao", members="nome, mnemonico; "
			+"Entidade Mensurável [ entidadeMedida.nome; ];"
			+"Elemento Mensurável [ elementoMensuravel.nome; ]"
	),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome, mnemonico", defaultOrder="${nome} asc")
})
public class Medida {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
    @Column(length=500, unique=true) 
    @Required 
    private String nome;
	
	@Stereotype("TEXT_AREA") 
	private String descricao;

	@Column(length=6) 
	private String mnemonico;
	
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome") 
	@Required 
	private TipoMedida tipoMedida;

	@ManyToOne 
	@DescriptionsList(
			descriptionProperties="nome"
			)
	//@Required
    private TipoDeEntidadeMensuravel tipoDeEntidadeMedida;
	
	@ManyToOne 
	@DescriptionsList(
			descriptionProperties="nome"
			,depends="tipoDeEntidadeMedida"
			,condition="${tipoDeEntidadeMensuravel.id} = ?"
			,order="${nome} asc"
			)
	//@Required
    private EntidadeMensuravel entidadeMedida;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMedida() {
		return tipoDeEntidadeMedida;
	}

	public void setTipoDeEntidadeMedida(
			TipoDeEntidadeMensuravel tipoDeEntidadeMedida) {
		this.tipoDeEntidadeMedida = tipoDeEntidadeMedida;
	}

	public EntidadeMensuravel getEntidadeMedida() {
		return entidadeMedida;
	}

	public void setEntidadeMedida(EntidadeMensuravel entidadeMedida) {
		this.entidadeMedida = entidadeMedida;
	}

	@ManyToOne
	@DescriptionsList(
			descriptionProperties="nome"
			/*,depends="entidadeMedida"
			,condition="${entidadeMensuravel.id} = ?"
			//,condition="?.id IN (SELECT id from ${entidadeMensuravel})"
			,order="${nome} asc"*/
			)
	//@Required
	//@Condition("${id} IN (SELECT id from ${elementoMensuravel.entidadeMensuravel})")
    private ElementoMensuravel elementoMensuravel;
	
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome") 
	//@Required 
	private Escala escala;
	 
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome") 
	//@Required
	private UnidadeDeMedida unidadeDeMedida;
	
    @ManyToMany 
    @JoinTable(
	      name="medida_correlatas"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id2")
	       }
	      )
    @ListProperties("nome, mnemonico")
	private Collection<Medida> medidasCorrelatas;	
    
    @ManyToMany 
    @JoinTable(
	      name="medida_derivaDe"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id2")
	       }
	      )
    @ListProperties("nome, mnemonico")
	private Collection<Medida> derivaDe;
	
    public Collection<Medida> getDerivaDe() {
		return derivaDe;
	}

	public void setDerivaDe(Collection<Medida> derivaDe) {
		this.derivaDe = derivaDe;
	}

	public Collection<Medida> getMedidasCorrelatas() {
		return medidasCorrelatas;
	}

	public void setMedidasCorrelatas(Collection<Medida> medidasCorrelatas) {
		this.medidasCorrelatas = medidasCorrelatas;
	}

	@ManyToMany 
    @JoinTable(
	      name="medida_objetivo"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="objetivo_id")
	       }
	      )
	private Collection<Objetivo> listaObjetivos;	
	
	public Collection<Objetivo> getListaObjetivos() {
		return listaObjetivos;
	}

	public void setListaObjetivos(Collection<Objetivo> listaObjetivos) {
		this.listaObjetivos = listaObjetivos;
	}

    @ManyToMany
    @JoinTable(
	      name="medida_necessidadeDeInformacao"
	      , joinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="necessidadeDeInformacao_id")
	       }
	      )
    private Collection<NecessidadeDeInformacao> listaNecessidadeDeInformacao;


	public Collection<NecessidadeDeInformacao> getListaNecessidadeDeInformacao() {
		return listaNecessidadeDeInformacao;
	}

	public void setListaNecessidadeDeInformacao(Collection<NecessidadeDeInformacao> listaNecessidadeDeInformacao) {
		this.listaNecessidadeDeInformacao = listaNecessidadeDeInformacao;
	}	

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

	public String getMnemonico() {
		return mnemonico;
	}

	public void setMnemonico(String mnemonico) {
		this.mnemonico = mnemonico;
	}

    /*@OneToMany(mappedBy="medida") 
	private Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao;*/

	public TipoMedida getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(TipoMedida tipo) {
		this.tipoMedida = tipo;
	}

	public ElementoMensuravel getElementoMensuravel() {
		return elementoMensuravel;
	}

	public void setElementoMensuravel(ElementoMensuravel elementoMensuravel) {
		this.elementoMensuravel = elementoMensuravel;
	}

/*	public Collection<MedidaPlanoDeMedicao> getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}*/
    
    public FormulaDeCalculoDeMedida getCalculadaPor() {
		return calculadaPor;
	}

	public void setCalculadaPor(FormulaDeCalculoDeMedida calculadaPor) {
		this.calculadaPor = calculadaPor;
	}

	//@OneToOne(cascade=CascadeType.REFRESH)
	//@OneToOne(cascade=CascadeType.REMOVE)
	@OneToOne(cascade=CascadeType.ALL)
	//@OneToOne
    //@PrimaryKeyJoinColumn
    @ReferenceView("Simple")
	private FormulaDeCalculoDeMedida calculadaPor;
	
	public Escala getEscala() {
		return escala;
	}

	public void setEscala(Escala escala) {
		this.escala = escala;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}
	
/*	@OneToMany(mappedBy="medida")
	private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;

	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}*/
    
/*	@OneToMany(mappedBy="medida")
	private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;*/
	
/*	@OneToMany(mappedBy="medida")
	private Collection<ModeloPreditivo> modeloPreditivo;*/

/*	public Collection<CapacidadeDeProcesso> getCapacidadeDeProcesso() {
		return capacidadeDeProcesso;
	}

	public void setCapacidadeDeProcesso(
			Collection<CapacidadeDeProcesso> capacidadeDeProcesso) {
		this.capacidadeDeProcesso = capacidadeDeProcesso;
	}*/

/*	public Collection<ModeloPreditivo> getModeloPreditivo() {
		return modeloPreditivo;
	}

	public void setModeloPreditivo(Collection<ModeloPreditivo> modeloPreditivo) {
		this.modeloPreditivo = modeloPreditivo;
	}*/
	
	
	
}