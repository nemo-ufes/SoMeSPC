/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique N�spoli Castro, Vin�cius Soares Fonseca                          

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

package org.medcep.model.processo.comportamento;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.processo.*;
import org.openxava.annotations.*;

/**
 * DEP-01
 */
@Entity
@Views({
	@View(members="data; processoPadrao; medida; limiteDeControle; descricao"),
	@View(name="Simple", members="data; Limites [ limiteDeControle; ];")
})
@Tabs({
	@Tab(properties="processoPadrao.nome, medida.nome, limiteDeControle.limiteInferior, limiteDeControle.limiteSuperior", defaultOrder="${processoPadrao.nome} asc")
})
public class DesempenhoDeProcessoEspecificado {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
     
	private Date data;
	 
	@Stereotype("TEXT_AREA")
	@Column(columnDefinition="TEXT")
	private String descricao;
	 
	@Required
	@NoCreate
	@ManyToOne
	//@NoFrame
	@ReferenceView("Simple")
	//@DescriptionsList(descriptionProperties="nome")
	private ProcessoPadrao processoPadrao;
		
	public ProcessoPadrao getProcessoPadrao() {
		return processoPadrao;
	}

	public void setProcessoPadrao(ProcessoPadrao processoPadrao) {
		this.processoPadrao = processoPadrao;
	}
	
/*	@Required
	@NoCreate
	@ManyToOne
	@DescriptionsList(
			descriptionProperties="nome, mnemonico"
			,depends="processoPadrao"
			,condition="${entidadeMedida.id} = ?"
			,order="${nome} asc"
			)*/
	@Required
	@NoCreate
	@ManyToOne
	@ReferenceView("Simple")
	@SearchAction("AnaliseDeComportamentoDeProcesso.searchMedida")
	private Medida medida;
	
	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}
	 
/*	@OneToMany(mappedBy="desempenhoDeProcessoEspecificado")
	private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;*/
	 
	//@PrimaryKeyJoinColumn
	@NoFrame(forViews="Simple")
	@Embedded
	
	private LimiteDeControle limiteDeControle;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

/*	public Collection<CapacidadeDeProcesso> getCapacidadeDeProcesso() {
		return capacidadeDeProcesso;
	}

	public void setCapacidadeDeProcesso(
			Collection<CapacidadeDeProcesso> capacidadeDeProcesso) {
		this.capacidadeDeProcesso = capacidadeDeProcesso;
	}*/

	public LimiteDeControle getLimiteDeControle() {
		return limiteDeControle;
	}

	public void setLimiteDeControle(LimiteDeControle limiteDeControle) {
		this.limiteDeControle = limiteDeControle;
	}
	 
	
}
 