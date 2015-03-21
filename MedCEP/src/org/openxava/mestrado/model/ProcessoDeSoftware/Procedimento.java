package org.openxava.mestrado.model.ProcessoDeSoftware;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="nome; descricao"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class Procedimento {
 
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
	//@Column(length=50000)
	private String descricao;
	 
	//private Collection<AtividadeDeProjeto> atividadeDeProjeto;

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

/*	public Collection<AtividadeDeProjeto> getAtividadeDeProjeto() {
		return atividadeDeProjeto;
	}

	public void setAtividadeDeProjeto(
			Collection<AtividadeDeProjeto> atividadeDeProjeto) {
		this.atividadeDeProjeto = atividadeDeProjeto;
	}*/
	 
	
}
 
