package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

import java.util.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;


/**
 * DEP-01
 */
@Entity
@Views({
	@View(members="data; processoPadrao; medida; descricao; limiteDeControle"),
	@View(name="Simple", members="data; limiteDeControle")
})
@Tabs({
	@Tab(properties="processoPadrao.nome, medida.nome, limiteDeControle.limiteInferior, limiteDeControle.limiteCentral, limiteDeControle.limiteSuperior", defaultOrder="${processoPadrao.nome} asc")
})
public class DesempenhoDeProcessoEspecificado {
 
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
	 
	@Stereotype("TEXT_AREA")
	private String descricao;
	 
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private ProcessoPadrao  processoPadrao ;
	 
	@ManyToOne
	@ReferenceView("Simple")
	private Medida medida;
	 
/*	@OneToMany(mappedBy="desempenhoDeProcessoEspecificado")
	private Collection<CapacidadeDeProcesso> capacidadeDeProcesso;*/
	 
	@OneToOne
	//@PrimaryKeyJoinColumn
	private LimiteDeControle limiteDeControle;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProcessoPadrao getProcessoPadrao() {
		return processoPadrao;
	}

	public void setProcessoPadrao(ProcessoPadrao processoPadrao) {
		this.processoPadrao = processoPadrao;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

/*	public Collection<CapacidadeDeProcesso> getCapacidadeDeProcesso() {
		return capacidadeDeProcesso;
	}

	public void setCapacidadeDeProcesso(
			Collection<CapacidadeDeProcesso> capacidadeDeProcesso) {
		this.capacidadeDeProcesso = capacidadeDeProcesso;
	}*/

	public LimiteDeControle getLimiteDeControle() {
		return limiteDeControle;
	}

	public void setLimiteDeControle(LimiteDeControle limiteDeControle) {
		this.limiteDeControle = limiteDeControle;
	}
	 
	
}
 
