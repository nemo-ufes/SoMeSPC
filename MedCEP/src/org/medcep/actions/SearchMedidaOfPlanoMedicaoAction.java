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

import org.openxava.actions.*;


public class SearchMedidaOfPlanoMedicaoAction extends ReferenceSearchAction { 
	
/*	private View collectionElementView;		
	private String viewObject;
	private boolean closeDialogDisallowed = false;
	private boolean dialogOpened = false; 

	private int row = -1;
	private List<Object> entities;
	private List<Object> selectedEntities;*/
	
	public void execute() throws Exception 
	{
		
		
		
/*		View collectionElementView = getCollectionElementView();
		Tab collectionTab = collectionElementView.getCollectionTab();
		
		int[] selected = row != -1 ? new int[] {row} : collectionTab.getSelected();
		
		
		entities = getCollectionElementView().getCollectionObjects();
		
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
			throw new Exception("Selecione um por vez.");
		}*/
		
		return;
		
		//super.execute();
		
		//getTab().setBaseCondition("${id} IN (SELECT id from ObjetivoEstrategico)"); 

		//this.clearActions();
	}
	
/*	public View getView() { 
		if (viewObject != null && !dialogOpened) 
			return super.getView();		
		return getCollectionElementView().getRoot();		
	}*/
	
/*	protected boolean mustRefreshCollection() { 
		return true;
	}
		
	protected void showDialog(View viewToShowInDialog) throws Exception {
		super.showDialog(viewToShowInDialog);		
		dialogOpened = true;
		collectionElementView = null; 
	}*/
		
	/** @since 4m5 */
/*	protected View getParentView() throws XavaException {
		return getCollectionElementView().getParent();
	}*/
	
/*	protected View getCollectionElementView() throws XavaException {
		if (collectionElementView == null) {
			if (viewObject == null || dialogOpened) collectionElementView = super.getView(); // In a dialog
			else {
				collectionElementView = (View) getContext().get(getRequest(), viewObject);
			}
			collectionElementView.refreshCollections(); 			
		}
		return collectionElementView;
	}*/
/*		
	protected boolean isEntityReferencesCollection() throws XavaException {
		return getCollectionElementView().getMetaModel() instanceof MetaEntity;		
	}
	
	public String getViewObject() {
		return viewObject;
	}

	public void setViewObject(String viewObject) {
		this.viewObject = viewObject;
	}

	@Override
	protected void closeDialog() {  
		if (isCloseDialogDisallowed()) {
			getCollectionElementView().reset();
		} else {
			super.closeDialog();
			dialogOpened = false;
			collectionElementView = null; 
		}
	}	
	
	public void setCloseDialogDisallowed(boolean closeDialogDisallowed) {
		this.closeDialogDisallowed = closeDialogDisallowed;
	}

	public boolean isCloseDialogDisallowed() {
		return closeDialogDisallowed;
	}*/

}