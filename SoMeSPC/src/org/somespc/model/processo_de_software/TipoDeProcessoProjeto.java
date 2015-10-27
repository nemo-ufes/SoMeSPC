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
package org.somespc.model.processo_de_software;

import java.util.*;

import javax.persistence.*;

import org.somespc.calculators.*;
import org.somespc.model.entidades_e_medidas.*;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;

@Entity
@Views({
	@View(members = "nome; descricao;"),
	@View(name = "Simple", members = "nome")
})
@Tabs({
	@Tab(properties = "nome", defaultOrder = "${nome} asc")
})
public class TipoDeProcessoProjeto extends EntidadeMensuravel
{

    @OneToMany(mappedBy = "tipoDeProcessoProjeto")
    private Collection<ProcessoProjeto> processoProjeto;


    public Collection<ProcessoProjeto> getProcessoProjeto() {
		return processoProjeto;
	}

	public void setProcessoProjeto(Collection<ProcessoProjeto> processoProjeto) {
		this.processoProjeto = processoProjeto;
	}

	@ManyToOne
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Tipo de Processo Projeto")
	    })
    public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel()
    {
	return tipoDeEntidadeMensuravel;
    }

    public void setTipoDeEntidadeMensuravel(
	    TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel)
    {
	this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
    }
    
    @PreCreate
    @PreUpdate
    public void ajusta()
    {
	if(tipoDeEntidadeMensuravel != null){
    	
    	String nomeEntidade = "Tipo de Processo de Projeto";
    	Query query = XPersistence.getManager().createQuery("from TipoDeEntidadeMensuravel t where t.nome = '" + nomeEntidade + "'");
    	TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = (TipoDeEntidadeMensuravel) query.getSingleResult();
    	
    	this.setTipoDeEntidadeMensuravel(tipoDeEntidadeMensuravel);
    }
    }//ajusta

}