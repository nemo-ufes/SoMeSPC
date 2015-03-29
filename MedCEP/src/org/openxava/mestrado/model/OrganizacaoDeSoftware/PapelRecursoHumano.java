package org.openxava.mestrado.model.OrganizacaoDeSoftware;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;


/**
 * Gerente do Projeto
 * 
 */
@Entity
@Views({
	@View(members="Papel do Recurso Humano  [nome; descricao] "),
	@View(name="Simple", members="nome")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
public class PapelRecursoHumano {
 
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
	private String descricao;
	 
	@OneToMany(mappedBy="responsavelPelaMedicao")
	private Collection<DefinicaoOperacionalDeMedida> responsavelPelaMedicao;
	 
	@OneToMany(mappedBy="responsavelPelaAnaliseDeMedicao")
	private Collection<DefinicaoOperacionalDeMedida> responsavelPelaAnaliseDeMedicao;
	 
	@OneToMany(mappedBy="papelRecursoHumano")
	private Collection<AlocacaoEquipe> alocacaoEquipe;

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

	public Collection<AlocacaoEquipe> getAlocacaoEquipe() {
		return alocacaoEquipe;
	}

	public void setAlocacaoEquipe(Collection<AlocacaoEquipe> alocacaoEquipe) {
		this.alocacaoEquipe = alocacaoEquipe;
	}

	public Collection<DefinicaoOperacionalDeMedida> getResponsavelPelaMedicao() {
		return responsavelPelaMedicao;
	}

	public void setResponsavelPelaMedicao(
			Collection<DefinicaoOperacionalDeMedida> responsavelPelaMedicao) {
		this.responsavelPelaMedicao = responsavelPelaMedicao;
	}

	public Collection<DefinicaoOperacionalDeMedida> getResponsavelPelaAnaliseDeMedicao() {
		return responsavelPelaAnaliseDeMedicao;
	}

	public void setResponsavelPelaAnaliseDeMedicao(
			Collection<DefinicaoOperacionalDeMedida> responsavelPelaAnaliseDeMedicao) {
		this.responsavelPelaAnaliseDeMedicao = responsavelPelaAnaliseDeMedicao;
	}
	
	
}
 
