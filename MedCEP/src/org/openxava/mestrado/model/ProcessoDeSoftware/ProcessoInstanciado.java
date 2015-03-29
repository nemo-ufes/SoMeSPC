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

package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;

@Entity
@Views({
	@View(members="Processo Instanciado [nome; versao; tipoDeEntidadeMensuravel; baseadoEm]; atividadeInstanciada; elementoMensuravel;"),
	@View(name="Simple", members="nome"),
	})
@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")
public class ProcessoInstanciado extends EntidadeMensuravel {
    
	@Required
	private String versao;
	
	@ManyToOne @Required
	@ReferenceView("Simple")
	private ProcessoPadrao  baseadoEm;
	
	@OneToMany(mappedBy="processoInstanciado")
	private Collection<AtividadeInstanciada> atividadeInstanciada;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public ProcessoPadrao getProcessoDeSoftwarePadrao() {
		return baseadoEm;
	}

	public void setProcessoDeSoftwarePadrao(
			ProcessoPadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

	public ProcessoPadrao getBaseadoEm() {
		return baseadoEm;
	}

	public void setBaseadoEm(ProcessoPadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

	public Collection<AtividadeInstanciada> getAtividadeInstanciada() {
		return atividadeInstanciada;
	}

	public void setAtividadeInstanciada(
			Collection<AtividadeInstanciada> atividadeInstanciada) {
		this.atividadeInstanciada = atividadeInstanciada;
	}
	 
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Processo de Software Instanciado")
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
 
