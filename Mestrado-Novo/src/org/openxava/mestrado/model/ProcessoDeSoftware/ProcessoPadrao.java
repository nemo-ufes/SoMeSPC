package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="nome, versao;  tipoDeEntidadeMensuravel; descricao; atividadePadrao; elementoMensuravel; capacidadeDeProcesso;"),
	@View(name="Simple", members="nome"),
	@View(name="SimpleNoFrame", members="nome")
})
@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")
@EntityValidator(
		value=ProcessoPadraoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class ProcessoPadrao  extends EntidadeMensuravel {
 
	@Required
	private String versao;
	
/*	private Boolean ehEstavel;
	 
	private Boolean ehCapaz;*/
	 
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
	
/*	public Boolean getEhEstavel() {
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
	}*/

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
	
	@ReadOnly
	@OneToMany(mappedBy="processoPadrao")
	@ListProperties("medida.nome, data, capaz")
	private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;
		
	public Collection<CapacidadeDeProcesso> getCapacidadeDeProcesso() {
		return capacidadeDeProcesso;
	}

	public void setCapacidadeDeProcesso(
			Collection<CapacidadeDeProcesso> capacidadeDeProcesso) {
		this.capacidadeDeProcesso = capacidadeDeProcesso;
	}
	
	
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
	
	/*
	public String getEstabilidadeDoProcesso(){
		if(capacidadeDeProcesso != null)
		{
			if(capacidadeDeProcesso.size() > 0)
			{
				return "É estável.";
			}
		}
		return "Não é estável.";
	}*/
}
 
