package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.ProcessoDeSoftware.*;

/**
 * Representar graficamente os valores medidos para a
 * medida em análise. ....
 */
@Entity
@Views({
	@View(members="nome, ehBaseadoemCriterios; descricao; metodoAnalitico;"),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome, ehBaseadoemCriterios", defaultOrder="${nome} asc")
})
public class ProcedimentoDeAnaliseDeMedicao extends Procedimento {

	private boolean ehBaseadoemCriterios;
	
    @ManyToMany 
    @JoinTable(
	      name="procedimentoDeAnaliseDeMedicao_metodoAnalitico"
	      , joinColumns={
	    		  @JoinColumn(name="procedimentoDeAnaliseDeMedicao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="metodoAnalitico_id")
	       }
	      )
    @ListProperties("nome, ehMetodoCEP")
	private Collection<MetodoAnalitico> metodoAnalitico;
	 
	/*@OneToMany(mappedBy="procedimentoDeAnaliseDeMedicao")
	private Collection<DefinicaoOperacionalDeMedida> definicaoOperacionalDoProcedimentoDeAnaliseDeMedicao;*/
	 
	public boolean isEhBaseadoemCriterios() {
		return ehBaseadoemCriterios;
	}

	public void setEhBaseadoemCriterios(boolean ehBaseadoemCriterios) {
		this.ehBaseadoemCriterios = ehBaseadoemCriterios;
	}

	public Collection<MetodoAnalitico> getMetodoAnalitico() {
		return metodoAnalitico;
	}

	public void setMetodoAnalitico(Collection<MetodoAnalitico> metodoAnalitico) {
		this.metodoAnalitico = metodoAnalitico;
	}
/*
	public Collection<DefinicaoOperacionalDeMedida> getProcedimentoDeAnaliseDeMedicao() {
		return definicaoOperacionalDoProcedimentoDeAnaliseDeMedicao;
	}

	public void setProcedimentoDeAnaliseDeMedicao(
			Collection<DefinicaoOperacionalDeMedida> procedimentoDeAnaliseDeMedicao) {
		this.definicaoOperacionalDoProcedimentoDeAnaliseDeMedicao = procedimentoDeAnaliseDeMedicao;
	}*/
	 
	//private Collection<AnaliseDeMedicao> analiseDeMedicao;
	
	
}
 
