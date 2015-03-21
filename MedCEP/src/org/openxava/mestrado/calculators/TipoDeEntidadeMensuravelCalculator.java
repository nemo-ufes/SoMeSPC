package org.openxava.mestrado.calculators;

import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;

public class TipoDeEntidadeMensuravelCalculator implements ICalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2453681285474844641L;
	private String nomeEntidade; 
	
	public Object calculate() throws Exception {
		Query query = XPersistence.getManager().createQuery(
				"from TipoDeEntidadeMensuravel t where t.nome = '"+ nomeEntidade +"'");
		
		TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel = (TipoDeEntidadeMensuravel) query.getSingleResult();
		return tipoDeEntidadeMensuravel; 
	}

	public String getNomeEntidade() {
		return nomeEntidade;
	}

	public void setNomeEntidade(String nomeEntidade) {
		this.nomeEntidade = nomeEntidade;
	}
		
}