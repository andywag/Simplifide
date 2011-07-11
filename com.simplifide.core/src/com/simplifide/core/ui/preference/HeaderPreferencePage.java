/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class HeaderPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public HeaderPreferencePage() {
        super("Build", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
}

    
    
    @Override
    protected void createFieldEditors() {
    	LicenseFieldEditor stfield = new LicenseFieldEditor(PreferenceConstants.FILE_HEADER,
				"File Header",
				80,
				this.getFieldEditorParent());
        this.addField(stfield);
     
        
    }

    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

}
