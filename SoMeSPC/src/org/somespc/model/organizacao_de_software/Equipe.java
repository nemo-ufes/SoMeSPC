/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique N�spoli Castro, Vin�cius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.somespc.model.organizacao_de_software;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome; Membros { recursoHumano; }, Aloca��es { alocacaoEquipe }"),
	@View(name = "Simple", members = "nome")
})
@Tab(properties = "nome", defaultOrder = "${nome} asc")
public class Equipe
{

    @Id
    @TableGenerator(name="TABLE_GENERATOR", table="ID_TABLE", pkColumnName="ID_TABLE_NAME", pkColumnValue="EQUIPE_ID", valueColumnName="ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="TABLE_GENERATOR")
    @Hidden
    private Integer id;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Column(length = 255, unique = true)
    @Required
    private String nome;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.REMOVE)
    @ListProperties("recursoHumano.nome, papelRecursoHumano.nome, inicio, fim")
    private Collection<AlocacaoEquipe> alocacaoEquipe;

    @ManyToMany
    @JoinTable(
	    name = "equipe_projeto"
	    , joinColumns = {
		    @JoinColumn(name = "equipe_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "projeto_id")
	    })
    private Collection<Projeto> projeto;

    @ManyToMany
    @JoinTable(
	    name = "equipe_recursoHumano"
	    , joinColumns = {
		    @JoinColumn(name = "equipe_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "recursoHumano_id")
	    })
    @ListProperties("nome")
    private Collection<RecursoHumano> recursoHumano;

    public Collection<RecursoHumano> getRecursoHumano()
    {
	return recursoHumano;
    }

    public void setRecursoHumano(Collection<RecursoHumano> recursoHumano)
    {
	this.recursoHumano = recursoHumano;
    }

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public Collection<AlocacaoEquipe> getAlocacaoEquipe()
    {
	return alocacaoEquipe;
    }

    public void setAlocacaoEquipe(Collection<AlocacaoEquipe> alocacaoEquipe)
    {
	this.alocacaoEquipe = alocacaoEquipe;
    }

    public Collection<Projeto> getProjeto()
    {
	return projeto;
    }

    public void setProjeto(Collection<Projeto> projeto)
    {
	this.projeto = projeto;
    }

}
