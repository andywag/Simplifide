/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class VerilogPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public VerilogPreferencePage() {
        super("Verilog", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }
    @Override
    protected void createFieldEditors() {
        
    }

    public void init(IWorkbench workbench) {        
    }

}
