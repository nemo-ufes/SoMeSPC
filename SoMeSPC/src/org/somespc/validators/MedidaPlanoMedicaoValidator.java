/*
 * SoMeSPC - powerful tool for measurement
 * 
 * Copyright (C) 2013 Ciro Xavier Maretto
 * Copyright (C) 2015 Henrique Néspoli Castro, Vinícius Soares Fonseca
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.somespc.validators;

import org.somespc.model.definicao_operacional_de_medida.DefinicaoOperacionalDeMedida;
import org.somespc.model.entidades_e_medidas.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class MedidaPlanoMedicaoValidator implements IValidator
{ // Must implement IValidator

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Medida medida; // Properties to be injected 
    private DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida; // Properties to be injected 

    public void validate(Messages errors) throws Exception
    {
	if (medida == null || medida.getId() == 0)
	    errors.add("medidaPlanoMedicao_medida_operacional_nao_selecionada");
	if (definicaoOperacionalDeMedida == null
		|| definicaoOperacionalDeMedida.getMedida() == null
		|| definicaoOperacionalDeMedida.getMedida().getId() == 0)
	    errors.add("medidaPlanoMedicao_definicao_operacional_nao_selecionada");
	else if (definicaoOperacionalDeMedida.getMedida().getId().compareTo(medida.getId()) != 0)
	    errors.add("medidaPlanoMedicao_Medida_DefMedida");
    }

    public Medida getMedida()
    {
	return medida;
    }

    public void setMedida(Medida medida)
    {
	this.medida = medida;
    }

    public DefinicaoOperacionalDeMedida getDefinicaoOperacionalDeMedida()
    {
	return definicaoOperacionalDeMedida;
    }

    public void setDefinicaoOperacionalDeMedida(
	    DefinicaoOperacionalDeMedida definicaoOperacionalDeMedida)
    {
	this.definicaoOperacionalDeMedida = definicaoOperacionalDeMedida;
    }

}