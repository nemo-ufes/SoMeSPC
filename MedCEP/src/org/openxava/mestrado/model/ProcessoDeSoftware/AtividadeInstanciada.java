package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="Atividade Instanciada [nome; tipoDeEntidadeMensuravel; baseadoEm; dependeDe; adotaProcedimento; requer; produz; realizadoPor; elementoMensuravel;]"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")	
})
public class AtividadeInstanciada extends EntidadeMensuravel {
 
	@ManyToOne
	@ReferenceView("Simple")
	private AtividadePadrao baseadoEm;
	 
	@ManyToOne
	private ProcessoInstanciado processoInstanciado;

/*	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}
	 */
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
	 
	//private Collection<AnaliseDeMedicao> analiseDeMedicao;
	 
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
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="AtividadeInstanciada_adota_Procedimento"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeInstanciada_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="procedimento_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<Procedimento> adotaProcedimento;
/*	
	@OneToMany(mappedBy="momentoRealDaMedicao")
	private Collection<Medicao> momentoRealDaMedicao;*/

	public AtividadePadrao getBaseadoEm() {
		return baseadoEm;
	}

	public void setBaseadoEm(AtividadePadrao baseadoEm) {
		this.baseadoEm = baseadoEm;
	}

	public ProcessoInstanciado getProcessoDeSoftwareDeProjeto() {
		return processoInstanciado;
	}

	public void setProcessoDeSoftwareDeProjeto(
			ProcessoInstanciado processoDeSoftwareDeProjeto) {
		this.processoInstanciado = processoDeSoftwareDeProjeto;
	}

	public Collection<AtividadeInstanciada> getDependeDe() {
		return dependeDe;
	}

	public void setDependeDe(Collection<AtividadeInstanciada> dependeDe) {
		this.dependeDe = dependeDe;
	}

	public ProcessoInstanciado getProcessoInstanciado() {
		return processoInstanciado;
	}

	public void setProcessoInstanciado(ProcessoInstanciado processoInstanciado) {
		this.processoInstanciado = processoInstanciado;
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

	public Collection<Procedimento> getAdotaProcedimento() {
		return adotaProcedimento;
	}

	public void setAdotaProcedimento(Collection<Procedimento> adotaProcedimento) {
		this.adotaProcedimento = adotaProcedimento;
	}
	 	
	@ManyToOne
	@Required
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
	
}
 
