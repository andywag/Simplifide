/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class BuildPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public BuildPreferencePage() {
        super("Build", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }

    
    
    @Override
    protected void createFieldEditors() {
        BooleanFieldEditor fieldEditor = new BooleanFieldEditor(PreferenceConstants.BUILD_ENABLED,
                "Enable Automatic Build of Project. This is required for All Project Operations",
                getFieldEditorParent());
        this.addField(fieldEditor);
        
        /*BooleanFieldEditor lib = new BooleanFieldEditor(PreferenceConstants.BUILD_LIBRARY_STORE,
                "Enable Storage of Library Contents (alpha)",
                getFieldEditorParent());
        this.addField(lib); */
        
        BooleanFieldEditor debug = new BooleanFieldEditor(PreferenceConstants.DEBUG_INFO,
                "Show Debug Information",
                getFieldEditorParent());
        this.addField(debug);
        
        BooleanFieldEditor debugparse = new BooleanFieldEditor(PreferenceConstants.PARSE_DEBUG_INFO,
                "Show Parse Debugging Information",
                getFieldEditorParent());
        this.addField(debugparse);
        
        /*
        BooleanFieldEditor update = new BooleanFieldEditor(PreferenceConstants.UPDATE_DISABLE,
                "Disable Automatic Updates",
                getFieldEditorParent());
        this.addField(update); */
        
        IntegerFieldEditor intedit = new IntegerFieldEditor(PreferenceConstants.MAXIMUM_FILE_LENGTH,
                "Maximum File Size Parsed in Bytes",
                getFieldEditorParent());
        this.addField(intedit);
        
    }

    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub
        
    }

}
