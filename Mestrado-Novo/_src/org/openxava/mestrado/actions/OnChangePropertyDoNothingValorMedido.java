package org.openxava.mestrado.actions;

import java.util.*;

import org.openxava.jpa.*;
import org.openxava.actions.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.model.*;

public class OnChangePropertyDoNothingValorMedido extends OnChangePropertyBaseAction { // 1

	public void execute() throws Exception {
		if (getView().getValueString("valorMedido").isEmpty() == false) {
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
			}*/
		}
		return;
	}

	public void setNewValue(Object object) {
		return;
		// newValue = object;
	}

}