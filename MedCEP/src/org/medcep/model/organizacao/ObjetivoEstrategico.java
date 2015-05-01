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

import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome;"			
	),
	@View(name = "Simple", members = "nome"),
})
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc")
})
public class ObjetivoEstrategico extends Objetivo
{

    @ManyToMany
    @JoinTable(
	    name = "ObjetivoDeSoftware_BaseadoEm_ObjetivoEstrategico"
	    ,
	    joinColumns = {
		    @JoinColumn(name = "ObjetivoEstrategico")
	    }
	    ,
	    inverseJoinColumns = {
		    @JoinColumn(name = "ObjetivoDeSoftware")
	    })
    @ListProperties("nome")
    private Collection<ObjetivoDeSoftware> objetivoDeSoftware;

    @ManyToMany
    @JoinTable(
	    name = "ObjetivoDeMedicao_BaseadoEm_ObjetivoEstrategico"
	    , joinColumns = {
		    @JoinColumn(name = "ObjetivoEstrategico")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "ObjetivoDeMedicao")
	    })
    @ListProperties("nome")
    private Collection<ObjetivoDeMedicao> objetivoDeMedicao;

    public Collection<ObjetivoDeSoftware> getObjetivoDeSoftware()
    {
	return objetivoDeSoftware;
    }

    public void setObjetivoDeSoftware(
	    Collection<ObjetivoDeSoftware> objetivoDeSoftware)
    {
	this.objetivoDeSoftware = objetivoDeSoftware;
    }

    public Collection<ObjetivoDeMedicao> getObjetivoDeMedicao()
    {
	return objetivoDeMedicao;
    }

    public void setObjetivoDeMedicao(Collection<ObjetivoDeMedicao> objetivoDeMedicao)
    {
	this.objetivoDeMedicao = objetivoDeMedicao;
    }
}
