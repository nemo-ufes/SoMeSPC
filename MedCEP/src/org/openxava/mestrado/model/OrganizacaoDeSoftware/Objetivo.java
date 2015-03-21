package org.openxava.mestrado.model.OrganizacaoDeSoftware;

import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	@View(members="nome; necessidadeDeInformacao; indicadores; subobjetivo"),
	@View(name="Simple", members="nome")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
public class Objetivo { 
	 
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
    
    @ManyToMany
    @JoinTable(
	      name="medida_objetivo"
	      , joinColumns={
	    		  @JoinColumn(name="objetivo_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      )
    @ListProperties("nome, mnemonico")	
	private Collection<Medida> indicadores;
    
    @ManyToMany
    @JoinTable(
	      name="subobjetivo"
	      , joinColumns={
	    		  @JoinColumn(name="objetivo_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="objetivo_id2")
	       }
	      )
	private Collection<Objetivo> subobjetivo;
        
    @ManyToMany
    @JoinTable(
  	      name="objetivo_identifica_necessidade"
  	      , joinColumns={
  	    		  @JoinColumn(name="objetivo_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="necessidadeDeInformacao_id")
  	       }
  	      )
    private Collection<NecessidadeDeInformacao> necessidadeDeInformacao;

	public Collection<Objetivo> getSubobjetivo() {
		return subobjetivo;
	}

	public void setSubobjetivo(Collection<Objetivo> subobjetivo) {
		this.subobjetivo = subobjetivo;
	}

	public Collection<NecessidadeDeInformacao> getNecessidadeDeInformacao() {
		return necessidadeDeInformacao;
	}

	public void setNecessidadeDeInformacao(
			Collection<NecessidadeDeInformacao> necessidadeDeInformacao) {
		this.necessidadeDeInformacao = necessidadeDeInformacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<Medida> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(Collection<Medida> indicadores) {
		this.indicadores = indicadores;
	}

    @ManyToMany
    @JoinTable(
  	      name="objetivo_projeto"
  	      , joinColumns={
  	    		  @JoinColumn(name="projeto_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivo_id")
  	       }
  	      )
	private Collection<Projeto> projeto;

	public Collection<Projeto> getProjeto() {
		return projeto;
	}

	public void setProjeto(Collection<Projeto> projeto) {
		this.projeto = projeto;
	}
    
    
}
 
 
