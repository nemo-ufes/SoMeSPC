package org.openxava.actions;

import org.openxava.web.editors.*;

/**
 * Logic of AttachedFiles.remove in default-controllers.xml. <p>
 * 
 * It remove the file of the file container. <p>
 * 
 * @author Jeromy Altuna
 */
public class RemoveFileFromFilesetAction extends ViewBaseAction {
	
	private String fileId;
	
	@Override
	public void execute() throws Exception {
		FilePersistorFactory.getInstance().remove(getFileId());
	}	

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}
