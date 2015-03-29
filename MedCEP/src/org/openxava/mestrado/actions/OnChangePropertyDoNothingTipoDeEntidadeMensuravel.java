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

package org.openxava.mestrado.actions;

import org.openxava.actions.*;

public class OnChangePropertyDoNothingTipoDeEntidadeMensuravel extends OnChangePropertyBaseAction { // 1

	public void execute() throws Exception {
		

		
		return;
	}

	public void setNewValue(Object object) {
		return;
		// newValue = object;
	}

}

/*
  
  
 
  
  		if (getView().getValueString("tipoDeEntidadeMensuravel").isEmpty() == false) {
			String idMedicao = getView().getValueString("id");

			Medicao medicao = XPersistence.getManager().find(Medicao.class,
					idMedicao);

			if (medicao != null) {
				String newValue = medicao.getValorMedido().getValorMedido();
				// Map map = new HashMap<String, String>();
				// map.put(MapFacade.MODEL_NAME, "valorMedido");
				getView().setValue("valorMedido.valorMedido", newValue);
			}
/*			else if(getView().getValueString("medidaPlanoDeMedicao").isEmpty() == true)
			{
				getView().setValue("valorMedido.valorMedido", "");

				addMessage("Antes de preencher o valor medido é necessário selecionar o plano de medição e a medida.");
			}// /
		}
		
		String valor = getView().getValueString("tipoDeEntidadeMensuravel");
		
		valor = "";
  
  
  
 */