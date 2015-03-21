package org.openxava.mestrado.model.ProcessoDeSoftware;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.mestrado.calculators.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;


@Entity
@Views({
	@View(members="nome; tipoDeEntidadeMensuravel; baseadoEm; dependeDe; adotaProcedimento; requer; produz; realizadoPor; elementoMensuravel;"),
	@View(name="Simple", members="nome")
})
@Tabs({
	@Tab(properties="nome", defaultOrder="${nome} asc")	
})
public class AtividadeDeProjeto extends AtividadeInstanciada {
	 
	@ManyToOne
	private ProcessoDeProjeto processoDeProjeto;

	public ProcessoDeProjeto getProcessoDeProjeto() {
		return processoDeProjeto;
	}

	public void setProcessoDeProjeto(ProcessoDeProjeto processoDeProjeto) {
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
