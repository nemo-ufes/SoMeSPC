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
package org.somespc.model.objetivos;

import java.util.*;

import javax.persistence.*;

import org.somespc.model.entidades_e_medidas.*;
import org.somespc.model.organizacao_de_software.*;
import org.somespc.model.plano_de_medicao.ItemPlanoMedicaoBase;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome;"
		+ " subnecessidade;"
		+ " indicadoPelosObjetivos;"
		+ " medidas;"
	),
	@View(name = "Simple", members = "nome")
})
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc")
})
public class NecessidadeDeInformacao extends ItemPlanoMedicaoBase
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
	    name = "subnecessidade"
	    , joinColumns = {
		    @JoinColumn(name = "necessidadeDeInformacao1", referencedColumnName = "id", nullable = false)
	    }
	    , inverseJoinColumns = {
	    	@JoinColumn(name = "necessidadeDeInformacao2", referencedColumnName = "id", nullable = false)
	    })
    
    @ListAction("ManyToMany.new")
    @NewAction("NecessidadeDeInformacao.addNecessidadeDeInformacao")
    @ListProperties("nome")
    private Collection<NecessidadeDeInformacao> subnecessidade;

    @ManyToMany
    @JoinTable(
	    name = "objetivo_identifica_necessidade"
	    , joinColumns = {
		    @JoinColumn(name = "necessidadeDeInformacao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "objetivo_id")
	    })
    @ListProperties("nome")
    @NewAction("NecessidadeDeInformacao.addObjetivoDeMedicao")
    private Collection<Objetivo> indicadoPelosObjetivos;

    public Collection<Objetivo> getIndicadoPelosObjetivos()
    {
	return indicadoPelosObjetivos;
    }

    public void setIndicadoPelosObjetivos(
	    Collection<Objetivo> indicadoPelosObjetivos)
    {
	this.indicadoPelosObjetivos = indicadoPelosObjetivos;
    }

    @ManyToMany
    @JoinTable(
	    name = "medida_necessidadeDeInformacao"
	    , joinColumns = {
		    @JoinColumn(name = "necessidadeDeInformacao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "medida_id")
	    })
    @ListProperties("nome, tipoMedida.nome, mnemonico")
    private Collection<Medida> medidas;

    public Collection<Medida> getMedidas()
    {
	return medidas;
    }

    public void setMedidas(Collection<Medida> medidas)
    {
	this.medidas = medidas;
    }

    public Collection<NecessidadeDeInformacao> getSubnecessidade()
    {
	return subnecessidade;
    }

    public void setSubnecessidade(Collection<NecessidadeDeInformacao> subnecessidade)
    {
	this.subnecessidade = subnecessidade;
    }
}
