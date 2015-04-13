/*
 * MedCEP - A powerful tool for measure
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
package org.medcep.model.organizacao;

import java.util.*;

import javax.persistence.*;

import org.medcep.model.medicao.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome; descricao"),
	@View(name = "Simple", members = "nome")
})
@Tab(properties = "nome", defaultOrder = "${nome} asc")
public class PapelRecursoHumano
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(length = 500, unique = true)
    @Required
    private String nome;

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @OneToMany(mappedBy = "responsavelPelaMedicao")
    private Collection<DefinicaoOperacionalDeMedida> responsavelPelaMedicao;

    @OneToMany(mappedBy = "responsavelPelaAnaliseDeMedicao")
    private Collection<DefinicaoOperacionalDeMedida> responsavelPelaAnaliseDeMedicao;

    @OneToMany(mappedBy = "papelRecursoHumano")
    private Collection<AlocacaoEquipe> alocacaoEquipe;

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public Collection<AlocacaoEquipe> getAlocacaoEquipe()
    {
	return alocacaoEquipe;
    }

    public void setAlocacaoEquipe(Collection<AlocacaoEquipe> alocacaoEquipe)
    {
	this.alocacaoEquipe = alocacaoEquipe;
    }

    public Collection<DefinicaoOperacionalDeMedida> getResponsavelPelaMedicao()
    {
	return responsavelPelaMedicao;
    }

    public void setResponsavelPelaMedicao(
	    Collection<DefinicaoOperacionalDeMedida> responsavelPelaMedicao)
    {
	this.responsavelPelaMedicao = responsavelPelaMedicao;
    }

    public Collection<DefinicaoOperacionalDeMedida> getResponsavelPelaAnaliseDeMedicao()
    {
	return responsavelPelaAnaliseDeMedicao;
    }

    public void setResponsavelPelaAnaliseDeMedicao(
	    Collection<DefinicaoOperacionalDeMedida> responsavelPelaAnaliseDeMedicao)
    {
	this.responsavelPelaAnaliseDeMedicao = responsavelPelaAnaliseDeMedicao;
    }

}
