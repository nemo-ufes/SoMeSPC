package org.openxava.mestrado.actions;

import java.util.*;

import javax.inject.*;

import org.openxava.actions.*; 
import org.openxava.jpa.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.PlanejamentoDaMedicao.PlanoDeMedicao.*;
import org.openxava.mestrado.model.MedicaoDeSoftware.Medicao.*;
import org.openxava.model.meta.*;
import org.openxava.tab.*;
import org.openxava.util.*;
import org.openxava.view.*;


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