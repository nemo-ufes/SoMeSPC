package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

/**
 * Taxa de Alteração de requisitos = Requisitos alterado / Requisitos Homologados
 */
@Entity
@View(name="Simple", members="nome, formula")
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class FormulaDeCalculoDeMedida {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
    @Column(length=500, unique=true) @Required 
    private String nome;
    
    @Required
	private String formula;

    //o ideal seria ao remover a formula fosse removido a referencia de medida sem excluir ela, mas ja tentei varias coisas e nd
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.REFRESH)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.ALL)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.DETACH)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.MERGE)
    //@OneToOne(mappedBy = "calculadaPor", cascade=CascadeType.PERSIST)
    //@OneToOne(mappedBy = "calculadaPor", orphanRemoval=true)
    //@OneToOne(mappedBy = "calculadaPor", optional=true)
    @OneToOne(mappedBy = "calculadaPor")
    @Required
    @ReferenceView("Simple")
	private Medida calcula;
    
	/*private Collection<ProcedimentoDeMedicao> procedimentoDeMedicao;*/
	
    @ManyToMany 
    @JoinTable(
	      name="formulaDeCalculoDeMedida_usa_medida"
	      , joinColumns={
	    		  @JoinColumn(name="formulaDeCalculoDeMedida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      )
    @ListProperties("nome, mnemonico")
	private Collection<Medida> usaMedidas;
	 
    @ManyToMany 
    @JoinTable(
	      name="formulaDeCalculoDeMedida_usa_formulaDeCalculoDeMedida"
	      , joinColumns={
	    		  @JoinColumn(name="formulaDeCalculoDeMedida_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="formulaDeCalculoDeMedida_id2")
	       }
	      )
	private Collection<FormulaDeCalculoDeMedida> usaFormulaDeCalculoDeMedida;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Medida getCalcula() {
		return calcula;
	}

	public void setCalcula(Medida calcula) {
		this.calcula = calcula;
	}

	public Collection<FormulaDeCalculoDeMedida> getUsaFormulaDeCalculoDeMedida() {
		return usaFormulaDeCalculoDeMedida;
	}

	public void setUsaFormulaDeCalculoDeMedida(
			Collection<FormulaDeCalculoDeMedida> usaFormulaDeCalculoDeMedida) {
		this.usaFormulaDeCalculoDeMedida = usaFormulaDeCalculoDeMedida;
	}

	public Collection<Medida> getUsaMedidas() {
		return usaMedidas;
	}

	public void setUsaMedidas(Collection<Medida> usaMedidas) {
		this.usaMedidas = usaMedidas;
	}
	
}
 
