package org.somespc.model.processo_de_software;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;
import org.somespc.calculators.TipoDeEntidadeMensuravelCalculator;
import org.somespc.model.entidades_e_medidas.ElementoMensuravel;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.entidades_e_medidas.TipoDeEntidadeMensuravel;
import org.somespc.model.organizacao_de_software.RecursoHumano;
import org.somespc.validators.AtividadeInstanciadaValidator;

@Entity
@Views({
	@View(members="nome; baseadoEm; dependeDe; requer; produz; realizadoPor;"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")	
})
@EntityValidator(
		value=AtividadeInstanciadaValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class AtividadeInstanciada extends EntidadeMensuravel {
 
	@ManyToOne
	@ReferenceView("Simple")
	@Required
	private AtividadePadrao baseadoEm;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoInstanciado_AtividadeInstanciada"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="processoInstanciado_id")
	       }
	      )
	private Collection<ProcessoInstanciado> processoInstanciado;
		
	public Collection<ProcessoInstanciado> getProcessoInstanciado() {
		return processoInstanciado;
	}

	public void setProcessoInstanciado(
			Collection<ProcessoInstanciado> processoInstanciado) {
		this.processoInstanciado = processoInstanciado;
	}

	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_dependeDe_AtividadeInstanciada"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id2")
	       }
	      )
	@ListProperties("nome") 
	private Collection<AtividadeInstanciada> dependeDe;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_RealizadoPor_RecursoHumano"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="recursoHumano_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<RecursoHumano> realizadoPor;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_produz_Artefato"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="artefato_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<Artefato> produz;
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_requer_Artefato"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="artefato_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<Artefato> requer;

	public AtividadePadrao getBaseadoEm() {
		return baseadoEm;
	}

	public void setBaseadoEm(AtividadePadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

		public Collection<AtividadeInstanciada> getDependeDe() {
		return dependeDe;
	}

	public void setDependeDe(Collection<AtividadeInstanciada> dependeDe) {
		this.dependeDe = dependeDe;
	}

	public Collection<RecursoHumano> getRealizadoPor() {
		return realizadoPor;
	}

	public void setRealizadoPor(Collection<RecursoHumano> realizadoPor) {
		this.realizadoPor = realizadoPor;
	}

	public Collection<Artefato> getProduz() {
		return produz;
	}

	public void setProduz(Collection<Artefato> produz) {
		this.produz = produz;
	}

	public Collection<Artefato> getRequer() {
		return requer;
	}

	public void setRequer(Collection<Artefato> requer) {
		this.requer = requer;
	}

	public Collection<Artefato> getProduto() {
		return produz;
	}

	public void setProduto(Collection<Artefato> produto) {
		this.produz = produto;
	}

	public Collection<Artefato> getArtefato() {
		return requer;
	}

	public void setArtefato(Collection<Artefato> artefato) {
		this.requer = artefato;
	}

	@ManyToOne
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Atividade Instanciada")
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
    	
    	String nomeEntidade = "Atividade Instanciada";
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
 
