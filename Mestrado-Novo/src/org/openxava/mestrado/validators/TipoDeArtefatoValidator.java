package org.openxava.mestrado.validators;

import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class TipoDeArtefatoValidator implements IValidator { // Must implement IPropertyValidator (1)
	
	private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel; // Properties to be injected
	
	public void validate(Messages errors) throws Exception
	{
		if(tipoDeEntidadeMensuravel.getNome().compareTo("Tipo de Artefato")!=0)
			errors.add("tipo_entidade_mensuravel_fixo");
		
	}

	public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel() {
		return tipoDeEntidadeMensuravel;
	}

	public void setTipoDeEntidadeMensuravel(
			TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel) {
		this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
	}

	
	
}
