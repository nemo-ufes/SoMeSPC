/*
    MedCEP - A powerful tool for measure
    
    Copyright (C) 2013 Ciro Xavier Maretto
    Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca                          

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/lgpl.html>.    
 */

package org.medcep.validators;

import org.medcep.model.medicao.planejamento.*;
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