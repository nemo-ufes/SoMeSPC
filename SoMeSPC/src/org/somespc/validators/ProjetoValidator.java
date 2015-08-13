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

import org.somespc.model.entidades_e_medidas.*;
import org.openxava.util.*;
import org.openxava.validators.*;

public class ProjetoValidator implements IValidator
{ // Must implement IPropertyValidator (1)

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel; // Properties to be injected

    public void validate(Messages errors) throws Exception
    {
	if (tipoDeEntidadeMensuravel.getNome().compareTo("Projeto") != 0)
	    errors.add("tipo_entidade_mensuravel_fixo");

    }

    public TipoDeEntidadeMensuravel getTipoDeEntidadeMensuravel()
    {
	return tipoDeEntidadeMensuravel;
    }

    public void setTipoDeEntidadeMensuravel(
	    TipoDeEntidadeMensuravel tipoDeEntidadeMensuravel)
    {
	this.tipoDeEntidadeMensuravel = tipoDeEntidadeMensuravel;
    }

}
