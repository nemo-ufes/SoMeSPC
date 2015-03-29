package org.openxava.mestrado.model.OrganizacaoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.actions.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.*;
import org.openxava.mestrado.model.CaracterizacaoDeProjetos.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

import sun.net.www.content.text.*;

import com.sun.xml.internal.bind.annotation.*;

@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; dataInicio, dataFim; equipe; objetivo; elementoMensuravel; criterioDeProjeto;"),
	@View(name="Simple", members="nome"),
	@View(name="SimpleNoFrame", members="nome")
	})
@Tab(properties="nome, dataInicio, dataFim", defaultOrder="${nome} asc")
public class Projeto extends EntidadeMensuravel {
    
	public Collection<CriterioDeProjeto> getCriterioDeProjeto() {
		return criterioDeProjeto;
	}

	public void setCriterioDeProjeto(Collection<CriterioDeProjeto> criterioDeProjeto) {
		this.criterioDeProjeto = criterioDeProjeto;
	}

	private Date dataInicio;
	 
	private Date dataFim;

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@OneToMany(mappedBy="projeto")
	private Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto;

    @ManyToMany 
    @JoinTable(
  	      name="objetivo_projeto"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivo_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="projeto_id")
  	       }
  	      )
    @ListProperties("nome")
	private Collection<Objetivo> objetivo;

	public Collection<PlanoDeMedicaoDoProjeto> getPlanoDeMedicaoDoProjeto() {
		return planoDeMedicaoDoProjeto;
	}

	public void setPlanoDeMedicaoDoProjeto(
			Collection<PlanoDeMedicaoDoProjeto> planoDeMedicaoDoProjeto) {
		this.planoDeMedicaoDoProjeto = planoDeMedicaoDoProjeto;
	}

	public Collection<Objetivo> getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Collection<Objetivo> objetivo) {
		this.objetivo = objetivo;
	}
    
    @ManyToMany 
    @JoinTable(
  	      name="equipe_projeto"
  	      , joinColumns={
  	    		  @JoinColumn(name="projeto_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="equipe_id")
  	       }
  	      )
	private Collection<Equipe> equipe;

	public Collection<Equipe> getEquipe() {
		return equipe;
	}

	public void setEquipe(Collection<Equipe> equipe) {
		this.equipe = equipe;
	}
	
	@OneToMany(mappedBy="projeto", cascade=CascadeType.REMOVE)
	@ListProperties("criterio.nome, valorMedido.valorMedido")
    @CollectionView("Projeto")
	private Collection<CriterioDeProjeto> criterioDeProjeto;
	
/*	@ManyToOne 
	@Required
	@Transient
	@NoModify
	@NoCreate
	@DescriptionsList(descriptionProperties="nome"
						//,condition="${nome} = 'Projeto'"
						)
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;

	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return super.tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		super.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}*/
	
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	//A solução para setar por padrçao o tipo de entidade de projeto/outros pode ser dada pelo
	//uso de uma consulta/atribuição do tipo ao objeto a ser salvo. (ex: projeto ou usar reflexão) 
/*	public Collection getFellowCarriers() {
		 Query query = XPersistence.getManager().createQuery("from Carrier c where " +
		 "c.warehouse.zoneNumber = :zone AND " +
		 "c.warehouse.number = :warehouseNumber AND " +
		 "NOT (c.number = :number) ");
		 query.setParameter("zone", getWarehouse().getZoneNumber());
		 query.setParameter("warehouseNumber", getWarehouse().getNumber());
		 query.setParameter("number", getNumber());
		 return query.getResultList();
		}*/
	
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Projeto")
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
	
	
	
	/*private ProcessoDeSoftwareDeProjeto processoDeSoftwareDeProjeto;*/

	
}
 
