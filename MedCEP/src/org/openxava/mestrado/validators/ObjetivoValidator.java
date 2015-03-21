package org.openxava.mestrado.validators;

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
