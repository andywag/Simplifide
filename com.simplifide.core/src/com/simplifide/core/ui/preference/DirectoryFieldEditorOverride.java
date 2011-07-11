package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class DirectoryFieldEditorOverride extends DirectoryFieldEditor{
	
	private String name;
	public DirectoryFieldEditorOverride(String name, String labelText, Composite parent) {
		super(name,labelText,parent);
		this.name = name;
	}
	
	
	protected String changePressed() {
        String path = super.changePressed();
        //this.getPreferenceStore().setValue(this.name, path);
        return path;
    }
	
	 protected boolean doCheckState() {
	        return true;
	    }
	 
	
}
