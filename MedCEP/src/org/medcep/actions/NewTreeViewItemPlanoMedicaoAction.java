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
package org.medcep.actions;

import java.util.*;

import org.openxava.actions.*;
import org.openxava.tab.*;
import org.openxava.view.*;

public class NewTreeViewItemPlanoMedicaoAction extends NewTreeViewItemAction { // 1

	public static Object TreeItemSelectObject = null;

	private List<Object> entities;
	private List<Object> selectedEntities;
		
	/*
	 * Diferente do comportamento padrão, ele coloca o objeto selecionado em uma variavel 
	 * estatica para uso posterior. (Não é a solução mais adequada, pois pode causar problema no
	 * uso simultaneo, mas foi uma forma encontrada até o momento para ter essa informação 
	 * posteriormente - uso no TreeView do PlanoDeMedicao)*/
	public void execute() throws Exception 
	{	
		
		View collectionElementView = getCollectionElementView();
		Tab collectionTab = collectionElementView.getCollectionTab();
		
		int[] selected = collectionTab.getSelected();
		
		entities = collectionElementView.getCollectionObjects();
		
		selectedEntities = new ArrayList<Object>();
		if (entities.size() > 0 && selected != null) 
		{
			for (int selectedRow : selected) 
			{
				selectedEntities.add(entities.get(selectedRow));
			}
		}
		
		if(selectedEntities.size() > 1)
		{
			throw new Exception("Selecione apenas um item.");
		}			
		else if(selectedEntities.size() == 1)
			TreeItemSelectObject = selectedEntities.get(0);
		else
			TreeItemSelectObject = null;
		
		
		super.execute();
		
	}

}