package com.simplifide.core.ui.preference.editor;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class EmacsPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public EmacsPreferencePage() {
        super("Emacs", FieldEditorPreferencePage.INFORMATION);
        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
    }
	
	
	public void init(IWorkbench workbench) {}

	

	@Override
	protected void createFieldEditors() {
		StringFieldEditor spath = new StringFieldEditor(PreferenceConstants.EMACS_PREFIX,
        		"Emacs Command Prefix            ", 
        		getFieldEditorParent());
        this.addField(spath);
        
        StringFieldEditor bpath = new StringFieldEditor(PreferenceConstants.EMACS_VHDL_BEAUTY,
        		"Vhdl Beautify Command           ", 
        		getFieldEditorParent());
        this.addField(bpath);
        
        StringFieldEditor autopath = new StringFieldEditor(PreferenceConstants.EMACS_VERILOG_AUTO,
        		"Verilog Auto Command            ", 
        		getFieldEditorParent());
        this.addField(autopath);
		
        StringFieldEditor injectpath = new StringFieldEditor(PreferenceConstants.EMACS_VERILOG_INJECT_AUTO,
        		"Verilog Inject Auto Command ", 
        		getFieldEditorParent());
        this.addField(injectpath);
        
        StringFieldEditor deletepath = new StringFieldEditor(PreferenceConstants.EMACS_VERILOG_DELETE_AUTO,
        		"Verilog Delete Auto Command", 
        		getFieldEditorParent());
        this.addField(deletepath);
        
        StringFieldEditor indent = new StringFieldEditor(PreferenceConstants.EMACS_VERILOG_INDENT,
        		"Verilog Indent Command         ", 
        		getFieldEditorParent());
        this.addField(indent);
        
        StringFieldEditor custom1 = new StringFieldEditor(PreferenceConstants.EMACS_CUSTOM1,
        		"Custom Command         ", 
        		getFieldEditorParent());
        this.addField(indent);
        
        StringFieldEditor custom2 = new StringFieldEditor(PreferenceConstants.EMACS_CUSTOM2,
        		"Custom Command         ", 
        		getFieldEditorParent());
        this.addField(indent);
        
	}

}
