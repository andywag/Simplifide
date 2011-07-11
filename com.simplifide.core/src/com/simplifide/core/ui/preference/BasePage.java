/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class BasePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public BasePage() {
		super("SimplifIDE", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		
		FileFieldEditor xmlFieldEditor2 = new FileFieldEditor(
				PreferenceConstants.CONFIG_DIRECTORY,
				"License File in Configuration Directory",
				getFieldEditorParent());
		this.addField(xmlFieldEditor2);
		
	    BooleanFieldEditor fieldEditor = new BooleanFieldEditor(PreferenceConstants.VHDL_ENABLE,
	                "Enable Vhdl",
	                getFieldEditorParent());
	   this.addField(fieldEditor);
	   
	    BooleanFieldEditor fieldEditor1 = new BooleanFieldEditor(PreferenceConstants.VERILOG_ENABLE,
                "Enable Verilog",
                getFieldEditorParent());
	    this.addField(fieldEditor1);
   
	   
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

}
