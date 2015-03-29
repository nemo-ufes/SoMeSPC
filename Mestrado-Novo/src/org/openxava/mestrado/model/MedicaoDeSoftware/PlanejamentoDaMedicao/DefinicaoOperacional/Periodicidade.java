package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;

/**
 * Uma vez em cada projeto; Uma vez em cada ocorrência da atividade; 
 */
@Entity
@Views({
	@View(members="nome; descricao"),
	@View(name="Simple", members="nome"),
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class Periodicidade {
 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
    @Column(length=500, unique=true) @Required 
    private String nome;
	
	@Stereotype("TEXT_AREA")	
	@Column(columnDefinition="TEXT")
	private String descricao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	 
	@OneToMany(mappedBy="periodicidadeDeMedicao")
	private Collection<DefinicaoOperacionalDeMedida> periodicidadeDeMedicao;
	 
	@OneToMany(mappedBy="periodicidadeDeAnaliseDeMedicao")
	private Collection<DefinicaoOperacionalDeMedida> periodicidadeDeAnaliseDeMedicao;

	public Collection<DefinicaoOperacionalDeMedida> getPeriodicidadeDeMedicao() {
		return periodicidadeDeMedicao;
	}

	public void setPeriodicidadeDeMedicao(
			Collection<DefinicaoOperacionalDeMedida> periodicidadeDeMedicao) {
		this.periodicidadeDeMedicao = periodicidadeDeMedicao;
	}

	public Collection<DefinicaoOperacionalDeMedida> getPeriodicidadeDeAnaliseDeMedicao() {
		return periodicidadeDeAnaliseDeMedicao;
	}

	public void setPeriodicidadeDeAnaliseDemedicao(
			Collection<DefinicaoOperacionalDeMedida> periodicidadeDeAnaliseDeMedicao) {
		this.periodicidadeDeAnaliseDeMedicao = periodicidadeDeAnaliseDeMedicao;
	}

	
	 
	
}
 
