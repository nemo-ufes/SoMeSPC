package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.validators.*;


@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; baseadoEm; dependeDe; requer; produz; realizadoPor; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")	
})
@EntityValidator(
		value=AtividadeDeProjetoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class AtividadeDeProjeto extends AtividadeInstanciada {
	 
	@ManyToMany(fetch=FetchType.LAZY) 
    @JoinTable(
	      name="ProcessoDeProjeto_AtividadeDeProjeto"
	      , joinColumns={
	    		  @JoinColumn(name="atividadeDeProjeto_id")
	       }
	      , inverseJoinColumns={
	    		  @JoinColumn(name="processoDeProjeto_id")
	       }
	      )
	@ListProperties("nome")
	private Collection<ProcessoDeProjeto> processoDeProjeto;
	
	public Collection<ProcessoDeProjeto> getProcessoDeProjeto() {
		return processoDeProjeto;
	}

	public void setProcessoDeProjeto(Collection<ProcessoDeProjeto> processoDeProjeto) {
		this.processoDeProjeto = processoDeProjeto;
	}

	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Atividade de Projeto")
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
