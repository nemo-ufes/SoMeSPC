/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
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

import org.somespc.model.entidades_e_medidas.*;
import org.somespc.model.objetivos.NecessidadeDeInformacao;
import org.somespc.model.plano_de_medicao.*;
import org.openxava.annotations.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	//@View(members="nome; necessidadeDeInformacao; indicadores; subobjetivo;"),
	@View(members = "nome"),
	@View(name = "Simple", members = "nome")
})
@Tab(properties = "nome", defaultOrder = "${nome} asc")
public class Objetivo extends ItemPlanoMedicaoBase
{

    @Column(length = 255, unique = true)
    @Required
    private String nome;

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    @ManyToMany
    @JoinTable(
	    name = "medida_objetivo"
	    , joinColumns = {
		    @JoinColumn(name = "objetivo_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "medida_id")
	    })
    @ListProperties("nome, mnemonico")
    private Collection<Medida> indicadores;

    @ManyToMany
    @JoinTable(
	    name = "subobjetivo"
	    , joinColumns = {
		    @JoinColumn(name = "objetivo_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "objetivo_id2")
	    })
    @ListProperties("nome")
    private Collection<Objetivo> subobjetivo;

    @ManyToMany
    @JoinTable(
	    name = "objetivo_identifica_necessidade"
	    , joinColumns = {
		    @JoinColumn(name = "objetivo_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "necessidadeDeInformacao_id")
	    })
    @ListProperties("nome")
    private Collection<NecessidadeDeInformacao> necessidadeDeInformacao;

    public Collection<Objetivo> getSubobjetivo()
    {
	return subobjetivo;
    }

    public void setSubobjetivo(Collection<Objetivo> subobjetivo)
    {
	this.subobjetivo = subobjetivo;
    }

    public Collection<NecessidadeDeInformacao> getNecessidadeDeInformacao()
    {
	return necessidadeDeInformacao;
    }

    public void setNecessidadeDeInformacao(
	    Collection<NecessidadeDeInformacao> necessidadeDeInformacao)
    {
	this.necessidadeDeInformacao = necessidadeDeInformacao;
    }

    public Collection<Medida> getIndicadores()
    {
	return indicadores;
    }

    public void setIndicadores(Collection<Medida> indicadores)
    {
	this.indicadores = indicadores;
    }

    @ManyToMany
    @JoinTable(
	    name = "objetivo_projeto"
	    , joinColumns = {
		    @JoinColumn(name = "projeto_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "objetivo_id")
	    })
    @ListProperties("nome")
    private Collection<Projeto> projeto;

    public Collection<Projeto> getProjeto()
    {
	return projeto;
    }

    public void setProjeto(Collection<Projeto> projeto)
    {
	this.projeto = projeto;
    }

}
