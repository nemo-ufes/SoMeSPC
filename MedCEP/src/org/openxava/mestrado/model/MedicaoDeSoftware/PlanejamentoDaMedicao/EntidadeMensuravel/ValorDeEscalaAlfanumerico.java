package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import javax.persistence.*;

@Entity
public class ValorDeEscalaAlfanumerico extends ValorDeEscala {
    
	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
		 
}
 
