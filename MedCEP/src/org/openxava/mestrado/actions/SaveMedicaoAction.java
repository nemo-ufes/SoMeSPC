package org.openxava.mestrado.actions;

import org.openxava.actions.*;

public class SaveMedicaoAction extends SaveAction {
	
    public void execute() throws Exception {
    	
    	String idMedicao = getView().getValueString("id");
    	String valorTemp = getView().getValueString("valorTemp");
    	
    	super.execute();
    	
    	/*Medicao medicao = XPersistence.getManager().find(Medicao.class, idMedicao);
    	
    	ValorMedido valorMedido;
    	if(medicao.getMedidaPlanoDeMedicao().getMedida().getEscala().isNumerico())
    	{
    		valorMedido = new ValorNumerico();
    		((ValorNumerico)valorMedido).setValor(Float.parseFloat(valorTemp));
    	}else
    	{
    		valorMedido = new ValorAlfanumerico();
    		((ValorAlfanumerico)valorMedido).setValor(valorTemp); 		
    	}
    	
    	medicao.setValorMedido(valorMedido);
    	    	
        super.execute(); */
    }

}
