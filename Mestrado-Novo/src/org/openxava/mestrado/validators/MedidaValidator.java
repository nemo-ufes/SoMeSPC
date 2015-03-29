package org.openxava.mestrado.validators;

import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class MedidaValidator implements IValidator { // Must implement IPropertyValidator (1)
	
	private TipoMedida tipoMedida; // Properties to be injected
	private ElementoMensuravel elementoMensuravel;  
	
	public void validate(Messages errors) throws Exception
	{
		if(elementoMensuravel != null
		&&	elementoMensuravel.getTipoElementoMensuravel().getNome().compareTo("Elemento Diretamente Mensurável") == 0 
		&& tipoMedida.getNome().compareTo("Medida Base") != 0)
			errors.add("elemento_diretamente_medida_base");
		
		if(elementoMensuravel != null
		&&	elementoMensuravel.getTipoElementoMensuravel().getNome().compareTo("Elemento Indiretamente Mensurável") == 0 
		&& tipoMedida.getNome().compareTo("Medida Derivada") != 0)
			errors.add("elemento_indiretamente_medida_derivada");		
	}

	public TipoMedida getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(TipoMedida tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	public ElementoMensuravel getElementoMensuravel() {
		return elementoMensuravel;
	}

	public void setElementoMensuravel(ElementoMensuravel elementoMensuravel) {
		this.elementoMensuravel = elementoMensuravel;
	}
	
}
