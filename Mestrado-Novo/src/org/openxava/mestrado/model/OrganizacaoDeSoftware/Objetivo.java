package org.openxava.mestrado.model.OrganizacaoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Views({
	//@View(members="nome; necessidadeDeInformacao; indicadores; subobjetivo;"),
	@View(members="nome"),
	@View(name="Simple", members="nome")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
public class Objetivo extends TreeItemPlanoMedicaoBase { 
	 
    @Column(length=500, unique=true) @Required
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
    
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
    @ListProperties("nome")
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
    @ListProperties("nome")
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
    @ListProperties("nome")
	private Collection<Projeto> projeto;

	public Collection<Projeto> getProjeto() {
		return projeto;
	}

	public void setProjeto(Collection<Projeto> projeto) {
		this.projeto = projeto;
	}

}
 
 
