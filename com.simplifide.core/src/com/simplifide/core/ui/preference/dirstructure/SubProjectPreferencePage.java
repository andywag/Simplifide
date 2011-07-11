/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference.dirstructure;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public  class SubProjectPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	

	public SubProjectPreferencePage() {
		super("SubProject Structure", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}


	@Override
	protected void createFieldEditors() {
		
		FileFieldEditor xmlFieldEditor = new FileFieldEditor(
				PreferenceConstants.SUBPROJECT_DIR_CONFIG,
				"SubProject Directory Structure Configuation File",
				getFieldEditorParent());
		this.addField(xmlFieldEditor);
		
		BooleanFieldEditor fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.SUBPROJECT_DIR_BUILD,
				"Create Build Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.SUBPROJECT_DIR_DOC,
				"Create Doc Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.SUBPROJECT_DIR_ROUTE,
				"Create Route Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.SUBPROJECT_DIR_SCRIPT,
				"Create Script Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.SUBPROJECT_DIR_SYNTHESIS,
				"Create Synthesis Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.SUBPROJECT_DIR_TEST,
				"Create Test Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		

    }

	public void init(IWorkbench workbench) {
		
		
	}

}
