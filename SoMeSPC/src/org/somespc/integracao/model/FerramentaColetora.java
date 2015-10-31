package org.somespc.integracao.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.somespc.model.plano_de_medicao.TreeItemPlanoMedicao;

@Entity
public class FerramentaColetora {

	@Id
	@TableGenerator(name = "TABLE_GENERATOR", table = "ID_TABLE", pkColumnName = "ID_TABLE_NAME", pkColumnValue = "FERRAMENTA_COLETORA_ID", valueColumnName = "ID_TABLE_VALUE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GENERATOR")
	@Hidden
	private Integer id;

	@Column(length = 255, unique = true)
	@Required
	private String nome;

	@OneToMany(mappedBy = "ferramentaColetora", cascade = CascadeType.MERGE)
	private Collection<TreeItemPlanoMedicao> itensParaColetar;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<TreeItemPlanoMedicao> getItensParaColetar() {
		return itensParaColetar;
	}

	public void setItensParaColetar(Collection<TreeItemPlanoMedicao> itensParaColetar) {
		this.itensParaColetar = itensParaColetar;
	}

}
