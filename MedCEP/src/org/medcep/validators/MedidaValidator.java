/*
 * MedCEP - A powerful tool for measure
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
package org.medcep.validators;

import org.medcep.model.entidades_e_medidas.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class MedidaValidator implements IValidator
{ // Must implement IPropertyValidator (1)

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TipoMedida tipoMedida; // Properties to be injected
    private ElementoMensuravel elementoMensuravel;

    public void validate(Messages errors) throws Exception
    {
	if (elementoMensuravel != null
		&& elementoMensuravel.getTipoElementoMensuravel().getNome().compareTo("Elemento Diretamente Mensurável") == 0
		&& tipoMedida.getNome().compareTo("Medida Base") != 0)
	    errors.add("elemento_diretamente_medida_base");

	if (elementoMensuravel != null
		&& elementoMensuravel.getTipoElementoMensuravel().getNome().compareTo("Elemento Indiretamente Mensurável") == 0
		&& tipoMedida.getNome().compareTo("Medida Derivada") != 0)
	    errors.add("elemento_indiretamente_medida_derivada");
    }

    public TipoMedida getTipoMedida()
    {
	return tipoMedida;
    }

    public void setTipoMedida(TipoMedida tipoMedida)
    {
	this.tipoMedida = tipoMedida;
    }

    public ElementoMensuravel getElementoMensuravel()
    {
	return elementoMensuravel;
    }

    public void setElementoMensuravel(ElementoMensuravel elementoMensuravel)
    {
	this.elementoMensuravel = elementoMensuravel;
    }

}
