package org.somespc.actions;

import org.openxava.actions.SaveAction;
import org.openxava.jpa.XPersistence;
import org.somespc.model.objetivos.ObjetivoDeMedicao;

public class CreateObjetivoEstrategicoAction extends SaveAction {
	
	public void execute() throws Exception {
		
		super.execute();
		
		String id = getView().getValueString("id");
    	
    	ObjetivoDeMedicao o = XPersistence.getManager().find(ObjetivoDeMedicao.class, id);
    	
    	if(o.getObjetivoEstrategico() != null && o.getObjetivoEstrategico().size() < 1)
    		o.ajustaObjetivos();    		
	 }
}
