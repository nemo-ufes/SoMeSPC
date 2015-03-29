package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="nome;" 
			//+ "necessidadeDeInformacao;"
			//+ "indicadores;"
			+ "objetivoEstrategico;"
			//+ "objetivoDeMedicao;"
			),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
/*@EntityValidator(
		value=ObjetivoDeSoftwareValidator.class, 
		properties={
			@PropertyValue(name="objetivoEstrategico")
		}
)*/
public class ObjetivoDeSoftware extends Objetivo {

	@ManyToMany
    @JoinTable(
	      name="ObjetivoDeMedicao_BaseadoEm_ObjetivoDeSoftware"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeSoftware")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoDeMedicao")
	       }
	      )
	@ListProperties("nome")
	private Collection<ObjetivoDeMedicao> objetivoDeMedicao;
	
	@ManyToMany
    @JoinTable(
	      name="ObjetivoDeSoftware_BaseadoEm_ObjetivoEstrategico"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeSoftware")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoEstrategico")
	       }
	      )
	@ListProperties("nome")
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
	 
/*  
	private Collection<PlanoDeMedicao> planoDeMedicao;

	public Collection<PlanoDeMedicao> getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(Collection<PlanoDeMedicao> planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}*/
    
/*    @PreCreate
    @PreUpdate
    public void validate() throws Exception
    {
		if(objetivoEstrategico == null || objetivoEstrategico.size() < 1)
			throw new Exception("necessario_objetivo_estrategico");
    		throw new InvalidStateException( // The validation exception from
    				new InvalidValue[] { // Hibernate Validator framework
    						new InvalidValue(
    								"necessario_objetivo_estrategico",
    								getClass(), "delivered",
    								true, this
    						)
    				}
			);
    }//validate
*/    	
}
 
