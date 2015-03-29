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

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.medcep.validators.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="medida; definicaoOperacionalDeMedida;"),
	@View(name="PlanoMedicao", members="medida; definicaoOperacionalDeMedida"),
	@View(name="Simple", members="medida.nome")
})
@Tab( properties = "medida.nome, planoDeMedicao.nome, definicaoOperacionalDeMedida.nome" )
@EntityValidator(value=MedidaPlanoMedicaoValidator.class, 
	properties={
		@PropertyValue(name="medida"), 
		@PropertyValue(name="definicaoOperacionalDeMedida"), 
	}
)
public class MedidaPlanoDeMedicao {
 
	@Id @GeneratedValue(generator="system-uuid") 
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@NoCreate
	@NoModify
	@ReferenceView("Simple")
	@SearchAction("MedidaPlanoDeMedicao.searchMedidaOfPlanoMedicao")
	private Medida medida;
	
	@ManyToOne
	@ReferenceView("Simple")
	private PlanoDeMedicao planoDeMedicao;
	 
	@ManyToOne
	@NoCreate
	@NoModify
	@ReferenceView("Simple")
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public PlanoDeMedicao getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}

	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
/*		if(definicaoOperacionalDeMedida.getMedida().getid() != getMedida().getid())
			throw new InvalidStateException(
				new InvalidValue[] {
						new InvalidValue(
								"a",
								getClass(), "a",
								true, this)
					}
				);*/
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}
	
/*	@OneToMany(mappedBy="medidaPlanoDeMedicao")
	private Collection<Medicao> medicao;

	public Collection<Medicao> getMedicao() {
		return medicao;
	}

	public void setMedicao(Collection<Medicao> medicao) {
		this.medicao = medicao;
	}*/	
/*	 
	private Collection<AnaliseDeMedicao> analiseDeMedição;*/
	 
	
}
 
