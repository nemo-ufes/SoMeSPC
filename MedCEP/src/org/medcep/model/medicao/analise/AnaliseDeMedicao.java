/*
 * MedCEP - A powerful tool for measure
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

package org.medcep.model.medicao.analise;

import java.util.*;

import javax.persistence.*;

import org.medcep.model.medicao.*;
import org.medcep.model.medicao.planejamento.*;
import org.medcep.model.organizacao.*;
import org.medcep.model.processo.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "data;"
		+ "planoDeMedicao;"
		+ "medidaPlanoDeMedicao;"
		+ "entidadeMensuravel;"
		+ "executorDaAnaliseDeMedicao;"
		+ "momentoRealDaAnaliseDeMedicao;"
		+ "resultado;"
		+ "medicao;"
	//+ "Medições para Análise {medicao;},"
	//+ "Detalhes da Análise {"
	//+ "executorDaAnaliseDeMedicao;"
	//+ "momentoRealDaAnaliseDeMedicao;}"
	),
	@View(name = "CEP",
		members = "data; resultado;"
	//+ "Medicoes {medicao;},"
	//+ "Resultado { resultado;}"
	),
	@View(name = "Simple", members = "resultado")
})
@Tabs({
	@Tab(properties = "data, " +
		"medidaPlanoDeMedicao.medida.nome, " +
		//"medidaPlanoDeMedicao.medida.entidadeMedida.nome, " +
		//"executorDaAnaliseDeMedicao.nome, " +
		//"momentoRealDaAnaliseDeMedicao.nome" +
		"projeto", defaultOrder = "${data} desc")
})
public class AnaliseDeMedicao
{

    @Id
    @SequenceGenerator(name="pk_sequence",sequenceName="seq_analise_medicao", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
    @Hidden
    private Integer id;

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String resultado;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public String getResultado()
    {
	return resultado;
    }

    public void setResultado(String resultado)
    {
	this.resultado = resultado;
    }

    @Required
    private Date data;

    @NoCreate
    @NoModify
    //@NoFrame
    @ManyToOne
    @ReferenceView("Simple")
    //@Required
    private RecursoHumano executorDaAnaliseDeMedicao;

    @NoCreate
    @NoModify
    //@NoFrame
    @ManyToOne
    @ReferenceView("Simple")
    //@Required
    private AtividadeProjeto momentoRealDaAnaliseDeMedicao;

    @ManyToOne
    @ReferenceView(value = "Simple", forViews = "CEP")
    @DescriptionsList(descriptionProperties = "nome", notForViews = "CEP")
    @NoCreate
    private PlanoDeMedicao planoDeMedicao;

    public PlanoDeMedicao getPlanoDeMedicao()
    {
	return planoDeMedicao;
    }

    public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao)
    {
	this.planoDeMedicao = planoDeMedicao;
    }

    @ManyToOne
    @ReferenceView(value = "Simple", forViews = "CEP")
    @DescriptionsList(
	    descriptionProperties = "medida.nome, medida.mnemonico"
	    , depends = "planoDeMedicao"
	    , condition = "${planoDeMedicao.id} = ?"
	    , order = "${medida.nome} asc", notForViews = "CEP")
    @NoCreate
    //@Required
    private MedidaPlanoDeMedicao medidaPlanoDeMedicao;

    public Date getData()
    {
	return data;
    }

    public void setData(Date data)
    {
	this.data = data;
    }

    public RecursoHumano getExecutorDaAnaliseDeMedicao()
    {
	return executorDaAnaliseDeMedicao;
    }

    public void setExecutorDaAnaliseDeMedicao(
	    RecursoHumano executorDaAnaliseDeMedicao)
    {
	this.executorDaAnaliseDeMedicao = executorDaAnaliseDeMedicao;
    }

    public AtividadeProjeto getMomentoRealDaAnaliseDeMedicao()
    {
	return momentoRealDaAnaliseDeMedicao;
    }

    public void setMomentoRealDaAnaliseDeMedicao(
	    AtividadeProjeto momentoRealDaAnaliseDeMedicao)
    {
	this.momentoRealDaAnaliseDeMedicao = momentoRealDaAnaliseDeMedicao;
    }

    @ManyToOne
    @ReferenceView("Simple")
    @SearchAction("Medicao.searchEntidadeMensuravel")
    private EntidadeMensuravel entidadeMensuravel;

    public EntidadeMensuravel getEntidadeMensuravel()
    {
	return entidadeMensuravel;
    }

    public void setEntidadeMensuravel(EntidadeMensuravel entidadeMensuravel)
    {
	this.entidadeMensuravel = entidadeMensuravel;
    }

    @ManyToMany
    @JoinTable(
	    name = "analiseDeMedicao_medicao"
	    , joinColumns = {
		    @JoinColumn(name = "analiseDeMedicao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "medicao_id")
	    })
    @ListProperties("medidaPlanoDeMedicao.medida.nome, projeto.nome, data, valorMedido.valorMedido")
    @NewAction("AnaliseDeMedicao.addMedicao")
    private Collection<Medicao> medicao;

    public MedidaPlanoDeMedicao getMedidaPlanoDeMedicao()
    {
	return medidaPlanoDeMedicao;
    }

    public void setMedidaPlanoDeMedicao(MedidaPlanoDeMedicao medidaPlanoDeMedicao)
    {
	this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
    }

    public Collection<Medicao> getMedicao()
    {
	return medicao;
    }


    public void setMedicao(Collection<Medicao> medicao)
    {
	this.medicao = medicao;
    }

    public String getProjeto()
    {
	if (medidaPlanoDeMedicao != null
		&& medidaPlanoDeMedicao.getPlanoDeMedicao() != null)
	{
	    if (medidaPlanoDeMedicao.getPlanoDeMedicao() instanceof PlanoDeMedicaoDoProjeto)
	    {
		if (((PlanoDeMedicaoDoProjeto) medidaPlanoDeMedicao.getPlanoDeMedicao()).getProjeto() != null)
		{
		    return ((PlanoDeMedicaoDoProjeto) medidaPlanoDeMedicao.getPlanoDeMedicao()).getProjeto().getNome();
		}
	    }

	}
	return "";
    } 

}
