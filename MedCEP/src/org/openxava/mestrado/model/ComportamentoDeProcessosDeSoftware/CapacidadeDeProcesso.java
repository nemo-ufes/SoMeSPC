package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

@Entity
@Views({
	@View(members="data; valor; processoPadrao; medida; descricao; desempenhoDeProcessoEspecificado; procedimento"),
	//@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="processoPadrao.nome, medida.nome, data, valor", defaultOrder="${processoPadrao.nome} asc")
})
public class CapacidadeDeProcesso {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
	private Date data;
	 
	private float valor;
	
	@Stereotype("TEXT_AREA")
	private String descricao;
	 
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado;
	 
	@OneToOne
	//@PrimaryKeyJoinColumn
	private BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso;
	 
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private ProcessoPadrao  processoPadrao;
	 
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private Procedimento procedimento;
	 
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private Medida medida;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public DesempenhoDeProcessoEspecificado getDesempenhoDeProcessoEspecificado() {
		return desempenhoDeProcessoEspecificado;
	}

	public void setDesempenhoDeProcessoEspecificado(
			DesempenhoDeProcessoEspecificado desempenhoDeProcessoEspecificado) {
		this.desempenhoDeProcessoEspecificado = desempenhoDeProcessoEspecificado;
	}

	public BaselineDeDesempenhoDeProcesso getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			BaselineDeDesempenhoDeProcesso baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}

	public ProcessoPadrao getProcessoPadrao() {
		return processoPadrao;
	}

	public void setProcessoPadrao(ProcessoPadrao processoPadrao) {
		this.processoPadrao = processoPadrao;
	}

	public Procedimento getProcedimento() {
		return procedimento;
	}

	public void setProcedimento(Procedimento procedimento) {
		this.procedimento = procedimento;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}
	 	
}
 
