/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference.dirstructure;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class ProjectStructurePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public ProjectStructurePreferencePage() {
        super("Project Structure", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }
    @Override
    protected void createFieldEditors() {
    	FileFieldEditor xmlFieldEditor = new FileFieldEditor(
				PreferenceConstants.WORKSPACE_DIR_CONFIG,
				"Workspace Directory Structure Configuation File",
				getFieldEditorParent());
		this.addField(xmlFieldEditor);
        
    }

    public void init(IWorkbench workbench) {

    }

}
