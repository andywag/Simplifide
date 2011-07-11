/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class ScriptingPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public ScriptingPreferencePage() {
        super("Scripting Preference", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }
    @Override
    protected void createFieldEditors() {
    	
    	BooleanFieldEditor fieldEditor = new BooleanFieldEditor(PreferenceConstants.SCRIPT_RELEASE,
                "Override Standard Script Location",
                getFieldEditorParent());
        this.addField(fieldEditor);
        //fieldEditor.setEnabled(false, getFieldEditorParent());
        
        DirectoryFieldEditor dir1 = new DirectoryFieldEditorOverride(PreferenceConstants.SCRIPT_RELEASE_PATH,
        		"Internal Script Path if Overridden",
        		getFieldEditorParent());
        this.addField(dir1);
        //dir1.setEnabled(false, getFieldEditorParent());
        
        StringFieldEditor spath = new StringFieldEditor(PreferenceConstants.SCRIPT_PATH_NAME,
        		"Location of Path Class", 
        		getFieldEditorParent());
        this.addField(spath);
        
        StringFieldEditor spath1 = new StringFieldEditor(PreferenceConstants.SCRIPT_STARTUP_NAME,
        		"Location of Statup Class", 
        		getFieldEditorParent());
        this.addField(spath1);
        /*
        DirectoryFieldEditor dir2 = new DirectoryFieldEditorOverride(PreferenceConstants.SCRIPT_EXTRA_PATH1,
        		"Extra Script Directory Location",
        		getFieldEditorParent());
        this.addField(dir2);
        
        DirectoryFieldEditor dir3 = new DirectoryFieldEditorOverride(PreferenceConstants.SCRIPT_EXTRA_PATH2,
        		"Extra Script Directory Location",
        		getFieldEditorParent());
        this.addField(dir3);
        */
    }

    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

}
