package org.openxava.mestrado.model.OrganizacaoDeSoftware;

import java.util.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

@Entity
@Views({
	@View(members="Equipe [nome; recursoHumano; alocacaoEquipe]"),
	@View(name="Simple", members="nome")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
public class Equipe {
 
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

    @OneToMany(mappedBy="equipe", cascade=CascadeType.REMOVE)
    @ListProperties("recursoHumano.nome, papelRecursoHumano.nome, inicio, fim")
	private Collection<AlocacaoEquipe> alocacaoEquipe;
	 
    @ManyToMany 
    @JoinTable(
  	      name="equipe_projeto"
  	      , joinColumns={
  	    		  @JoinColumn(name="equipe_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="projeto_id")
  	       }
  	      )
	private Collection<Projeto> projeto;
    
    @ManyToMany 
    @JoinTable(
  	      name="equipe_recursoHumano"
  	      , joinColumns={
  	    		  @JoinColumn(name="equipe_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="recursoHumano_id")
  	       }
  	      )
    @ListProperties("nome")
	private Collection<RecursoHumano> recursoHumano;

	public Collection<RecursoHumano> getRecursoHumano() {
		return recursoHumano;
	}

	public void setRecursoHumano(Collection<RecursoHumano> recursoHumano) {
		this.recursoHumano = recursoHumano;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<AlocacaoEquipe> getAlocacaoEquipe() {
		return alocacaoEquipe;
	}

	public void setAlocacaoEquipe(Collection<AlocacaoEquipe> alocacaoEquipe) {
		this.alocacaoEquipe = alocacaoEquipe;
	}

	public Collection<Projeto> getProjeto() {
		return projeto;
	}

	public void setProjeto(Collection<Projeto> projeto) {
		this.projeto = projeto;
	}
	 
	
}
 
