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
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; descricao; elementosMensuraveis"),
	@View(name="Simple", members="nome"),
	@View(name="SimpleNoFrame", members="nome"),
	})
@Tab(properties="nome, tipoDeEntidadeMensuravel.nome", defaultOrder="${nome} asc")
public class EntidadeMensuravel {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;   
    
    @Column(length=500, unique=true) @Required
	private String nome;
	
	@Stereotype("TEXT_AREA") 
	@Column(columnDefinition="TEXT")
	private String descricao;
	
	@ManyToOne 
	@Required
	@NoModify
	@DescriptionsList(descriptionProperties="nome") 
	protected TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}	 
		
	@Transient
	@ReadOnly
	public Collection<ElementoMensuravel> getElementosMensuraveis() {
		return tipoDeEntidadeMensuravel.getElementoMensuravel();
	}
	
	public void setElementosMensuraveis(Collection<ElementoMensuravel> elementosMensuraveis){		
	}
	
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
		
	@OneToMany(mappedBy="entidadeMensuravel")
	private Collection<Medicao> medicao;

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}


}
 
