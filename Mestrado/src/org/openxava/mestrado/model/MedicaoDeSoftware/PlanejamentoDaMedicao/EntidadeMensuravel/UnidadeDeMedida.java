package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;


/**
 * Requisitos
 */
@Entity
@Views({
	@View(members="nome; descricao"),
	@View(name="Simple", members="nome"),
	})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class UnidadeDeMedida {
 
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
	 
	@OneToMany(mappedBy="unidadeDeMedida")
	private Collection<Medida> medida;

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

	public Collection<Medida> getMedida() {
		return medida;
	}

	public void setMedida(Collection<Medida> medida) {
		this.medida = medida;
	}
	
}
 
