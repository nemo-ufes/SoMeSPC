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
package org.medcep.model.medicao;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

/**
 * Taxa de Alteração de requisitos = Requisitos alterado / Requisitos Homologados
 */
@Entity
@View(name = "Simple", members = "nome, data; formula")
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc")
})
public class FormulaDeCalculoDeMedida
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

    @Required
    private String formula;

    //o ideal seria ao remover a formula fosse removido a referencia de medida sem excluir ela, mas ja tentei varias coisas e nd
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.REFRESH)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.ALL)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.DETACH)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.MERGE)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.PERSIST)
    //@OneToOne(mappedBy = "calculadaPor", orphanRemoval=true)
    //@OneToOne(mappedBy = "calculadaPor", optional=true)
    @ManyToOne
    @Required
    @ReferenceView("Simple")
    @NoCreate
    @NoModify
    private Medida calcula;

    /* private Collection<ProcedimentoDeMedicao> procedimentoDeMedicao; */

    private Date data;

    public Date getData()
    {
	return data;
    }

    public void setData(Date data)
    {
	this.data = data;
    }

    @ManyToMany
    @JoinTable(
	    name = "formulaDeCalculoDeMedida_usa_medida"
	    , joinColumns = {
		    @JoinColumn(name = "formulaDeCalculoDeMedida_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "medida_id")
	    })
    @ListProperties("nome, mnemonico")
    private Collection<Medida> usaMedidas;

    public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public String getFormula()
    {
	return formula;
    }

    public void setFormula(String formula)
    {
	this.formula = formula;
    }

    public Medida getCalcula()
    {
	return calcula;
    }

    public void setCalcula(Medida calcula)
    {
	this.calcula = calcula;
    }

    public Collection<Medida> getUsaMedidas()
    {
	return usaMedidas;
    }

    public void setUsaMedidas(Collection<Medida> usaMedidas)
    {
	this.usaMedidas = usaMedidas;
    }

}
