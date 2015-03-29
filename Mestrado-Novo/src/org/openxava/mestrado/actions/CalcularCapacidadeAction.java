package org.openxava.mestrado.actions;

import org.openxava.actions.*;
import org.openxava.mestrado.model.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.jpa.*;

public class CalcularCapacidadeAction extends SaveAction { 
																	
	public void execute() throws Exception {
		
		super.execute();
		
		String id = getView().getValueString("id"); 
		
		AnaliseDeComportamentoDeProcesso c = XPersistence.getManager().
									find(AnaliseDeComportamentoDeProcesso.class, id);
		
		if(c.getCapacidadeDeProcesso() == null)
			c.getCapacidadeDeProcesso().getCapacidade();
		
		//c.getCapacidade(); 
		//getView().refresh(); 

		/*addMessage("invoice_created_from_order", // Confirmation message (4)
		order.getInvoice());*/
	}
}