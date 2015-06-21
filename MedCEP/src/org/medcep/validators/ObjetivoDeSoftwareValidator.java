package org.medcep.validators;

import java.util.Collection;

import org.medcep.model.organizacao.ObjetivoEstrategico;
import org.openxava.util.*;
import org.openxava.validators.*;

public class ObjetivoDeSoftwareValidator implements IValidator
{ // Must implement IPropertyValidator (1)
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection<ObjetivoEstrategico> objetivoEstrategico;

	public void validate(Messages errors) throws Exception {
		if (objetivoEstrategico == null || objetivoEstrategico.isEmpty()){
			System.out.println("Passei aqui senhor");
			errors.add("falta_objetivo_estrategico");
		}
	}

	public Collection<ObjetivoEstrategico> getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(Collection<ObjetivoEstrategico> objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

}
