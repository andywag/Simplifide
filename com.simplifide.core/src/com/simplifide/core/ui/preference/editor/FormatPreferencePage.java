package com.simplifide.core.ui.preference.editor;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class FormatPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public FormatPreferencePage() {
        super("Format", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }
	
	
	public void init(IWorkbench workbench) {}

	

	@Override
	protected void createFieldEditors() {
		
        IntegerFieldEditor bpath = new IntegerFieldEditor(PreferenceConstants.FORMAT_INDENT_MODULE,
        		"Module/Instance Indent Width", 
        		getFieldEditorParent());
        this.addField(bpath);
        
        
        
	}

}
