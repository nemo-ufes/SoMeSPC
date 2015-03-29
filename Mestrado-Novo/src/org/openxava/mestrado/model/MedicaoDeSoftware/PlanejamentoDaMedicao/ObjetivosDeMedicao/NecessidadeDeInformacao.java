package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
@Views({
	@View(members="nome;"
			+ " subnecessidade;"
			+ " indicadoPelosObjetivos;"
			+ " medidas;"
	),	
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class NecessidadeDeInformacao extends TreeItemPlanoMedicaoBase {
    
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
  	      name="subnecessidade"
  	      , joinColumns={
  	       @JoinColumn(name="necessidadeDeInformacao_id")
  	       }
  	      , inverseJoinColumns={
  	       @JoinColumn(name="necessidadeDeInformacao_id2")
  	       }
  	      )
    @ListProperties("nome")
	private Collection<NecessidadeDeInformacao> subnecessidade;
    
    @ManyToMany
    @JoinTable(
  	      name="objetivo_identifica_necessidade"
  	      , joinColumns={
  	    		  @JoinColumn(name="necessidadeDeInformacao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="objetivo_id")
  	       }
  	      )
    @ListProperties("nome")
    @NewAction("NecessidadeDeInformacao.addObjetivoDeMedicao")
	private Collection<Objetivo> indicadoPelosObjetivos;
    
    public Collection<Objetivo> getIndicadoPelosObjetivos() {
		return indicadoPelosObjetivos;
	}

	public void setIndicadoPelosObjetivos(
			Collection<Objetivo> indicadoPelosObjetivos) {
		this.indicadoPelosObjetivos = indicadoPelosObjetivos;
	}

	@ManyToMany
    @JoinTable(
	      name="medida_necessidadeDeInformacao"
	      , joinColumns={
	    		  @JoinColumn(name="necessidadeDeInformacao_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="medida_id")
	       }
	      )
	@ListProperties("nome, tipoMedida.nome, mnemonico")
	private Collection<Medida> medidas;

	public Collection<Medida> getMedidas() {
		return medidas;
	}

	public void setMedidas(Collection<Medida> medidas) {
		this.medidas = medidas;
	}

	
	public Collection<NecessidadeDeInformacao> getSubnecessidade() {
		return subnecessidade;
	}

	public void setSubnecessidade(Collection<NecessidadeDeInformacao> subnecessidade) {
		this.subnecessidade = subnecessidade;
	}
/*
	private Collection<PlanoDeMedicao> planoDeMedicao;*/

/*	public Collection<PlanoDeMedicao> getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(Collection<PlanoDeMedicao> planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}
    */
    
	
}
 
