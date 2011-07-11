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

public  class LibraryStructPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	

	public LibraryStructPreferencePage() {
		super("Library Structure", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}


	@Override
	protected void createFieldEditors() {
		
		FileFieldEditor xmlFieldEditor = new FileFieldEditor(
				PreferenceConstants.LIBRARY_DIR_CONFIG,
				"LIBRARY Directory Structure Configuation File",
				getFieldEditorParent());
		this.addField(xmlFieldEditor);
		
		BooleanFieldEditor fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.LIBRARY_DIR_BUILD,
				"Create Build Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.LIBRARY_DIR_DOC,
				"Create Doc Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.LIBRARY_DIR_ROUTE,
				"Create Route Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.LIBRARY_DIR_SCRIPT,
				"Create Script Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.LIBRARY_DIR_SYNTHESIS,
				"Create Synthesis Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.LIBRARY_DIR_TEST,
				"Create Test Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		
		fieldEditor = new BooleanFieldEditor(
				PreferenceConstants.LIBRARY_DIR_SUB,
				"Create Sub Projects Directory",
				getFieldEditorParent());
		this.addField(fieldEditor);
		        
    }

	public void init(IWorkbench workbench) {
		
		
	}

}
