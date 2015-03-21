package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

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
 
