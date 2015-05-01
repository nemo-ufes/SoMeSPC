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
import javax.xml.bind.annotation.*;

import org.medcep.model.medicao.*;
import org.medcep.model.processo.comportamento.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome;"),
	@View(name = "Simple", members = "nome")
})
@Tab(properties = "nome", defaultOrder = "${nome} asc")
@XmlRootElement
public class RecursoHumano
{
    @Id
    @TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "RECURSO_HUMANO_ID", valueColumnName = "ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
    @Hidden
    private Integer id;

    @Column(length = 255, unique = true)
    @Required
    private String nome;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

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
	    name = "equipe_recursoHumano"
	    , joinColumns = {
		    @JoinColumn(name = "recursoHumano_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "equipe_id")
	    })
    private Collection<Equipe> equipe;

    @XmlTransient
    public Collection<Equipe> getEquipe()
    {
	return equipe;
    }

    public void setEquipe(Collection<Equipe> equipe)
    {
	this.equipe = equipe;
    }

    @OneToMany(mappedBy = "recursoHumano")
    private Collection<AlocacaoEquipe> alocacaoEquipe;

    @XmlTransient
    public Collection<AlocacaoEquipe> getAlocacaoEquipe()
    {
	return alocacaoEquipe;
    }

    public void setAlocacaoEquipe(Collection<AlocacaoEquipe> alocacaoEquipe)
    {
	this.alocacaoEquipe = alocacaoEquipe;
    }

    @OneToMany(mappedBy = "executorDaMedicao")
    private Collection<Medicao> medicaoExecutada;

    @XmlTransient
    public Collection<Medicao> getMedicaoExecutada()
    {
	return medicaoExecutada;
    }

    public void setMedicaoExecutada(Collection<Medicao> medicaoExecutada)
    {
	this.medicaoExecutada = medicaoExecutada;
    }

    @OneToMany(mappedBy = "registradoPor")
    private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;

    @XmlTransient
    public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso()
    {
	return baselineDeDesempenhoDeProcesso;
    }

    public void setBaselineDeDesempenhoDeProcesso(
	    Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso)
    {
	this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
    }

}
