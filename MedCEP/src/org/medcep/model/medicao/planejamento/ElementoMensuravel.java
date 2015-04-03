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
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.medcep.model.medicao.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="nome; tipoElementoMensuravel; descricao"),
	@View(name="Simple", members="nome"),
	@View(name="SimpleNoFrame", members="nome")
	})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class ElementoMensuravel {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;   
    
    @Column(length=500, unique=true) @Required
	private String nome;
	
	@Stereotype("TEXT_AREA")
	@Column(columnDefinition="TEXT")
	private String descricao;
	
	@ManyToOne 
	@DescriptionsList(descriptionProperties="nome")
	//@NoCreate
	//@NoModify
	@Required
	private TipoElementoMensuravel tipoElementoMensuravel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public TipoElementoMensuravel getTipoElementoMensuravel() {
		return tipoElementoMensuravel;
	}

	public void setTipoElementoMensuravel(TipoElementoMensuravel tipoElementoMensuravel) {
		this.tipoElementoMensuravel = tipoElementoMensuravel;
	}

	public Collection<Medida> getMedida() {
		return medida;
	}

	public void setMedida(Collection<Medida> medida) {
		this.medida = medida;
	}

	public Collection<ElementoMensuravel> getSubelemento() {
		return subelemento;
	}

	public void setSubelemento(Collection<ElementoMensuravel> subelemento) {
		this.subelemento = subelemento;
	}

	public Collection<EntidadeMensuravel> getEntidadeMensuravel() {
		return entidadeMensuravel;
	}

	public void setEntidadeMensuravel(
			Collection<EntidadeMensuravel> entidadeMensuravel) {
		this.entidadeMensuravel = entidadeMensuravel;
	}

	@OneToMany(mappedBy="elementoMensuravel")
	private Collection<Medida> medida;

    @ManyToMany
    @JoinTable(
	      name="subelemento"
	      , joinColumns={
	    		  @JoinColumn(name="subelemento_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="subelemento_id2")
	       }
	      )
	private Collection<ElementoMensuravel> subelemento;
	 
    @ManyToMany
    @JoinTable(
	      name="elementoMensuravel_tipoDeEntidadeMensuravel"
	      , joinColumns={
	    		  @JoinColumn(name="elementoMensuravel_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="tipoDeEntidadeMensuravel_id")
	       }
	      )
	private Collection<TipoDeEntidadeMensuravel> tipoDeEntidadeMensuravel;
	
    @ManyToMany
    @JoinTable(
	      name="elementoMensuravel_entidadeMensuravel"
	      , joinColumns={
	    		  @JoinColumn(name="elementoMensuravel_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="entidadeMensuravel_id")
	       }
	      )
	private Collection<EntidadeMensuravel> entidadeMensuravel;
	
	@OneToMany(mappedBy="elementoMensuravel")
	private Collection<Medicao> medicao;

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}	
	

	public Collection<TipoDeEntidadeMensuravel> getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			Collection<TipoDeEntidadeMensuravel> tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}
	
}
 
