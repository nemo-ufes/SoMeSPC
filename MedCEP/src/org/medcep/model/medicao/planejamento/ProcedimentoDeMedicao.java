/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */

package org.medcep.model.medicao.planejamento;

import java.util.*;

import javax.persistence.*;

import org.medcep.model.processo.*;
import org.openxava.annotations.*;

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
 
