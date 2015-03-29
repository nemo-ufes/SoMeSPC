package org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel;

import javax.persistence.*;

import org.openxava.annotations.*;

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
 
