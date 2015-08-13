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
package org.somespc.model.definicao_operacional_de_medida;

import java.util.*;

import javax.persistence.*;

import org.somespc.model.processo_de_software.*;
import org.openxava.annotations.*;

/**
 * Representar graficamente os valores medidos para a
 * medida em análise. ....
 */
@Entity
@Views({
	@View(members = "nome, ehBaseadoemCriterios; descricao; metodoAnalitico;"),
	@View(name = "Simple", members = "nome"),
})
@Tabs({
	@Tab(properties = "nome, ehBaseadoemCriterios", defaultOrder = "${nome} asc")
})
public class ProcedimentoDeAnaliseDeMedicao extends Procedimento
{

    private boolean ehBaseadoemCriterios;

    @ManyToMany
    @JoinTable(
	    name = "procedimentoDeAnaliseDeMedicao_metodoAnalitico"
	    , joinColumns = {
		    @JoinColumn(name = "procedimentoDeAnaliseDeMedicao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "metodoAnalitico_id")
	    })
    @ListProperties("nome, ehMetodoCEP")
    private Collection<MetodoAnalitico> metodoAnalitico;

    public boolean isEhBaseadoemCriterios()
    {
	return ehBaseadoemCriterios;
    }

    public void setEhBaseadoemCriterios(boolean ehBaseadoemCriterios)
    {
	this.ehBaseadoemCriterios = ehBaseadoemCriterios;
    }

    public Collection<MetodoAnalitico> getMetodoAnalitico()
    {
	return metodoAnalitico;
    }

    public void setMetodoAnalitico(Collection<MetodoAnalitico> metodoAnalitico)
    {
	this.metodoAnalitico = metodoAnalitico;
    }
  
}
