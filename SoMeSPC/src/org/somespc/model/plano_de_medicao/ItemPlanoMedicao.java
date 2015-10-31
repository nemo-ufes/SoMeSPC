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

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;

import org.openxava.annotations.Hidden;
import org.openxava.annotations.ListProperties;
import org.openxava.annotations.NoCreate;
import org.openxava.annotations.NoModify;
import org.openxava.annotations.PreCreate;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;
import org.somespc.actions.NewTreeViewItemPlanoMedicaoAction;
import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.entidades_e_medidas.Medida;
import org.somespc.model.objetivos.NecessidadeDeInformacao;
import org.somespc.model.objetivos.ObjetivoDeMedicao;
import org.somespc.model.objetivos.ObjetivoDeSoftware;
import org.somespc.model.objetivos.ObjetivoEstrategico;

@Entity
@Views({ @View(members =
// "path;"
" item;"
		// + " path;"
		// + " treeOrder;"
		) })
public class ItemPlanoMedicao {

	@Id
	@TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "ITEM_PLANO_MED_ID", valueColumnName = "ID_TABLE_VALUE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
	@Hidden
	private Integer id;

	@ManyToOne
	@NoCreate
	@NoModify
	@ReferenceView("Simple")
	private ItemPlanoMedicaoBase item;

	public ItemPlanoMedicaoBase getItem() {
		return item;
	}

	@ManyToOne
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "planoDeMedicao_objetivoDeMedicao", joinColumns = {
			@JoinColumn(name = "planoDeMedicao_id") }, inverseJoinColumns = {
					@JoinColumn(name = "objetivoDeMedicao_id") })
	@ListProperties("nome")
	private Set<ObjetivoDeMedicao> objetivoDeMedicao;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "planoDeMedicao_objetivoEstrategico", joinColumns = {
			@JoinColumn(name = "planoDeMedicao_id") }, inverseJoinColumns = {
					@JoinColumn(name = "objetivoEstrategico_id") })
	@ListProperties("nome")
	private Set<ObjetivoEstrategico> objetivoEstrategico;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "planoDeMedicao_objetivoDeSoftware", joinColumns = {
			@JoinColumn(name = "planoDeMedicao_id") }, inverseJoinColumns = {
					@JoinColumn(name = "objetivoDeSoftware_id") })
	@ListProperties("nome")
	private Set<ObjetivoDeSoftware> objetivoDeSoftware;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "planoDeMedicao_necessidadeDeInformacao", joinColumns = {
			@JoinColumn(name = "planoDeMedicao_id") }, inverseJoinColumns = {
					@JoinColumn(name = "necessidadeDeInformacao_id") })
	@ListProperties("nome")
	private Set<NecessidadeDeInformacao> necessidadeDeInformacao;

	@Column(length = 700)
	private String path;

	@ManyToOne
	private PlanoDeMedicao planoDeMedicaoContainer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}

	public void setItem(ItemPlanoMedicaoBase item) {
		this.item = item;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public PlanoDeMedicao getPlanoDeMedicaoContainer() {
		return planoDeMedicaoContainer;
	}

	public void setPlanoDeMedicaoContainer(PlanoDeMedicao planoDeMedicaoContainer) {
		this.planoDeMedicaoContainer = planoDeMedicaoContainer;
	}

	public Set<ObjetivoDeMedicao> getObjetivoDeMedicao() {
		return objetivoDeMedicao;
	}

	public void setObjetivoDeMedicao(Set<ObjetivoDeMedicao> objetivoDeMedicao) {
		this.objetivoDeMedicao = objetivoDeMedicao;
	}

	public Set<ObjetivoEstrategico> getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(Set<ObjetivoEstrategico> objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

	public Set<ObjetivoDeSoftware> getObjetivoDeSoftware() {
		return objetivoDeSoftware;
	}

	public void setObjetivoDeSoftware(Set<ObjetivoDeSoftware> objetivoDeSoftware) {
		this.objetivoDeSoftware = objetivoDeSoftware;
	}

	public Set<NecessidadeDeInformacao> getNecessidadeDeInformacao() {
		return necessidadeDeInformacao;
	}

	public void setNecessidadeDeInformacao(Set<NecessidadeDeInformacao> necessidadeDeInformacao) {
		this.necessidadeDeInformacao = necessidadeDeInformacao;
	}

	@PreCreate
	@PreUpdate
	public void ajustes() {
		if (item != null) {
			if (item instanceof ObjetivoEstrategico) {
				this.nome = "OE - " + item.getNome();
			} else if (item instanceof ObjetivoDeSoftware) {
				this.nome = "OS - " + item.getNome();
			} else if (item instanceof ObjetivoDeMedicao) {
				this.nome = "OM - " + item.getNome();
			} else if (item instanceof NecessidadeDeInformacao) {
				this.nome = "NI - " + item.getNome();
			} else if (item instanceof Medida) {
				this.nome = "ME - " + item.getNome();
			}
			/*
			 * else if(item instanceof DefinicaoOperacionalDeMedida) { this.nome
			 * = "DE - " + item.getNome(); }
			 */else {
				this.nome = item.getNome();
			}
		}
		if (NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject != null) {
			ItemPlanoMedicao pti = (ItemPlanoMedicao) NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject;
			path = pti.getPath() + "/" + pti.getId();
		}
		NewTreeViewItemPlanoMedicaoAction.TreeItemSelectObject = null;
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
