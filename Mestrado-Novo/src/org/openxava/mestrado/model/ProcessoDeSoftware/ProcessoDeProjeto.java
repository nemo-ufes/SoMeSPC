package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.OrganizacaoDeSoftware.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; baseadoEm; projeto; descricao; atividadeDeProjeto; elementoMensuravel;"),
	@View(name="Simple", members="nome"),
	})
@Tab(properties="nome, versao", defaultOrder="${nome} asc, ${versao} desc")
@EntityValidator(
		value=ProcessoDeProjetoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class ProcessoDeProjeto extends ProcessoInstanciado {
	
	@OneToOne
	@ReferenceView("Simple")
	private Projeto projeto;

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	/*@OneToMany(mappedBy="processoDeProjeto")
	@ListProperties("nome") 
	@NewAction("ProcessoDeProjeto.AddAtividadeProjetoAction")
	//@NewAction("EntidadeMensuravel.AddElementoMensuravel")
	private Collection<AtividadeDeProjeto> atividadeDeProjeto;*/
	
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoDeProjeto_AtividadeDeProjeto"
	      , joinColumns={
	    		  @JoinColumn(name="processoDeProjeto_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="atividadeDeProjeto_id")
	       }
	      )
	@ListProperties("nome")
	@NewAction("ProcessoDeProjeto.add")
	private Collection<AtividadeDeProjeto> atividadeDeProjeto;
	
	public Collection<AtividadeDeProjeto> getAtividadeDeProjeto() {
		return atividadeDeProjeto;
	}

	public void setAtividadeDeProjeto(
			Collection<AtividadeDeProjeto> atividadeDeProjeto) {
		this.atividadeDeProjeto = atividadeDeProjeto;
	}
	
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Processo de Software de Projeto")
			 }
	)
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;
	
	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}
	
}
 
