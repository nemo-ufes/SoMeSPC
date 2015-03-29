package org.openxava.mestrado.actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;

public class SaveObjetivoDeSoftwareAction extends SaveAction {
	
    public void execute() throws Exception {
    	
    	super.execute();
    	String id = getView().getValueString("id");
    	
    	ObjetivoDeSoftware o = XPersistence.getManager().find(ObjetivoDeSoftware.class, id);
    	
    	if(o.getObjetivoEstrategico() != null && o.getObjetivoEstrategico().size() < 1)
    	   	addMessage("falta_objetivo_estrategico");
    	
    }

}
