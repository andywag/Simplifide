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

public abstract class ErrorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	
	
	
	public ErrorPreferencePage() {
		super("Errors", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}

        protected abstract String getConstantName(String constantName);
        
        private void addField(String constantName, String description) {
            String fieldName = this.getConstantName(constantName);
            BooleanFieldEditor fieldEditor = new BooleanFieldEditor(fieldName,description,getFieldEditorParent());
            this.addField(fieldEditor);
        }
        
	@Override
	protected void createFieldEditors() {
            //this.addField(PreferenceConstants.ERROR_SYNTAX,"Enable Syntax Error Highlighting");
            this.addField(PreferenceConstants.ERROR_NOT_DEFINED,"Enable Not Defined Error (alpha)");
            this.addField(PreferenceConstants.ERROR_TYPE_MISMATCH,"Enable Type Checking Errors (alpha)");
            this.addField(PreferenceConstants.WARNING_LATCH,"Enable Latch Warning (alpha)");
            this.addField(PreferenceConstants.WARNING_NOT_ASSIGNED,"Variable Not Assigned Warning (alpha)");
            this.addField(PreferenceConstants.WARNING_NOT_USED,"Variable Not Used Warning (alpha)");
            
        }

	public void init(IWorkbench workbench) {
		
		
	}

}
