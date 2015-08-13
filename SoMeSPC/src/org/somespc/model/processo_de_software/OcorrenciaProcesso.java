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
package org.somespc.model.processo_de_software;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import org.somespc.calculators.*;
import org.somespc.model.entidades_e_medidas.*;
import org.somespc.validators.*;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;

@Entity
@Views({
	@View(members = "nome; processoProjetoOcorrido"),
	@View(name = "Simple", members = "nome"),
})
@Tab(properties = "nome", defaultOrder = "${nome} asc", baseCondition = "TYPE(e) = OcorrenciaProcesso")
@EntityValidator(
	value = ProcessoInstanciadoValidator.class,
	properties = {
		@PropertyValue(name = "tipoDeEntidadeMensuravel")
	})
@XmlRootElement
public class OcorrenciaProcesso extends EntidadeMensuravel
{

    @ManyToOne
    @Required
    @ReferenceView("Simple")
    private ProcessoProjeto processoProjetoOcorrido;

    @ManyToOne
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Ocorrência de Processo de Software")
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

    public ProcessoProjeto getProcessoProjetoOcorrido()
    {
	return processoProjetoOcorrido;
    }

    public void setProcessoProjetoOcorrido(ProcessoProjeto processoProjetoOcorrido)
    {
	this.processoProjetoOcorrido = processoProjetoOcorrido;
    }
    
    @PreCreate
    @PreUpdate
    public void ajusta()
    {
	if(tipoDeEntidadeMensuravel != null){
    	
    	String nomeEntidade = "Ocorrência de Processo de Software";
    	Query query = XPersistence.getManager().createQuery("from TipoDeEntidadeMensuravel t where t.nome = '" + nomeEntidade + "'");
    	TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = (TipoDeEntidadeMensuravel) query.getSingleResult();
    	
    	this.setTipoDeEntidadeMensuravel(tipoDeEntidadeMensuravel);
    }
    }//ajusta

}
