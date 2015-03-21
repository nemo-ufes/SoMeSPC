package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import javax.persistence.*;

@Entity
public class ValorDeEscalaNumerico extends ValorDeEscala {
 
	private float valorNumerico;

	public float getValorNumerico() {
		return valorNumerico;
	}

	public void setValorNumerico(float valor) {
		this.valorNumerico = valor;
	}
	
}
 
