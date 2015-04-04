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
package org.medcep.model.organizacao;

import java.util.*;

import javax.persistence.*;

import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.organizacao.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="nome;"
			+ "tipoObjetivoMedicao;"
			//+ "necessidadeDeInformacao;"
			//+ "indicadores;" 
			+ "objetivoDeSoftware;"
			+ "objetivoEstrategico"
			),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome, tipoObjetivoMedicao.nome", defaultOrder="${nome} asc")
})
public class ObjetivoDeMedicao extends Objetivo {
	
	@ManyToOne 
	@Required
	@NoCreate
	@NoModify
	@DescriptionsList(descriptionProperties="nome") 
	private TipoObjetivoDeMedicao tipoObjetivoMedicao;	

	public TipoObjetivoDeMedicao getTipoObjetivoMedicao() {
		return tipoObjetivoMedicao;
	}

	public void setTipoObjetivoMedicao(TipoObjetivoDeMedicao tipoObjetivoMedicao) {
		this.tipoObjetivoMedicao = tipoObjetivoMedicao;
	}
    
	@ManyToMany
    @JoinTable(
	      name="ObjetivoDeMedicao_BaseadoEm_ObjetivoDeSoftware"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeMedicao")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoDeSoftware")
	       }
	      )
	@ListProperties("nome")
	private Collection<ObjetivoDeSoftware> objetivoDeSoftware;
	 
	@ManyToMany
    @JoinTable(
	      name="ObjetivoDeMedicao_BaseadoEm_ObjetivoEstrategico"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeMedicao")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoEstrategico")
	       }
	      )
	@ListProperties("nome")
	private Collection<ObjetivoEstrategico> objetivoEstrategico;
	
	public Collection<ObjetivoDeSoftware> getObjetivoDeSoftware() {
		return objetivoDeSoftware;
	}

	public void setObjetivoDeSoftware(
			Collection<ObjetivoDeSoftware> objetivoDeSoftware) {
		this.objetivoDeSoftware = objetivoDeSoftware;
	}

	public Collection<ObjetivoEstrategico> getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(
			Collection<ObjetivoEstrategico> objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}
    
/*  
    private Collection<PlanoDeMedicao> planoDeMedicao;

	public Collection<PlanoDeMedicao> getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(Collection<PlanoDeMedicao> planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}*/
	
	
}
 
