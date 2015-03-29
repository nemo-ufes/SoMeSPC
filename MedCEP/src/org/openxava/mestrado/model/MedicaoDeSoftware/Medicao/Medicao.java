package org.openxava.mestrado.model.MedicaoDeSoftware.Medicao;

import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.actions.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;


@Entity
@Views({
/*	@View(members="data; "
			+"entidadeMensuravel; "
			+"elementoMensuravel; "
			+"Valor Medido [ tipo; valorTemp ]; "
			+"Planejamento {planoDeMedicao;"
			+"medidaPlanoDeMedicao;},"
			+"Execucao {definicaoOperacionalDeMedida; "
			+"momentoRealDaMedicao; "
			+"executorDaMedicao; "
			+"contextoDeMedicao}"
	),*/
	@View(members="Cadastro Medição [data, "
			+ "planoDeMedicao,"
			+ "medidaPlanoDeMedicao;"
			+ "valorMedido;"
			//+"entidadeMensuravel; "
			//+"elementoMensuravel; "
			//+"Planejamento {planoDeMedicao;"
			//+"medidaPlanoDeMedicao;},"
			+"momentoRealDaMedicao; "
			+"executorDaMedicao; "
			+"contextoDeMedicao;]"
	),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="medidaPlanoDeMedicao.medida.nome, medidaPlanoDeMedicao.medida.entidadeMedida.nome, data, valorMedido.valorMedido", defaultOrder="${data} desc")
})
public class Medicao {
 
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
	
/*	@Transient
	@Editor("ValidValuesRadioButton")
	private Type tipo;	
	public enum Type { NUMERICO, ALFANUMERICO };
*/		
	@ManyToOne
	@Required
	//@ReferenceView("Simple")
	@DescriptionsList(descriptionProperties="nome")
	private PlanoDeMedicao planoDeMedicao;
	
	@ManyToOne
	@Required
	//@ReferenceView("Medicao")
	@ReferenceView("Simple")
	@DescriptionsList(
			descriptionProperties="medida.nome, medida.mnemonico"
			,depends="planoDeMedicao"
			,condition="${planoDeMedicao.id} = ?"
			,order="${medida.nome} asc"
			)
	@NoCreate
	//@NoModify
	private MedidaPlanoDeMedicao medidaPlanoDeMedicao;
	
	@ManyToOne 
	//@Required
	//@ReferenceView("Simple")
/*	@DescriptionsList(
			descriptionProperties="nome"
			//,depends="medidaPlanoDeMedicao.medida.entidadeMensuravel.id"
			,depends="medidaPlanoDeMedicao"
			,condition="${id} = ?"
			,order="${nome} asc"
			)*/
	private EntidadeMensuravel entidadeMensuravel;
	
	@ManyToOne 
	//@Required
	//@ReferenceView("Simple")
	@DescriptionsList(
			descriptionProperties="nome"
/*			,depends="entidadeMensuravel"
			,condition="${entidadeMensuravel.id} = ?"*/
			,order="${nome} asc"
			)
	private ElementoMensuravel elementoMensuravel;
	
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private ContextoDeMedicao contextoDeMedicao;
	
/*	@ManyToOne 
	//@Required
	@NoCreate
	@NoModify
	@ReferenceView("Simple")
	@SearchAction("Medicao.searchDefinicaoOperacional")
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;*/
	
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private AtividadeInstanciada momentoRealDaMedicao;
	
	@ManyToOne 
	//@Required
	@ReferenceView("Simple")
	private RecursoHumano executorDaMedicao;
	 
    @OneToOne(cascade=CascadeType.ALL)
	//@ManyToOne(cascade=CascadeType.REMOVE)
    //@PrimaryKeyJoinColumn
    @NoSearch
    //@NoFrame
    @OnChange(OnChangePropertyDoNothingValorMedido.class)
    @OnChangeSearch(OnChangeSearchDoNothing.class)
    @Required
    //@Embedded
	private ValorMedido valorMedido;

/*    @ManyToMany
    @JoinTable(
	      name="analiseDeMedicao_medicao"
	      , joinColumns={
	    		  @JoinColumn(name="medicao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="analiseDeMedicao_id")
	       }
	      )
	private Collection<AnaliseDeMedicao> analiseDeMedicao;	*/
   
	//private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public EntidadeMensuravel getEntidadeMensuravel() {
		return entidadeMensuravel;
	}

	public void setEntidadeMensuravel(EntidadeMensuravel entidadeMensuravel) {
		this.entidadeMensuravel = entidadeMensuravel;
	}

	public ContextoDeMedicao getContextoDeMedicao() {
		return contextoDeMedicao;
	}

	public void setContextoDeMedicao(ContextoDeMedicao contextoDeMedicao) {
		this.contextoDeMedicao = contextoDeMedicao;
	}
/*
	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(
			DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}*/

	public AtividadeInstanciada getMomentoRealDaMedicao() {
		return momentoRealDaMedicao;
	}

	public void setMomentoRealDaMedicao(AtividadeInstanciada momentoRealDaMedicao) {
		this.momentoRealDaMedicao = momentoRealDaMedicao;
	}

/*	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}*/
/*
	public ProcedimentoDeMedicao getProcedimentoDeMedicao() {
		return procedimentoDeMedicao;
	}

	public void setProcedimentoDeMedicao(ProcedimentoDeMedicao procedimentoDeMedicao) {
		this.procedimentoDeMedicao = procedimentoDeMedicao;
	}
*/
	public RecursoHumano getExecutorDaMedicao() {
		return executorDaMedicao;
	}

	public void setExecutorDaMedicao(RecursoHumano executorDaMedicao) {
		this.executorDaMedicao = executorDaMedicao;
	}

/*	public Collection<AnaliseDeMedicao> getAnaliseDeMedicao() {
		return analiseDeMedicao;
	}

	public void setAnaliseDeMedicao(Collection<AnaliseDeMedicao> analiseDeMedicao) {
		this.analiseDeMedicao = analiseDeMedicao;
	}*/

	public PlanoDeMedicao getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}

	public MedidaPlanoDeMedicao getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(MedidaPlanoDeMedicao medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}

	public ElementoMensuravel getElementoMensuravel() {
		return elementoMensuravel;
	}

	public void setElementoMensuravel(ElementoMensuravel elementoMensuravel) {
		this.elementoMensuravel = elementoMensuravel;
	}

	public ValorMedido getValorMedido() {
		return valorMedido;
	}

	public void setValorMedido(ValorMedido valorMedido) {
/*		if(this.valorMedido != null && this.valorMedido instanceof ValorNumerico)
		{
			this.valorMedido = new ValorNumerico();
			this.valorMedido.setValorMedido(valorMedido.getValorMedido());
		}
		else
		{
			this.valorMedido = valorMedido;		
		}*/
		this.valorMedido = valorMedido;	
		
	}
	
	@PreCreate
	//@PreUpdate
	public void ajusteValorMedido() 
	{
		ValorMedido valorMedidoAux = valorMedido;
		if(getMedidaPlanoDeMedicao().getMedida().getEscala().isNumerico())
    	{
    		valorMedido = new ValorNumerico();
    		((ValorNumerico)valorMedido).setValorMedido(valorMedidoAux.getValorMedido());
    	}
		else
    	{
    		valorMedido = new ValorAlfanumerico();
    		((ValorAlfanumerico)valorMedido).setValorMedido(valorMedidoAux.getValorMedido()); 		
    	}
		//TODO: e se alterar a medida plano medição depois que a medição for criada?
	}
	
}
 
