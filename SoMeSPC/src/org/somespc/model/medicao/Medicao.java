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
package org.somespc.model.medicao;

import java.sql.*;

import javax.persistence.*;

import org.somespc.actions.*;
import org.somespc.model.entidades_e_medidas.ElementoMensuravel;
import org.somespc.model.entidades_e_medidas.EntidadeMensuravel;
import org.somespc.model.organizacao_de_software.*;
import org.somespc.model.plano_de_medicao.*;
import org.somespc.model.processo_de_software.AtividadeInstanciada;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "data; "
		+ "planoDeMedicao;"
		+ "medidaPlanoDeMedicao;"
		+ "entidadeMensuravel;"
		+ "valorMedido;"
		+ "executorDaMedicao; "
		+ "contextoDeMedicao; "
		+ "momentoRealDaMedicao"
	)
})
@Tabs({
	@Tab(properties = "medidaPlanoDeMedicao.medida.nome, " +
		"projeto.nome, " +
		"data, " +
		"valorMedido.valorMedido")
})
public class Medicao implements Comparable<Medicao>
{

    @Id
    @TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "MEDICAO_ID", valueColumnName = "ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
    private Integer id;

    private Timestamp data;

    @ManyToOne(fetch = FetchType.LAZY)
    @Required
    @NoCreate
    @NoModify
    @DescriptionsList(descriptionProperties = "nome")
    private PlanoDeMedicao planoDeMedicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @Required
    @NoCreate
    @NoModify
    @ReferenceView("Simple")
    @DescriptionsList(
	    descriptionProperties = "medida.nome, medida.mnemonico"
	    , depends = "planoDeMedicao"
	    , condition = "${planoDeMedicao.id} = ?")
    private MedidaPlanoDeMedicao medidaPlanoDeMedicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @Required
    @NoCreate
    @ReferenceView("Simple")
    //@SearchAction("Medicao.searchEntidadeMensuravel")
    private EntidadeMensuravel entidadeMensuravel;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(
	    descriptionProperties = "nome")
    private ElementoMensuravel elementoMensuravel;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(
	    descriptionProperties = "nome")
    private Projeto projeto;

    public Projeto getProjeto()
    {
	return this.projeto;
    }

    public void setProjeto(Projeto projeto)
    {
	this.projeto = projeto;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Simple")
    private ContextoDeMedicao contextoDeMedicao;

    
    @NoCreate
    @NoModify
    //@ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Simple")
    //@SearchAction("Medicao.searchMomentoReal")
    private AtividadeInstanciada momentoRealDaMedicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @ReferenceView("Simple")
    private RecursoHumano executorDaMedicao;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @NoSearch
    @OnChange(OnChangePropertyDoNothingValorMedido.class)
    @Required
    private ValorMedido valorMedido;

    public Timestamp getData()
    {
	return data;
    }

    public void setData(Timestamp data)
    {
	this.data = data;
    }

    public EntidadeMensuravel getEntidadeMensuravel()
    {
	return entidadeMensuravel;
    }

    public void setEntidadeMensuravel(EntidadeMensuravel entidadeMensuravel)
    {
	this.entidadeMensuravel = entidadeMensuravel;
    }

    public ContextoDeMedicao getContextoDeMedicao()
    {
	return contextoDeMedicao;
    }

    public void setContextoDeMedicao(ContextoDeMedicao contextoDeMedicao)
    {
	this.contextoDeMedicao = contextoDeMedicao;
    }

    public AtividadeInstanciada getMomentoRealDaMedicao()
    {
	return momentoRealDaMedicao;
    }

    public void setMomentoRealDaMedicao(AtividadeInstanciada momentoRealDaMedicao)
    {
	this.momentoRealDaMedicao = momentoRealDaMedicao;
    }

    public RecursoHumano getExecutorDaMedicao()
    {
	return executorDaMedicao;
    }

    public void setExecutorDaMedicao(RecursoHumano executorDaMedicao)
    {
	this.executorDaMedicao = executorDaMedicao;
    }

    public PlanoDeMedicao getPlanoDeMedicao()
    {
	return planoDeMedicao;
    }

    public void setPlanoDeMedicao(PlanoDeMedicao planoDeMedicao)
    {
	this.planoDeMedicao = planoDeMedicao;
    }

    public MedidaPlanoDeMedicao getMedidaPlanoDeMedicao()
    {
	return medidaPlanoDeMedicao;
    }

    public void setMedidaPlanoDeMedicao(MedidaPlanoDeMedicao medidaPlanoDeMedicao)
    {
	this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
    }

    public ElementoMensuravel getElementoMensuravel()
    {
	return elementoMensuravel;
    }

    public void setElementoMensuravel(ElementoMensuravel elementoMensuravel)
    {
	this.elementoMensuravel = elementoMensuravel;
    }

    public ValorMedido getValorMedido()
    {
	return valorMedido;
    }

    public void setValorMedido(ValorMedido valorMedido)
    {
	this.valorMedido = valorMedido;
    }

    @PreCreate
    //@PreUpdate
    public void ajusteValorMedido()
    {
	ValorMedido valorMedidoAux = valorMedido;
	if (getMedidaPlanoDeMedicao().getMedida().getEscala().isNumerico())
	{
	    valorMedido = new ValorNumerico();
	    ((ValorNumerico) valorMedido).setValorMedido(valorMedidoAux.getValorMedido());
	}
	else
	{
	    valorMedido = new ValorAlfanumerico();
	    ((ValorAlfanumerico) valorMedido).setValorMedido(valorMedidoAux.getValorMedido());
	}
	//TODO: e se alterar a medida plano medição depois que a medição for criada?

	//Seta projeto para facilitar pesquisa
	if (medidaPlanoDeMedicao != null
		&& medidaPlanoDeMedicao.getPlanoDeMedicao() != null)
	{
	    if (medidaPlanoDeMedicao.getPlanoDeMedicao() instanceof PlanoDeMedicaoDoProjeto)
	    {
		if (((PlanoDeMedicaoDoProjeto) medidaPlanoDeMedicao.getPlanoDeMedicao()).getProjeto() != null)
		{
		    projeto = ((PlanoDeMedicaoDoProjeto) medidaPlanoDeMedicao.getPlanoDeMedicao()).getProjeto();
		}
	    }

	}

    }

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    public int compareTo(Medicao o)
    {
	return getData().compareTo(o.getData());
    }

}
