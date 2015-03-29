/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */

package org.medcep.model.processo;

import java.util.*;

import javax.persistence.*;

import org.medcep.calculators.*;
import org.medcep.model.medicao.planejamento.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;


@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; baseadoEm; dependeDe; requer; produz; realizadoPor; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")	
})
@EntityValidator(
		value=AtividadeDeProjetoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class AtividadeDeProjeto extends AtividadeInstanciada {
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoDeProjeto_AtividadeDeProjeto"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeDeProjeto_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="processoDeProjeto_id")
	       }
	      )
	@ListProperties("nome")
	private Collection<ProcessoDeProjeto> processoDeProjeto;
	
	public Collection<ProcessoDeProjeto> getProcessoDeProjeto() {
		return processoDeProjeto;
	}

	public void setProcessoDeProjeto(Collection<ProcessoDeProjeto> processoDeProjeto) {
		this.processoDeProjeto = processoDeProjeto;
	}

	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Atividade de Projeto")
			 }
	)
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}
	
}
