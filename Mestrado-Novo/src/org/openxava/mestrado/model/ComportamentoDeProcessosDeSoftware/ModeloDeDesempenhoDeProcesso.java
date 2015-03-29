package org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;

@Entity
@Views({
	@View(members=
			//"nome,  "
			" data; "
			+ " formulaDeCalculoDeMedida; "
			+ " baselineDeDesempenhoDeProcesso;"
	),	
	//@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="formulaDeCalculoDeMedida.nome, data", defaultOrder="${formulaDeCalculoDeMedida.nome} asc, ${data} desc")
})
public class ModeloDeDesempenhoDeProcesso {
	
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
/*    @Required 
	@Column(length=500, unique=true) 
    private String nome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}*/
	
	private Date data;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
    //@NewAction("EntidadeMensuravel.AddElementoMensuravel")
    @ListProperties("medida.nome, processoPadrao.nome, data, limiteDeControle.limiteInferior, limiteDeControle.limiteSuperior")
	private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;
		
	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}

	@OneToOne
	@Required
	@ReferenceView("Simple")
	private FormulaDeCalculoDeMedida formulaDeCalculoDeMedida;

	public FormulaDeCalculoDeMedida getFormulaDeCalculoDeMedida() {
		return formulaDeCalculoDeMedida;
	}

	public void setFormulaDeCalculoDeMedida(
			FormulaDeCalculoDeMedida formulaDeCalculoDeMedida) {
		this.formulaDeCalculoDeMedida = formulaDeCalculoDeMedida;
	}
	
}
