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

import java.util.*;

import javax.persistence.*;

import org.somespc.model.objetivos.NecessidadeDeInformacao;
import org.somespc.model.objetivos.ObjetivoDeMedicao;
import org.somespc.model.objetivos.ObjetivoDeSoftware;
import org.somespc.model.objetivos.ObjetivoEstrategico;
import org.somespc.model.organizacao_de_software.*;
import org.openxava.annotations.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members = "data;" +
		"nome, " +
		"versao; " +
		"descricao; " +
		"alteracoes; " +
		"recursoHumano; " +
		"planoTree; " +
		//"objetivos { objetivoEstrategico; objetivoDeSoftware; objetivoDeMedicao; necessidadeDeInformacao; } " +
		"MedidasDoPlano { medidaPlanoDeMedicao }"),
	@View(name = "Simple", members = "nome")
})
@Tabs({
	@Tab(properties = "nome, versao", defaultOrder = "${nome} asc, ${versao} desc")
})
public class PlanoDeMedicao
{

    public String getAlteracoes()
    {
	return alteracoes;
    }

    public void setAlteracoes(String alteracoes)
    {
	this.alteracoes = alteracoes;
    }

    @Id
    @TableGenerator(name="TABLE_GENERATOR", table="ID_TABLE", pkColumnName="ID_TABLE_NAME", pkColumnValue="PLANO_MEDICAO_ID", valueColumnName="ID_TABLE_VALUE")
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

    @Stereotype("TEXT_AREA")
    @Column(columnDefinition = "TEXT")
    private String alteracoes;

    private String versao;

    @ManyToMany
    @JoinTable(
	    name = "recursoHumano_planoDeMedicao"
	    , joinColumns = {
		    @JoinColumn(name = "planoDeMedicao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "recursoHumano_id")
	    })
    @ListProperties("nome")
    private Collection<RecursoHumano> recursoHumano;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
	    name = "planoDeMedicao_objetivoDeMedicao"
	    , joinColumns = {
		    @JoinColumn(name = "planoDeMedicao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "objetivoDeMedicao_id")
	    })
    @ListProperties("nome")
    private Set<ObjetivoDeMedicao> objetivoDeMedicao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
	    name = "planoDeMedicao_objetivoEstrategico"
	    , joinColumns = {
		    @JoinColumn(name = "planoDeMedicao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "objetivoEstrategico_id")
	    })
    @ListProperties("nome")
    private Set<ObjetivoEstrategico> objetivoEstrategico;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
	    name = "planoDeMedicao_objetivoDeSoftware"
	    , joinColumns = {
		    @JoinColumn(name = "planoDeMedicao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "objetivoDeSoftware_id")
	    })
    @ListProperties("nome")
    private Set<ObjetivoDeSoftware> objetivoDeSoftware;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
	    name = "planoDeMedicao_necessidadeDeInformacao"
	    , joinColumns = {
		    @JoinColumn(name = "planoDeMedicao_id")
	    }
	    , inverseJoinColumns = {
		    @JoinColumn(name = "necessidadeDeInformacao_id")
	    })
    @ListProperties("nome")
    private Set<NecessidadeDeInformacao> necessidadeDeInformacao;

    @OneToMany(mappedBy = "planoDeMedicaoContainer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    //@OneToMany(mappedBy="planoDeMedicaoContainer")
    @Editor("TreeView")
    @ListProperties("nome")
    @OrderBy("path")
    //@NewAction("TreeContainer.addObjetivosToPlanoMedicao")
    //@NewAction("TreeContainer.NewTreeViewItem3")
    private Set<TreeItemPlanoMedicao> planoTree;

    public Set<TreeItemPlanoMedicao> getPlanoTree()
    {
	ajustes();
	return planoTree;
    }

    public void setPlanoTree(Set<TreeItemPlanoMedicao> planoTree)
    {
	this.planoTree = planoTree;
    }

    /*
     * @OneToMany(mappedBy="parentContainer2", cascade = CascadeType.REMOVE)
     * //@OneToMany(mappedBy="parentContainer")
     * 
     * @Editor("TreeView")
     * 
     * @ListProperties("description")
     * //@OrderBy("path, treeOrder")
     * 
     * @OrderBy("treeOrder")
     * //@NewAction("TreeContainer.addObjetivosToPlanoMedicao")
     * private Collection<TreeItem> treeItems;
     * 
     * public Collection<TreeItem> getTreeItems() {
     * return treeItems;
     * }
     * 
     * public void setTreeItems(Collection<TreeItem> treeItems) {
     * this.treeItems = treeItems;
     * }
     */

    @OneToMany(mappedBy = "planoDeMedicao", cascade = CascadeType.REMOVE)
    @ListProperties("medida.nome, definicaoOperacionalDeMedida.nome")
    @CollectionView("PlanoMedicao")
    private Collection<MedidaPlanoDeMedicao> medidaPlanoDeMedicao;

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

    public String getVersao()
    {
	return versao;
    }

    public void setVersao(String versao)
    {
	this.versao = versao;
    }

    public Collection<RecursoHumano> getRecursoHumano()
    {
	return recursoHumano;
    }

    public void setRecursoHumano(Collection<RecursoHumano> recursoHumano)
    {
	this.recursoHumano = recursoHumano;
    }

    public Set<ObjetivoDeMedicao> getObjetivoDeMedicao()
    {
	return objetivoDeMedicao;
    }

    public void setObjetivoDeMedicao(Set<ObjetivoDeMedicao> objetivoDeMedicao)
    {
	this.objetivoDeMedicao = objetivoDeMedicao;
    }

    public Set<ObjetivoEstrategico> getObjetivoEstrategico()
    {
	return objetivoEstrategico;
    }

    public void setObjetivoEstrategico(
    		Set<ObjetivoEstrategico> objetivoEstrategico)
    {
	this.objetivoEstrategico = objetivoEstrategico;
    }

    public Set<ObjetivoDeSoftware> getObjetivoDeSoftware()
    {
	return objetivoDeSoftware;
    }

    public void setObjetivoDeSoftware(
    		Set<ObjetivoDeSoftware> objetivoDeSoftware)
    {
	this.objetivoDeSoftware = objetivoDeSoftware;
    }

    public Set<NecessidadeDeInformacao> getNecessidadeDeInformacao()
    {
	return necessidadeDeInformacao;
    }

    public void setNecessidadeDeInformacao(
    		Set<NecessidadeDeInformacao> necessidadeDeInformacao)
    {
	this.necessidadeDeInformacao = necessidadeDeInformacao;
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

    @PreCreate
    @PreUpdate
    @PrePersist
    public void ajustes()
    {
	
	if (objetivoEstrategico == null)
	    objetivoEstrategico = new HashSet<ObjetivoEstrategico>();
	if (objetivoDeSoftware == null)
	    objetivoDeSoftware = new HashSet<ObjetivoDeSoftware>();
	if (objetivoDeMedicao == null)
	    objetivoDeMedicao = new HashSet<ObjetivoDeMedicao>();
	if (necessidadeDeInformacao == null)
	    necessidadeDeInformacao = new HashSet<NecessidadeDeInformacao>();
	
	//Transpoe os objets da tree view para listas separadas	
	if (objetivoEstrategico != null)
	    objetivoEstrategico.clear();
	if (objetivoDeSoftware != null)
	    objetivoDeSoftware.clear();
	if (objetivoDeMedicao != null)
	    objetivoDeMedicao.clear();
	if (necessidadeDeInformacao != null)
	    necessidadeDeInformacao.clear();
	 
	if (planoTree != null)
	{
	    for (TreeItemPlanoMedicao itemTree : planoTree)
	    {
		TreeItemPlanoMedicaoBase item = itemTree.getItem();

		if (item instanceof ObjetivoEstrategico)
		{
		    objetivoEstrategico.add((ObjetivoEstrategico) item);
		}
		else if (item instanceof ObjetivoDeSoftware)
		{
		    objetivoDeSoftware.add((ObjetivoDeSoftware) item);
		}
		else if (item instanceof ObjetivoDeMedicao)
		{
		    objetivoDeMedicao.add((ObjetivoDeMedicao) item);
		}
		else if (item instanceof NecessidadeDeInformacao)
		{
		    necessidadeDeInformacao.add((NecessidadeDeInformacao) item);
		}
	    }
	}

    }//ajustes

    /*
     * private Collection<Medicao> medicao;
     * 
     * private Collection<AnaliseDeMedicao> analiseDeMedicao;
     */

}
