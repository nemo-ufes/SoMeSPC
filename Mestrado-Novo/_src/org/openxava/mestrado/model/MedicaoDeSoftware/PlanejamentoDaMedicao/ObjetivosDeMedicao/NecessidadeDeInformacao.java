package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;

@Entity
@View(name="Simple", members="nome")
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")
})
public class NecessidadeDeInformacao {
	 
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
	private Collection<Medida> listaMedida;
    

	public Collection<Medida> getListaMedida() {
		return listaMedida;
	}

	public void setListaMedida(Collection<Medida> listaMedida) {
		this.listaMedida = listaMedida;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<NecessidadeDeInformacao> getSubnecessidade() {
		return subnecessidade;
	}

	public void setSubnecessidade(Collection<NecessidadeDeInformacao> subnecessidade) {
		this.subnecessidade = subnecessidade;
	}
/*
    @ManyToMany 
    @JoinTable(
  	      name="planoDeMedicao_necessidadeDeInformacao"
  	      , joinColumns={
  	    		  @JoinColumn(name="necessidadeDeInformacao_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      )
	private Collection<PlanoDeMedicao> planoDeMedicao;*/

/*	public Collection<PlanoDeMedicao> getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(Collection<PlanoDeMedicao> planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}
    */
    
	
}
 
