package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
@Views({
	@View(members="nome;" 
			+ "tipoObjetivoMedicao;"
			+ "necessidadeDeInformacao;"
			+ "indicadores;" 
			//+ "objetivoDeMedicao;"
			+ "objetivoDeSoftware;"
			+ "objetivoEstrategico"
			),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome, tipoObjetivoMedicao.nome", defaultOrder="${nome} asc")
})
public class ObjetivoDeMedicao extends Objetivo {
	
	@ManyToOne @DescriptionsList(descriptionProperties="nome") @Required
	private TipoObjetivoDeMedicao tipoObjetivoMedicao;	

	public TipoObjetivoDeMedicao getTipoObjetivoMedicao() {
		return tipoObjetivoMedicao;
	}

	public void setTipoObjetivoMedicao(TipoObjetivoDeMedicao tipoObjetivoMedicao) {
		this.tipoObjetivoMedicao = tipoObjetivoMedicao;
	}
    
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
	      name="ObjetivoDeMedicao_BaseadoEm_ObjetivoDeSoftware"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeMedicao")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoDeSoftware")
	       }
	      )
	private Collection<ObjetivoDeSoftware> objetivoDeSoftware;
	 
	@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
	      name="ObjetivoDeMedicao_BaseadoEm_ObjetivoEstrategico"
	      , joinColumns={
	    		  @JoinColumn(name="ObjetivoDeMedicao")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="ObjetivoEstrategico")
	       }
	      )
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
	
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
  	      name="definicaoOperacionalDeMedida_objetivoDeMedicao"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="definicaoOperacionalDeMedida_id")
  	       }
  	      )
	private Collection<DefinicaoOperacionalDeMedida> definicaoOperacionalDeMedida;
    
/*    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_objetivoDeMedicao"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivoDeMedicao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      )
    private Collection<PlanoDeMedicao> planoDeMedicao;*/

	public Collection<DefinicaoOperacionalDeMedida> getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(
			Collection<DefinicaoOperacionalDeMedida> definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}
/*
	public Collection<PlanoDeMedicao> getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(Collection<PlanoDeMedicao> planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}*/
	
 	
	//TODO: ver
	@PostCreate
	public void callBack()
	{
/*		for (ObjetivoDeSoftware objSoft : getObjetivoDeSoftware()) 
		{
//			getObjetivoEstrategico().addAll(objSoft.getObjetivoEstrategico());
			for(ObjetivoEstrategico objEstr : objSoft.getObjetivoEstrategico())
			{
				if(getObjetivoEstrategico() == null)
					setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>());
				 
				getObjetivoEstrategico().add(objEstr);
			}
		}*/
	}
	
	@PreUpdate 
	public void callBack1()
	{
/*		for (ObjetivoDeSoftware objSoft : getObjetivoDeSoftware()) 
		{
//			getObjetivoEstrategico().addAll(objSoft.getObjetivoEstrategico());
			for(ObjetivoEstrategico objEstr : objSoft.getObjetivoEstrategico())
			{
				if(getObjetivoEstrategico() == null)
					setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>());
				 
				getObjetivoEstrategico().add(objEstr);
			}
		}*/
	}
	
	@PrePersist 
	public void callBack2()
	{
/*		for (ObjetivoDeSoftware objSoft : getObjetivoDeSoftware()) 
		{
//			getObjetivoEstrategico().addAll(objSoft.getObjetivoEstrategico());
			for(ObjetivoEstrategico objEstr : objSoft.getObjetivoEstrategico())
			{
				if(getObjetivoEstrategico() == null)
					setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>());
				 
				getObjetivoEstrategico().add(objEstr);
			}
		}*/
	}

	@PreCreate 
	public void callBack3()
	{
		/*for (ObjetivoDeSoftware objSoft : getObjetivoDeSoftware()) 
		{
//			getObjetivoEstrategico().addAll(objSoft.getObjetivoEstrategico());
			for(ObjetivoEstrategico objEstr : objSoft.getObjetivoEstrategico())
			{
				if(getObjetivoEstrategico() == null)
					setObjetivoEstrategico(new ArrayList<ObjetivoEstrategico>());
				 
				getObjetivoEstrategico().add(objEstr);
			}
		}*/
	}
	
	
}
 
