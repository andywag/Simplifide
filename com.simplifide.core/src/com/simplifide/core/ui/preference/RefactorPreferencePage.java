/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class RefactorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public RefactorPreferencePage() {
        super("Refactor", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }

    
    
    @Override
    protected void createFieldEditors() {
        StringFieldEditor spath = new StringFieldEditor(PreferenceConstants.REFACTOR_REPLACE_EXPR,
        		"Port Connect Match Expression", 
        		getFieldEditorParent());
        this.addField(spath);
        
        StringFieldEditor spath2 = new StringFieldEditor(PreferenceConstants.REFACTOR_REPLACE_DEST,
        		"Port Connect Replace Expression", 
        		getFieldEditorParent());
        this.addField(spath2);
        
    }

    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

}
