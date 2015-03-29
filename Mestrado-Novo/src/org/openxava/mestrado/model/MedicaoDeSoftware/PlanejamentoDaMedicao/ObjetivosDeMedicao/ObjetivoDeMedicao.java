package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao;

import java.awt.List;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.jpa.*;
import org.openxava.mestrado.model.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

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
 
