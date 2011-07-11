package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.FileFieldEditor;

public class VerilogFileTemplatePreferencePage extends FileTemplatePreferencePage{

	
	protected void createFieldEditors() {
		

		FileFieldEditor head = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VERILOG_HEADERDIR,
				"Verilog File Header Directory",
				this.getFieldEditorParent());
		
		FileFieldEditor template = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VERILOG_TEMPLATE,
				"Verilog File Template",
				this.getFieldEditorParent());
		
        this.addField(head);
        this.addField(template);

	}
}
