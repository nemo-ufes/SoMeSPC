package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
@Views({
	@View(members="nome, versao; tipoDeEntidadeMensuravel; baseadoEm; atividadeInstanciada; elementoMensuravel;"),
	@View(name="Simple", members="nome"),
	})
@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")
public class ProcessoInstanciado extends EntidadeMensuravel {
    
	@Required
	private String versao;
	
	@ManyToOne @Required
	@ReferenceView("Simple")
	private ProcessoPadrao  baseadoEm;
	
	@OneToMany(mappedBy="processoInstanciado")
	private Collection<AtividadeInstanciada> atividadeInstanciada;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

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
	 
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Processo de Software Instanciado")
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
 
