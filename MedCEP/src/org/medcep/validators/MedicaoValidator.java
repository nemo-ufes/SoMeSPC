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
