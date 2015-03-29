package org.openxava.mestrado.actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.mestrado.model.ComportamentoDeProcessosDeSoftware.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.ObjetivosDeMedicao.*;

public class SaveObjetivoDeMedicaoAction extends SaveAction {
	
    public void execute() throws Exception {
    	
    	super.execute();
    	String id = getView().getValueString("id");
    	
    	ObjetivoDeMedicao o = XPersistence.getManager().find(ObjetivoDeMedicao.class, id);
    	
    	if(o.getObjetivoEstrategico() != null && o.getObjetivoEstrategico().size() < 1)
    	   	addMessage("falta_objetivo_estrategico");
    	
    	if(o.getObjetivoDeSoftware() != null && o.getObjetivoDeSoftware().size() < 1)
    	   	addMessage("falta_objetivo_software");
    	
    }

}
