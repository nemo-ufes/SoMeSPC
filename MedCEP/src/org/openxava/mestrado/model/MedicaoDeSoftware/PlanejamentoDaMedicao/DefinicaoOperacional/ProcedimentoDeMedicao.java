package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

/**
 * Registrar o número requisitos homologados pelo cliente que
 * foram alterados no período. O número de requisitos alterados
 * equivale ao número de requisitos homologados que sofreram
 * alterações no período.
 */
@Entity
@Views({
	@View(members="nome; descricao; formulaDeCalculoDeMedida;"),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class ProcedimentoDeMedicao extends Procedimento {
    
/*	@OneToMany(mappedBy="procedimentoDeMedicao")
	private Collection<DefinicaoOperacionalDeMedida> procedimentoDeMedicao;*/
	
    @ManyToMany 
    @JoinTable(
	      name="procedimentoDeMedicao_formulaDeCalculoDeMedida"
	      , joinColumns={
	    		  @JoinColumn(name="procedimentoDeMedicao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="formulaDeCalculoDeMedida_id")
	       }
	      )
	private Collection<FormulaDeCalculoDeMedida> formulaDeCalculoDeMedida;
	 
	/*private Collection<Medicao> medicao;*/

	public Collection<FormulaDeCalculoDeMedida> getFormulaDeCalculoDeMedida() {
		return formulaDeCalculoDeMedida;
	}

	public void setFormulaDeCalculoDeMedida(
			Collection<FormulaDeCalculoDeMedida> formulaDeCalculoDeMedida) {
		this.formulaDeCalculoDeMedida = formulaDeCalculoDeMedida;
	}

/*	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}*/

/*	public Collection<DefinicaoOperacionalDeMedida> getProcedimentoDeMedicao() {
		return procedimentoDeMedicao;
	}

	public void setProcedimentoDeMedicao(
			Collection<DefinicaoOperacionalDeMedida> procedimentoDeMedicao) {
		this.procedimentoDeMedicao = procedimentoDeMedicao;
	}*/
	
}
 
