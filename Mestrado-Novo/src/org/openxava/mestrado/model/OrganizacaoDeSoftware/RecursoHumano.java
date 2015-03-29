package org.openxava.mestrado.model.OrganizacaoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import org.openxava.annotations.*;
import org.openxava.mestrado.model.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="nome;"),
	//@View(members="nome; tipoDeEntidadeMensuravel; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tab(properties="nome", defaultOrder="${nome} asc")
/*@EntityValidator(
		value=RecursoHumanoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)*/
public class RecursoHumano {//extends EntidadeMensuravel {
	 
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;   
    
    @Column(length=500, unique=true) @Required
	private String nome;
        
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
/*    @ManyToMany 
    @JoinTable(
  	      name="recursoHumano_planoDeMedicao"
  	      , joinColumns={
  	    		  @JoinColumn(name="recursoHumano_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="planoDeMedicao_id")
  	       }
  	      )
	private Collection<PlanoDeMedicao> planoDeMedicao;

	public Collection<PlanoDeMedicao> getPlanoDeMedicao() {
		return planoDeMedicao;
	}

	public void setPlanoDeMedicao(Collection<PlanoDeMedicao> planoDeMedicao) {
		this.planoDeMedicao = planoDeMedicao;
	}
    */

    @ManyToMany 
    @JoinTable(
  	      name="equipe_recursoHumano"
  	      , joinColumns={
  	    		  @JoinColumn(name="recursoHumano_id")
  	       }
  	      , inverseJoinColumns={
  	    		  @JoinColumn(name="equipe_id")
  	       }
  	      )
	private Collection<Equipe> equipe;
	 
	public Collection<Equipe> getEquipe() {
		return equipe;
	}

	public void setEquipe(Collection<Equipe> equipe) {
		this.equipe = equipe;
	}

	@OneToMany(mappedBy="recursoHumano") 
	private Collection<AlocacaoEquipe> alocacaoEquipe;

	public Collection<AlocacaoEquipe> getAlocacaoEquipe() {
		return alocacaoEquipe;
	}

	public void setAlocacaoEquipe(Collection<AlocacaoEquipe> alocacaoEquipe) {
		this.alocacaoEquipe = alocacaoEquipe;
	}
    
	@OneToMany(mappedBy="executorDaMedicao")
	private Collection<Medicao> medicaoExecutada;
		 
	public Collection<Medicao> getMedicaoExecutada() {
		return medicaoExecutada;
	}

	public void setMedicaoExecutada(Collection<Medicao> medicaoExecutada) {
		this.medicaoExecutada = medicaoExecutada;
	}

	@OneToMany(mappedBy="registradoPor")
	private Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso;

	public Collection<BaselineDeDesempenhoDeProcesso> getBaselineDeDesempenhoDeProcesso() {
		return baselineDeDesempenhoDeProcesso;
	}

	public void setBaselineDeDesempenhoDeProcesso(
			Collection<BaselineDeDesempenhoDeProcesso> baselineDeDesempenhoDeProcesso) {
		this.baselineDeDesempenhoDeProcesso = baselineDeDesempenhoDeProcesso;
	}
	
/*	 @Enumerated(EnumType.STRING)
	 private Sex sexo;
	 public enum Sex { MALE, FEMALE };
	 
	public Sex getSexo() {
		return sexo;
	}

	public void setSexo(Sex sexo) {
		this.sexo = sexo;
	}*/
	
/*	@ManyToOne
	//@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Recurso Humano")
			 }
	)
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}*/
	 

	
	 
	
/*	private Collection<Equipe> equipe;
 
	private Collection<AnaliseDeMedicao> analiseDeMedicao;

	private Collection<AtividadeDeProjeto> atividadeDeProjeto;*/
	 
}
 
