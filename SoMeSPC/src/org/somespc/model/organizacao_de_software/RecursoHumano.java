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
package org.somespc.model.organizacao_de_software;

import java.util.*;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import org.somespc.calculators.TipoDeEntidadeMensuravelCalculator;
import org.somespc.model.comportamento_processo_de_software.*;
import org.somespc.model.entidades_e_medidas.ElementoMensuravel;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.TipoDeEntidadeMensuravel;
import org.somespc.model.medicao.Medicao;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;

@Entity
@Views({ @View(members = "nome;"), @View(name = "Simple", members = "nome") })
@Tab(properties = "nome", defaultOrder = "${nome} asc")
@XmlRootElement
public class RecursoHumano extends EntidadeMensuravel {
	@ManyToMany
	@JoinTable(name = "equipe_recursoHumano", joinColumns = {
			@JoinColumn(name = "recursoHumano_id") }, inverseJoinColumns = { @JoinColumn(name = "equipe_id") })
	private Collection<Equipe> equipe;

	@XmlTransient
	public Collection<Equipe> getEquipe() {
		return equipe;
	}

	public void setEquipe(Collection<Equipe> equipe) {
		this.equipe = equipe;
	}

	@OneToMany(mappedBy = "recursoHumano")
	private Collection<AlocacaoEquipe> alocacaoEquipe;

	@XmlTransient
	public Collection<AlocacaoEquipe> getAlocacaoEquipe() {
		return alocacaoEquipe;
	}

	public void setAlocacaoEquipe(Collection<AlocacaoEquipe> alocacaoEquipe) {
		this.alocacaoEquipe = alocacaoEquipe;
	}

	@OneToMany(mappedBy = "executorDaMedicao")
	private Collection<Medicao> medicaoExecutada;

	@XmlTransient
	public Collection<Medicao> getMedicaoExecutada() {
		return medicaoExecutada;
	}

	public void setMedicaoExecutada(Collection<Medicao> medicaoExecutada) {
		this.medicaoExecutada = medicaoExecutada;
	}

	@OneToMany(mappedBy = "registradoPor")
	private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;

	@XmlTransient
	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}

    @ManyToOne
    @Transient
    @DefaultValueCalculator(
	    value = TipoDeEntidadeMensuravelCalculator.class,
	    properties = {
		    @PropertyValue(name = "nomeEntidade", value = "Recurso Humano")
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
    	
    	String nomeEntidade = "Recurso Humano";
    	Query query = XPersistence.getManager().createQuery("from TipoDeEntidadeMensuravel t where t.nome = '" + nomeEntidade + "'");
    	TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = (TipoDeEntidadeMensuravel) query.getSingleResult();
    	
    	this.setTipoDeEntidadeMensuravel(tipoDeEntidadeMensuravel);
    }
    }//ajusta

}
