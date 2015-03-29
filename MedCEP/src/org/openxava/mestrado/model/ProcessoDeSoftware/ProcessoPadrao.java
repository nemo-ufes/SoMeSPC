package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;

@Entity
@Views({
	@View(members="Processo Padrão [nome; versao; tipoDeEntidadeMensuravel; ehEstavel, ehCapaz; descricao]; atividadePadrao; elementoMensuravel;"),
	@View(name="Simple", members="nome"),
	@View(name="Simple2", members="nome")
})
@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")
public class ProcessoPadrao  extends EntidadeMensuravel {
 
	@Required
	private String versao;
	
	private Boolean ehEstavel;
	 
	private Boolean ehCapaz;
	 
	@OneToMany(mappedBy="processoPadrao")
	private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;
	 
	@OneToMany(mappedBy="processoPadrao")
	private Collection<DesempenhoDeProcessoEspecificado> desempenhoDeProcessoEspecificado;
	 
	@OneToMany(mappedBy="baseadoEm")
	private Collection<ProcessoInstanciado> processoInstanciado;
	
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoPadrao_AtividadePadrao"
	      , joinColumns={
	    		  @JoinColumn(name="processoPadrao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="atividadePadrao_id")
	       }
	      )
	@ListProperties("nome") 
	private Collection<AtividadePadrao> atividadePadrao;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	public Boolean getEhEstavel() {
		return ehEstavel;
	}

	public void setEhEstavel(Boolean ehEstavel) {
		this.ehEstavel = ehEstavel;
	}

	public Boolean getEhCapaz() {
		return ehCapaz;
	}

	public void setEhCapaz(Boolean ehCapaz) {
		this.ehCapaz = ehCapaz;
	}

	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}

	public Collection<DesempenhoDeProcessoEspecificado> getDesempenhoDeProcessoEspecificado() {
		return desempenhoDeProcessoEspecificado;
	}

	public void setDesempenhoDeProcessoEspecificado(
			Collection<DesempenhoDeProcessoEspecificado> desempenhoDeProcessoEspecificado) {
		this.desempenhoDeProcessoEspecificado = desempenhoDeProcessoEspecificado;
	}

	public Collection<ProcessoInstanciado> getProcessoDeSoftwareDeProjeto() {
		return processoInstanciado;
	}

	public void setProcessoDeSoftwareDeProjeto(
			Collection<ProcessoInstanciado> processoDeSoftwareDeProjeto) {
		this.processoInstanciado = processoDeSoftwareDeProjeto;
	}

	public Collection<AtividadePadrao> getAtividadePadrao() {
		return atividadePadrao;
	}

	public void setAtividadePadrao(Collection<AtividadePadrao> atividadePadrao) {
		this.atividadePadrao = atividadePadrao;
	}
	 
	//removido por questões de navegabilidade
	//private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;
	 
	//removido por questões de navegabilidade
	//private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;
	
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Processo de Software Padrão")
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
 
