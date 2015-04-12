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
package org.medcep.model.processo.comportamento;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.medcep.model.medicao.*;
import org.openxava.annotations.*;

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
