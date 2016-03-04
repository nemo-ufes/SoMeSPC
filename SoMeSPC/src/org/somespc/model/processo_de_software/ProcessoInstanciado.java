package org.somespc.model.processo_de_software;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;
import org.somespc.calculators.TipoDeEntidadeMensuravelCalculator;
import org.somespc.model.entidades_e_medidas.ElementoMensuravel;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.TipoDeEntidadeMensuravel;
import org.somespc.validators.ProcessoInstanciadoValidator;

@Entity
@Views({
	@View(members="nome; baseadoEm; dependeDe; atividadeInstanciada;"),
	@View(name="Simple", members="nome"),
	})
@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")
@EntityValidator(
		value=ProcessoInstanciadoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class ProcessoInstanciado extends EntidadeMensuravel {
    
	@ManyToOne @Required
	@ReferenceView("Simple")
	private ProcessoPadrao  baseadoEm;
	
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoInstanciado_dependeDe_ProcessoInstanciado"
	      , joinColumns={
	    		  @JoinColumn(name="processoInstanciado_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="processoInstanciado_id2")
	       }
	      )
	@ListProperties("nome") 
	private Collection<ProcessoInstanciado> dependeDe;
	
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoInstanciado_AtividadeInstanciada"
	      , joinColumns={
	    		  @JoinColumn(name="processoInstanciado_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      )
	@ListProperties("nome")
	@NewAction("ProcessoInstanciado.add")
	private Collection<AtividadeInstanciada> atividadeInstanciada;
	
	public ProcessoPadrao getProcessoDeSoftwarePadrao() {
		return baseadoEm;
	}

	public void setProcessoDeSoftwarePadrao(
			ProcessoPadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

	public ProcessoPadrao getBaseadoEm() {
		return baseadoEm;
	}

	public void setBaseadoEm(ProcessoPadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

	public Collection<AtividadeInstanciada> getAtividadeInstanciada() {
		return atividadeInstanciada;
	}

	public void setAtividadeInstanciada(
			Collection<AtividadeInstanciada> atividadeInstanciada) {
		this.atividadeInstanciada = atividadeInstanciada;
	}
	
	public Collection<ProcessoInstanciado> getDependeDe() {
		return dependeDe;
	}

	public void setDependeDe(Collection<ProcessoInstanciado> dependeDe) {
		this.dependeDe = dependeDe;
	}

	 
	@ManyToOne
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Processo Instanciado")
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
	
    @PreCreate
    @PreUpdate
    public void ajustaElementosMensuraveis()
    {
	if(tipoDeEntidadeMensuravel != null){
    	
    	String nomeEntidade = "Processo Instanciado";
    	Query query = XPersistence.getManager().createQuery("from TipoDeEntidadeMensuravel t where t.nome = '" + nomeEntidade + "'");
    	TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = (TipoDeEntidadeMensuravel) query.getSingleResult();
    	
    	this.setTipoDeEntidadeMensuravel(tipoDeEntidadeMensuravel);
    }
	if (getElementoMensuravel() == null)
	    setElementoMensuravel(new ArrayList<ElementoMensuravel>());

	if (tipoDeEntidadeMensuravel != null && tipoDeEntidadeMensuravel.getElementoMensuravel() != null)
	{
	    boolean add;
	    for (ElementoMensuravel elemTipo : tipoDeEntidadeMensuravel.getElementoMensuravel())
	    {
		add = true;
		for (ElementoMensuravel elem : getElementoMensuravel())
		{
		    if (elem.getNome().compareTo(elemTipo.getNome()) == 0)
		    {
			add = false;
			break;
		    }
		}
		if (add)
		    getElementoMensuravel().add(elemTipo);
	    }//elemTipo
	}
    }//ajusta
	
}
 
