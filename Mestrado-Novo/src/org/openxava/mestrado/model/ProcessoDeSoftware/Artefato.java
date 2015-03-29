package org.openxava.mestrado.model.ProcessoDeSoftware;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.validators.*;

@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; tipoDeArtefato; descricao; elementoMensuravel;"),
	@View(name="Simple", members="nome"),
	@View(name="SimpleNoFrame", members="nome")
})
@Tabs({
	@Tab(properties="nome, tipoDeArtefato.nome", defaultOrder="${nome} asc")
})
@EntityValidator(
		value=ArtefatoValidator.class, 
		properties={
			@PropertyValue(name="tipoDeEntidadeMensuravel")
		}
)
public class Artefato extends EntidadeMensuravel {

	@ManyToOne @Required
	@ReferenceView("Simple")
	private TipoDeArtefato tipoDeArtefato;

	public TipoDeArtefato getTipoDeArtefato() {
		return tipoDeArtefato;
	}

	public void setTipoDeArtefato(TipoDeArtefato tipoDeArtefato) {
		this.tipoDeArtefato = tipoDeArtefato;
	}	
	
	@ManyToOne
	@Required
	@Transient
	@DefaultValueCalculator(
		value=TipoDeEntidadeMensuravelCalculator.class,
		properties={
			 @PropertyValue(name="nomeEntidade", value="Artefato")
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
	 
	/*private Collection<AtividadeDeProjeto> atividadeDeProjetoRequerido;
	 
	private Collection<AtividadeDeProjeto> atividadeDeProjetoProduzido;*/
	 
	
}
 
