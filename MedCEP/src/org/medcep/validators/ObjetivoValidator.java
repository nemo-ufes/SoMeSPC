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

import org.openxava.util.*;
import org.openxava.validators.*;

public class ObjetivoValidator implements IPropertyValidator { // Must implement IPropertyValidator (1)
	
	/*@PropertyValidator(UnitPriceValidator.class) // Contains the validation logic*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6864003388701065320L;

	public void validate( // Required because of IPropertyValidator (2)
		Messages errors, // Here you add the error messages (3)
		Object object, // The value to validate
		String objectName, // The entity name, usually to use in message
		String propertyName) // The property name, usually to use in message
	{
		if (object == null) 
			return;
/*		if (!(object instanceof BigDecimal)) {
			errors.add( // If you add an error the validation fails
			"expected_type", // Message id in i18n file
			propertyName, // Arguments for i18n message
			objectName,
			"bigdecimal");
			return;
		} 
		BigDecimal n = (BigDecimal) object;
		if (n.intValue() > 1000) {
			errors.add("not_greater_1000"); // Message id in i18n file
		}*/
	}

}
