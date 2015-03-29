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

package org.medcep.model.organizacao;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.medcep.model.medicao.planejamento.*;
import org.openxava.annotations.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="Objetivo [nome; necessidadeDeInformacao; indicadores; subobjetivo]"),
	@View(name="Simple", members="nome")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
public class Objetivo { 
	 
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
    
    @ManyToMany
    @JoinTable(
	      name="medida_objetivo"
	      , joinColumns={
	    		  @JoinColumn(name="objetivo_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      )
    @ListProperties("nome, mnemonico")	
	private Collection<Medida> indicadores;
    
    @ManyToMany
    @JoinTable(
	      name="subobjetivo"
	      , joinColumns={
	    		  @JoinColumn(name="objetivo_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="objetivo_id2")
	       }
	      )
	private Collection<Objetivo> subobjetivo;
        
    @ManyToMany
    @JoinTable(
  	      name="objetivo_identifica_necessidade"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivo_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="necessidadeDeInformacao_id")
  	       }
  	      )
    private Collection<NecessidadeDeInformacao> necessidadeDeInformacao;

	public Collection<Objetivo> getSubobjetivo() {
		return subobjetivo;
	}

	public void setSubobjetivo(Collection<Objetivo> subobjetivo) {
		this.subobjetivo = subobjetivo;
	}

	public Collection<NecessidadeDeInformacao> getNecessidadeDeInformacao() {
		return necessidadeDeInformacao;
	}

	public void setNecessidadeDeInformacao(
			Collection<NecessidadeDeInformacao> necessidadeDeInformacao) {
		this.necessidadeDeInformacao = necessidadeDeInformacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Medida> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(Collection<Medida> indicadores) {
		this.indicadores = indicadores;
	}

    @ManyToMany
    @JoinTable(
  	      name="objetivo_projeto"
  	      , joinColumns={
  	    		  @JoinColumn(name="projeto_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivo_id")
  	       }
  	      )
	private Collection<Projeto> projeto;

	public Collection<Projeto> getProjeto() {
		return projeto;
	}

	public void setProjeto(Collection<Projeto> projeto) {
		this.projeto = projeto;
	}
    
    
}
 
 
