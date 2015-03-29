package org.openxava.mestrado.calculators;

import org.openxava.calculators.*;

public class TipoDeEntidadeMensuravel implements ICalculator {

	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel;
	
	private String nome;

	public Object calculate() throws Exception {
		return tipoDeEntidadeMensuravel;
	}

	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}
	
}
