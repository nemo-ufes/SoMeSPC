package org.openxava.mestrado.actions;

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