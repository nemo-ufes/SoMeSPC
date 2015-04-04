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
import org.medcep.model.medicao.*;
import org.medcep.model.medicao.planejamento.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; descricao; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class TipoDeArtefato extends EntidadeMensuravel {
 
    /*
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    */
	
	@OneToMany(mappedBy="tipoDeArtefato")
	private Collection<Artefato> artefato;
	
	public Collection<Artefato> getArtefato() {
		return artefato;
	}

	public void setArtefato(Collection<Artefato> artefato) {
		this.artefato = artefato;
	}
		
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Tipo de Artefato")
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

	//removido por questões de navegabilidade
	//requerido
	//private Collection<AtividadePadrao> atividadePadrao;
	 
	//removido por questões de navegabilidade
	//produzido
	//private Collection<AtividadePadrao> atividadePadrao;
	 
	
}
 
