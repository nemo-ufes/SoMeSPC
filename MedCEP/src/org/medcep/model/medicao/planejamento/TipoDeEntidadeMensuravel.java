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
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="nome; descricao; elementoMensuravel"),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class TipoDeEntidadeMensuravel {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;   
    
	@Column(length=500, unique=true) @Required
	private String nome;

	@Stereotype("TEXT_AREA")
	private String descricao;

	@OneToMany(mappedBy="tipoDeEntidadeMensuravel")
	private Collection<EntidadeMensuravel> entidadeMensuravel;
	
    @ManyToMany
    @JoinTable(
	      name="elementoMensuravel_tipoDeEntidadeMensuravel"
	      , joinColumns={
	    		  @JoinColumn(name="tipoDeEntidadeMensuravel_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="elementoMensuravel_id")
	       }
	      )
	private Collection<ElementoMensuravel> elementoMensuravel;

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

	public Collection<EntidadeMensuravel> getEntidadeMensuravel() {
		return entidadeMensuravel;
	}

	public void setEntidadeMensuravel(
			Collection<EntidadeMensuravel> entidadeMensuravel) {
		this.entidadeMensuravel = entidadeMensuravel;
	}

	public Collection<ElementoMensuravel> getElementoMensuravel() {
		return elementoMensuravel;
	}

	public void setElementoMensuravel(
			Collection<ElementoMensuravel> elementoMensuravel) {
		this.elementoMensuravel = elementoMensuravel;
	}

    
    
}
 
