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

package org.medcep.model.medicao.planejamento;

import java.util.*;

import javax.persistence.*;

import org.medcep.model.organizacao.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="nome;" 
			+ "necessidadeDeInformacao;"
			+ "indicadores;" 
			+ "objetivoDeMedicao;"
			//+ "objetivoDeSoftware;"
			+ "objetivoEstrategico"
			),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class ObjetivoDeSoftware extends Objetivo {

	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
	      name="ObjetivoDeMedicao_BaseadoEm_ObjetivoDeSoftware"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeSoftware")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoDeMedicao")
	       }
	      )
	private Collection<ObjetivoDeMedicao> objetivoDeMedicao;
	 
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
	      name="OObjetivoDeSoftware_BaseadoEm_ObjetivoEstrategico"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeSoftware")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoEstrategico")
	       }
	      )
	private Collection<ObjetivoEstrategico> objetivoEstrategico;

	public Collection<ObjetivoDeMedicao> getObjetivoDeMedicao() {
		return objetivoDeMedicao;
	}

	public void setObjetivoDeMedicao(Collection<ObjetivoDeMedicao> objetivoDeMedicao) {
		this.objetivoDeMedicao = objetivoDeMedicao;
	}

	public Collection<ObjetivoEstrategico> getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(
			Collection<ObjetivoEstrategico> objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}
	 
/*    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoDeSoftware"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivoDeSoftware_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      )
	private Collection<PlanoDeMedicao> planoDeMedicao;

	public Collection<PlanoDeMedicao> getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(Collection<PlanoDeMedicao> planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}*/
    
    
}
 
