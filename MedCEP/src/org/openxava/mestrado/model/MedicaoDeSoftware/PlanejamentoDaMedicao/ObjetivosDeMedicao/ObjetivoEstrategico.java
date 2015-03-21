package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
/*@View(members="nome;" 
		+ "indicadores;" 
		+ "Necessidade de Informacao { necessidadeDeInformacao }"
		+ "Objetivos de Medicao { objetivoDeMedicao }"
		//+ "Objetivos de Software { objetivoDeSoftware }"
		//+ "Objetivos Estrategicos { objetivoEstrategico }"
		)*/
@Views({
	@View(members="nome;" 
			+ "necessidadeDeInformacao;"
			+ "indicadores;" 
			+ "objetivoDeMedicao;"
			+ "objetivoDeSoftware"
			//+ "objetivoEstrategico"
			),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class ObjetivoEstrategico extends Objetivo { 

	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
	      name="OObjetivoDeSoftware_BaseadoEm_ObjetivoEstrategico"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoEstrategico")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoDeSoftware")
	       }
	      )
/*    @Editor(value="TreeView")
    @ListProperties("nome")
    @Tree(pathProperty="objetivoDeSoftware")*/
	private Collection<ObjetivoDeSoftware> objetivoDeSoftware;
	 
	@ManyToMany (fetch=FetchType.LAZY)
    @JoinTable(
	      name="ObjetivoDeMedicao_BaseadoEm_ObjetivoEstrategico"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoEstrategico")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoDeMedicao")
	       }
	      )
	private Collection<ObjetivoDeMedicao> objetivoDeMedicao;

	public Collection<ObjetivoDeSoftware> getObjetivoDeSoftware() {
		return objetivoDeSoftware;
	}

	public void setObjetivoDeSoftware(
			Collection<ObjetivoDeSoftware> objetivoDeSoftware) {
		this.objetivoDeSoftware = objetivoDeSoftware;
	}

	public Collection<ObjetivoDeMedicao> getObjetivoDeMedicao() {
		return objetivoDeMedicao;
	}

	public void setObjetivoDeMedicao(Collection<ObjetivoDeMedicao> objetivoDeMedicao) {
		this.objetivoDeMedicao = objetivoDeMedicao;
	}
	 
/*    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoEstrategico"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivoEstrategico_id")
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
 
