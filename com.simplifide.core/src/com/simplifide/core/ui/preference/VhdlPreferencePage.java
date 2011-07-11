/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class VhdlPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public VhdlPreferencePage() {
        super("Vhdl", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }
    @Override
    protected void createFieldEditors() {
    	BooleanFieldEditor fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.VHDL_CAP_KEYWORDS,
				"Automatically Capitalize Keywords",
				getFieldEditorParent());
		this.addField(fieldEditor);
        
    }

    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

}
