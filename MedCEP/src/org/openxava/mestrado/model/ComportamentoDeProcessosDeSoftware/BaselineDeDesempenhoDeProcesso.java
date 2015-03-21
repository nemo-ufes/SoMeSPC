package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.AnaliseDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;


/**
 * BDP-01
 */
@Entity
@Views({
	@View(members="data;"
			+ "processoPadrao;"
			+ "limiteDeControle;"
			//+ "descricao;"
			//+ "medida;"
			+ "Execução {"
			+ "contextoDeBaselineDeDesempenhoDeProcesso;"
			+ "modeloDeDesempenhoDeProcesso;"
			+ "registradoPor; },"
			+ "Atualiza a Baseline {" 
			+ "atualizaBaselineDeDesempenhoDeProcesso;"
			+ "}"
	),
	@View(name="Simple", members="data; limiteDeControle; Processo Padrão [processoPadrao.nome]; Medida [medida.nome];"),
	})
@Tabs({
	@Tab(properties="processoPadrao.nome, medida.nome, data", defaultOrder="${processoPadrao.nome} asc, ${data} desc")
})
public class BaselineDeDesempenhoDeProcesso {
   
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
	private ContextoDeBaselineDeDesempenhoDeProcesso contextoDeBaselineDeDesempenhoDeProcesso;
	 
	@OneToOne
	//@Required
	private AnaliseDeMedicao analiseDeMedicao;
	
    public AnaliseDeMedicao getAnaliseDeMedicao() {
		return analiseDeMedicao;
	}

	public void setAnaliseDeMedicao(AnaliseDeMedicao analiseDeMedicao) {
		this.analiseDeMedicao = analiseDeMedicao;
	}

	@ManyToMany 
    @JoinTable(
	      name="baselineDeDesempenhoDeProcesso_modeloDeDesempenhoDeProcesso"
	      , joinColumns={
	    		  @JoinColumn(name="baselineDeDesempenhoDeProcesso_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="modeloDeDesempenhoDeProcesso_id")
	       }
	      )
	private Collection<FormulaDeCalculoDeMedida> modeloDeDesempenhoDeProcesso;
	 
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private Medida medida;
	 
	@ManyToMany 
    @JoinTable(
	      name="baselineDeDesempenhoDeProcesso_Medicao"
	      , joinColumns={
	    		  @JoinColumn(name="baselineDeDesempenhoDeProcesso_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medicao_id")
	       }
	      )
	private Collection<Medicao> medicao;
	 
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private RecursoHumano registradoPor;
	 
	@ManyToOne 
	//@Required
	@ReferenceView(value="Simple",notForViews="Simple")
	private ProcessoPadrao  processoPadrao ;
	 
	//private CapacidadeDeProcesso capacidadeDeProcesso;
	 
	//private Collection<LimiteDeControle> limiteDeControle;
	@OneToOne
	//@PrimaryKeyJoinColumn
	private LimiteDeControle limiteDeControle;
	 
	@OneToOne
	//@PrimaryKeyJoinColumn
	@ReferenceView("Simple")
	private BaselineDeDesempenhoDeProcesso atualizaBaselineDeDesempenhoDeProcesso;

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

	public ContextoDeBaselineDeDesempenhoDeProcesso getContextoDeBaselineDeDesempenhoDeProcesso() {
		return contextoDeBaselineDeDesempenhoDeProcesso;
	}

	public void setContextoDeBaselineDeDesempenhoDeProcesso(
			ContextoDeBaselineDeDesempenhoDeProcesso contextoDeBaselineDeDesempenhoDeProcesso) {
		this.contextoDeBaselineDeDesempenhoDeProcesso = contextoDeBaselineDeDesempenhoDeProcesso;
	}

	public Collection<FormulaDeCalculoDeMedida> getModeloDeDesempenhoDeProcesso() {
		return modeloDeDesempenhoDeProcesso;
	}

	public void setModeloDeDesempenhoDeProcesso(
			Collection<FormulaDeCalculoDeMedida> modeloDeDesempenhoDeProcesso) {
		this.modeloDeDesempenhoDeProcesso = modeloDeDesempenhoDeProcesso;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}
	
	public ProcessoPadrao getProcessoPadrao() {
		return processoPadrao;
	}

	public void setProcessoPadrao(ProcessoPadrao processoPadrao) {
		this.processoPadrao = processoPadrao;
	}

	public BaselineDeDesempenhoDeProcesso getAtualizaBaselineDeDesempenhoDeProcesso() {
		return atualizaBaselineDeDesempenhoDeProcesso;
	}

	public LimiteDeControle getLimiteDeControle() {
		return limiteDeControle;
	}

	public void setLimiteDeControle(LimiteDeControle limiteDeControle) {
		this.limiteDeControle = limiteDeControle;
	}

	public void setAtualizaBaselineDeDesempenhoDeProcesso(
			BaselineDeDesempenhoDeProcesso atualizaBaselineDeDesempenhoDeProcesso) {
		this.atualizaBaselineDeDesempenhoDeProcesso = atualizaBaselineDeDesempenhoDeProcesso;
	}

	public RecursoHumano getRegistradoPor() {
		return registradoPor;
	}

	public void setRegistradoPor(RecursoHumano registradoPor) {
		this.registradoPor = registradoPor;
	}
	 
}
 
