package org.openxava.mestrado.actions;

import org.openxava.actions.*;

public class OnChangeSectionCEPAction extends ChangeSectionAction implements IChainAction { 

	public void execute() throws Exception {
		super.execute();
	}

	public String getNextAction() throws Exception
	{
		return "CRUD.save";
	}
	
}