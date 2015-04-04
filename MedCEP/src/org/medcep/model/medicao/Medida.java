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

package org.medcep.model.medicao;

import java.util.*;

import javax.persistence.*;

import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.organizacao.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="nome, mnemonico; "
			+"tipoMedida; "
			+"escala; "
			+"unidadeDeMedida; " 
			+"tipoDeEntidadeMedida; "
			+"elementoMensuravel; "
			+"medidasCorrelatas; "
			+"grupo_medida_derivada [ " 
			+"derivaDe; "
			+"calculadaPor; ]; "
		
	),
	@View(name="ForMedicao", members="nome, mnemonico; "
			+"Tipo de Entidade Mensurável [ tipoDeEntidadeMedida.nome; ];"
			+"Elemento Mensurável [ elementoMensuravel.nome; ]"
	),
	@View(name="Simple", members="nome"),
	@View(name="SimpleNoFrame", members="nome")
})
@Tabs({
	@Tab(properties="nome, mnemonico", defaultOrder="${nome} asc")
})
@EntityValidator(
		value=MedidaValidator.class, 
		properties={
			@PropertyValue(name="tipoMedida"),
			@PropertyValue(name="elementoMensuravel")
		}
)
public class Medida extends TreeItemPlanoMedicaoBase {
 
    @Column(length=500, unique=true) 
    @Required 
    private String nome;
	
	@Stereotype("TEXT_AREA") 
	@Column(columnDefinition="TEXT")
	private String descricao;

	@Column(length=6) 
	private String mnemonico;
	
	@ManyToOne 
	@Required 
	@NoCreate
	@NoModify
	//@Editor("ValidValuesRadioButton")
	@DescriptionsList(descriptionProperties="nome", order="${nome} asc")
	private TipoMedida tipoMedida;

	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome", order="${nome} asc")
	@Required
	@NoFrame
	@ReferenceView("SimpleNoFrame")
    private TipoDeEntidadeMensuravel tipoDeEntidadeMedida;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMedida() {
		return tipoDeEntidadeMedida;
	}

	public void setTipoDeEntidadeMedida(
			TipoDeEntidadeMensuravel tipoDeEntidadeMedida) {
		this.tipoDeEntidadeMedida = tipoDeEntidadeMedida;
	}


	@ListProperties("nome")
	@OneToMany(mappedBy="medida")
	private Collection<DefinicaoOperacionalDeMedida> definicaoOperacionalDeMedida;

	public Collection<DefinicaoOperacionalDeMedida> getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(
			Collection<DefinicaoOperacionalDeMedida> definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}

	@ManyToOne
	@Required
	@ReferenceView("Simple")
	@SearchAction("Medida.searchElementoMensuravelForMedida")
    private ElementoMensuravel elementoMensuravel;
	
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome") 
	@Required 
	private Escala escala;
	 
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome") 
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
   
	@OneToMany(mappedBy="calcula", cascade = CascadeType.REMOVE)
	private Collection<FormulaDeCalculoDeMedida> calculadaPor;
		
	public Collection<FormulaDeCalculoDeMedida> getCalculadaPor() {
		return calculadaPor;
	}

	public void setCalculadaPor(Collection<FormulaDeCalculoDeMedida> calculadaPor) {
		this.calculadaPor = calculadaPor;
	}

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
	
	public boolean isIndicador(){
		return (listaObjetivos != null && listaObjetivos.size() > 0) || (listaNecessidadeDeInformacao != null && listaNecessidadeDeInformacao.size() > 0);
	}
		
}