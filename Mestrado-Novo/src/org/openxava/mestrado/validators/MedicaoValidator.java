package org.openxava.mestrado.validators;

import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.EntidadeMensuravel.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class MedicaoValidator implements IValidator {

	private MedidaPlanoDeMedicao medidaPlanoDeMedicao;
	private EntidadeMensuravel entidadeMensuravel;
	
	public void validate(Messages errors) throws Exception
	{
		ElementoMensuravel elem = medidaPlanoDeMedicao.getMedida().getElementoMensuravel();
		
		boolean possui = false;
		if(entidadeMensuravel != null && entidadeMensuravel.getElementoMensuravel() != null)
			for (ElementoMensuravel e : entidadeMensuravel.getElementoMensuravel())
			{
				if(e.getNome().compareTo(elem.getNome()) == 0)
				{
					possui = true;
					break;
				}
			}
		if(possui == false)
			errors.add("entidade_mensuravel_ter_elementos_da_medida");		
	}

	public MedidaPlanoDeMedicao getMedidaPlanoDeMedicao() {
		return medidaPlanoDeMedicao;
	}

	public void setMedidaPlanoDeMedicao(MedidaPlanoDeMedicao medidaPlanoDeMedicao) {
		this.medidaPlanoDeMedicao = medidaPlanoDeMedicao;
	}

	public EntidadeMensuravel getEntidadeMensuravel() {
		return entidadeMensuravel;
	}

	public void setEntidadeMensuravel(EntidadeMensuravel entidadeMensuravel) {
		this.entidadeMensuravel = entidadeMensuravel;
	}


	
}
