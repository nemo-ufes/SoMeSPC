package org.openxava.mestrado.validators;

import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.DefinicaoOperacional.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class MedidaPlanoMedicaoValidator implements IValidator { // Must implement IValidator
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -609729388992201878L;
	private Medida medida; // Properties to be injected from Order
	private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida; // Properties to be injected from Order
	
	public void validate(Messages errors) throws Exception
	{
		if(definicaoOperacionalDeMedida.getMedida().getId().compareTo(medida.getId())!=0)
			errors.add("medidaPlanoMedicao_Medida_DefMedida");
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida() {
		return definicaoOperacionalDeMedida;
	}

	public void setDefinicaoOperacionalDeMedida(
			DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida) {
		this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
	}

	
	
}	