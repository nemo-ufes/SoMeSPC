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

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.TableGenerator;

import org.openxava.annotations.CollectionView;
import org.openxava.annotations.Editor;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.ListProperties;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.Tab;
import org.openxava.annotations.Tabs;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;
import org.somespc.model.organizacao_de_software.RecursoHumano;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({ @View(members = "data;" + "nome, " + "versao; " + "descricao; " + "alteracoes; " + "recursoHumano; "
		+ "planoTree; " + "MedidasDoPlano { medidaPlanoDeMedicao }"), @View(name = "Simple", members = "nome") })
@Tabs({ @Tab(properties = "nome, versao", defaultOrder = "${nome} asc, ${versao} desc") })
public class PlanoDeMedicao {

	public String getAlteracoes() {
		return alteracoes;
	}

	public void setAlteracoes(String alteracoes) {
		this.alteracoes = alteracoes;
	}

	@Id
	@TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "PLANO_MEDICAO_ID", valueColumnName = "ID_TABLE_VALUE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
	@Hidden
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	@JoinTable(name = "recursoHumano_planoDeMedicao", joinColumns = {
			@JoinColumn(name = "planoDeMedicao_id") }, inverseJoinColumns = { @JoinColumn(name = "recursoHumano_id") })
	@ListProperties("nome")
	private Collection<RecursoHumano> recursoHumano;

	@OneToMany(mappedBy = "planoDeMedicaoContainer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Editor("TreeView")
	@ListProperties("nome")
	@OrderBy("path")
	private Set<ItemPlanoMedicao> planoTree;

	public Set<ItemPlanoMedicao> getPlanoTree() {
		return planoTree;
	}

	public void setPlanoTree(Set<ItemPlanoMedicao> planoTree) {
		this.planoTree = planoTree;
	}

	@OneToMany(mappedBy = "planoDeMedicao", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@ListProperties("medida.nome, definicaoOperacionalDeMedida.nome")
	@CollectionView("PlanoMedicao")
	private Set<MedidaPlanoDeMedicao> medidaPlanoDeMedicao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Collection<RecursoHumano> getRecursoHumano() {
		return recursoHumano;
	}

	public void setRecursoHumano(Collection<RecursoHumano> recursoHumano) {
		this.recursoHumano = recursoHumano;
	}


	public Set<MedidaPlanoDeMedicao> getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(HashSet<MedidaPlanoDeMedicao> medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}
}
