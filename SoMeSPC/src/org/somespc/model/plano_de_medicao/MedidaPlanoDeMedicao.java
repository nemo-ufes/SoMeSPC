/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */

package org.somespc.model.plano_de_medicao;

import javax.persistence.*;

import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.entidades_e_medidas.*;
import org.somespc.validators.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "medida; definicaoOperacionalDeMedida;"),
	@View(name = "PlanoMedicao", members = "medida; definicaoOperacionalDeMedida"),
	@View(name = "Simple", members = "medida.nome")
})
@Tab(properties = "medida.nome, planoDeMedicao.nome, definicaoOperacionalDeMedida.nome")
@EntityValidator(value = MedidaPlanoMedicaoValidator.class,
	properties = {
		@PropertyValue(name = "medida"),
		@PropertyValue(name = "definicaoOperacionalDeMedida"),
	})
public class MedidaPlanoDeMedicao
{

    @Id
    @TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "MED_PLANO_MEDICAO_ID", valueColumnName = "ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
    private Integer id;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @ManyToOne
    @NoCreate
    @ReferenceView("Simple")
    @SearchAction("MedidaPlanoDeMedicao.searchMedidaPlanoMedicao")
    private Medida medida;

    @ManyToOne
    @ReferenceView("Simple")
    private PlanoDeMedicao planoDeMedicao;

    @ManyToOne
    @NoCreate
    @ReferenceView("Simple")
    @SearchAction("MedidaPlanoDeMedicao.searchDefinicaoOperacionalMedidaForMedidaPlanoMedicao")
    private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;

    public Medida getMedida()
    {
	return medida;
    }

    public void setMedida(Medida medida)
    {
	this.medida = medida;
    }

    public PlanoDeMedicao getPlanoDeMedicao()
    {
	return planoDeMedicao;
    }

    public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao)
    {
	this.planoDeMedicao = planoDeMedicao;
    }

    public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida()
    {
	return definicaoOperacionalDeMedida;
    }

    public void setDefinicaoOperacionalDeMedida(DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida)
    {

	this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
    }

}
