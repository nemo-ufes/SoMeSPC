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
package org.somespc.model.definicao_operacional_de_medida;

import java.util.*;

import javax.persistence.*;

import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.objetivos.ObjetivoDeMedicao;
import org.somespc.model.organizacao_de_software.*;
import org.somespc.model.plano_de_medicao.*;
import org.somespc.model.processo_de_software.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members = "nome, data;"
		+ "descricao;"
		+ "medida;"
		+ "Medição {"
		+ "momentoDeMedicao;"
		+ "periodicidadeDeMedicao;"
		+ "responsavelPelaMedicao;"
		+ "procedimentoDeMedicao;"
		+ "},"
		+ "Análise de Medição {"
		+ "momentoDeAnaliseDeMedicao;"
		+ "periodicidadeDeAnaliseDeMedicao;"
		+ "responsavelPelaAnaliseDeMedicao;"
		+ "procedimentoDeAnaliseDeMedicao;"
		+ "}"
	),
	@View(name = "Simple", members = "nome"),
//@View(name="ParaAnalise", members="nome; procedimentoDeAnaliseDeMedicao.descricao"),
})
@Tabs({
	@Tab(properties = "nome, medida.nome, data", defaultOrder = "${nome} asc, ${data} desc")
})
public class DefinicaoOperacionalDeMedida
{

    @Id
    @TableGenerator(name="TABLE_GENERATOR", table="ID_TABLE", pkColumnName="ID_TABLE_NAME", pkColumnValue="DEF_OP_MED_ID", valueColumnName="ID_TABLE_VALUE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator="TABLE_GENERATOR")
     @Hidden
    private Integer id;

    public Integer getId()
    {
	return id;
    }

    public void setId(Integer id)
    {
	this.id = id;
    }

    @Column(length = 255, unique = true)
    @Required
    private String nome;

    private Date data;

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Required
    @ManyToOne
    @ReferenceView("Simple")
    private Medida medida;

    @ManyToMany
    @JoinTable(
	    name = "definicaoOperacionalDeMedida_objetivoDeMedicao"
	    , joinColumns = {
		    @JoinColumn(name = "definicaoOperacionalDeMedida_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "objetivoDeMedicao_id")
	    })
    private Collection<ObjetivoDeMedicao> objetivoDeMedicao;

    @OneToMany(mappedBy = "definicaoOperacionalDeMedida")
    private Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    //@Required
    private AtividadePadrao momentoDeMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    private AtividadePadrao momentoDeAnaliseDeMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    //@Required
    private Periodicidade periodicidadeDeMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    private Periodicidade periodicidadeDeAnaliseDeMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    //@Required
    private PapelRecursoHumano responsavelPelaMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    private PapelRecursoHumano responsavelPelaAnaliseDeMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    //@Required
    private ProcedimentoDeMedicao procedimentoDeMedicao;

    @ManyToOne
    @ReferenceView("Simple")
    private ProcedimentoDeAnaliseDeMedicao procedimentoDeAnaliseDeMedicao;
    
	@OneToMany(mappedBy = "definicaoOperacionalDeMedida", cascade = CascadeType.MERGE)
	private Collection<ItemPlanoMedicao> treeItemPlanoMedicao;
	
    public Collection<ItemPlanoMedicao> getTreeItemPlanoMedicao() {
		return treeItemPlanoMedicao;
	}

	public void setTreeItemPlanoMedicao(Collection<ItemPlanoMedicao> treeItemPlanoMedicao) {
		this.treeItemPlanoMedicao = treeItemPlanoMedicao;
	}

	public String getNome()
    {
	return nome;
    }

    public void setNome(String nome)
    {
	this.nome = nome;
    }

    public Date getData()
    {
	return data;
    }

    public void setData(Date data)
    {
	this.data = data;
    }

    public String getDescricao()
    {
	return descricao;
    }

    public void setDescricao(String descricao)
    {
	this.descricao = descricao;
    }

    public Medida getMedida()
    {
	return medida;
    }

    public void setMedida(Medida medida)
    {
	this.medida = medida;
    }

    public Collection<ObjetivoDeMedicao> getObjetivoDeMedicao()
    {
	return objetivoDeMedicao;
    }

    public void setObjetivoDeMedicao(Collection<ObjetivoDeMedicao> objetivoDeMedicao)
    {
	this.objetivoDeMedicao = objetivoDeMedicao;
    }

    public Collection<MedidaPlanoDeMedicao> getMedidaPlanoDeMedicao()
    {
	return medidaPlanoDeMedicao;
    }

    public void setMedidaPlanoDeMedicao(
	    Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao)
    {
	this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
    }

    public AtividadePadrao getMomentoDeMedicao()
    {
	return momentoDeMedicao;
    }

    public void setMomentoDeMedicao(AtividadePadrao momentoDeMedicao)
    {
	this.momentoDeMedicao = momentoDeMedicao;
    }

    public AtividadePadrao getMomentoDeAnaliseDeMedicao()
    {
	return momentoDeAnaliseDeMedicao;
    }

    public void setMomentoDeAnaliseDeMedicao(
	    AtividadePadrao momentoDeAnaliseDeMedicao)
    {
	this.momentoDeAnaliseDeMedicao = momentoDeAnaliseDeMedicao;
    }

    public Periodicidade getPeriodicidadeDeMedicao()
    {
	return periodicidadeDeMedicao;
    }

    public void setPeriodicidadeDeMedicao(Periodicidade periodicidadeDeMedicao)
    {
	this.periodicidadeDeMedicao = periodicidadeDeMedicao;
    }

    public Periodicidade getPeriodicidadeDeAnaliseDeMedicao()
    {
	return periodicidadeDeAnaliseDeMedicao;
    }

    public void setPeriodicidadeDeAnaliseDeMedicao(
	    Periodicidade periodicidadeDeAnaliseDeMedicao)
    {
	this.periodicidadeDeAnaliseDeMedicao = periodicidadeDeAnaliseDeMedicao;
    }

    public PapelRecursoHumano getResponsavelPelaMedicao()
    {
	return responsavelPelaMedicao;
    }

    public void setResponsavelPelaMedicao(PapelRecursoHumano responsavelPelaMedicao)
    {
	this.responsavelPelaMedicao = responsavelPelaMedicao;
    }

    public PapelRecursoHumano getResponsavelPelaAnaliseDeMedicao()
    {
	return responsavelPelaAnaliseDeMedicao;
    }

    public void setResponsavelPelaAnaliseDeMedicao(
	    PapelRecursoHumano responsavelPelaAnaliseDeMedicao)
    {
	this.responsavelPelaAnaliseDeMedicao = responsavelPelaAnaliseDeMedicao;
    }

    public ProcedimentoDeMedicao getProcedimentoDeMedicao()
    {
	return procedimentoDeMedicao;
    }

    public void setProcedimentoDeMedicao(ProcedimentoDeMedicao procedimentoDeMedicao)
    {
	this.procedimentoDeMedicao = procedimentoDeMedicao;
    }

    public ProcedimentoDeAnaliseDeMedicao getProcedimentoDeAnaliseDeMedicao()
    {
	return procedimentoDeAnaliseDeMedicao;
    }

    public void setProcedimentoDeAnaliseDeMedicao(
	    ProcedimentoDeAnaliseDeMedicao procedimentoDeAnaliseDeMedicao)
    {
	this.procedimentoDeAnaliseDeMedicao = procedimentoDeAnaliseDeMedicao;
    }

}
