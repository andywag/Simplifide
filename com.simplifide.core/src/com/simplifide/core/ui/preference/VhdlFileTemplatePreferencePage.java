package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.FileFieldEditor;

public class VhdlFileTemplatePreferencePage extends FileTemplatePreferencePage{

	
	protected void createFieldEditors() {
		
		
		FileFieldEditor head = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VHDL_HEADERDIR,
				"Vhdl File Template",
				this.getFieldEditorParent());
		
		FileFieldEditor pac_template = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VHDL_PACKAGE_TEMPLATE,
				"Vhdl Package Template",
				this.getFieldEditorParent());
		
		FileFieldEditor ent_template = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VHDL_ENTITY_TEMPLATE,
				"Vhdl Entity Template",
				this.getFieldEditorParent());
		
		FileFieldEditor arc_template = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VHDL_ARCHITECTURE_TEMPLATE,
				"Vhdl Architecture Template",
				this.getFieldEditorParent());
		
		FileFieldEditor entarc_template = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VHDL_ENTITYARCHITECTURE_TEMPLATE,
				"Vhdl Combined Entity and Architecture Template",
				this.getFieldEditorParent());
		
		FileFieldEditor testbench_template = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VHDL_TESTBENCH,
				"Vhdl Testbench",
				this.getFieldEditorParent());
		
		FileFieldEditor tester_template = new FileFieldEditor(PreferenceConstants.FILEWIZARD_VHDL_TESTER,
				"Vhdl Stimulus File",
				this.getFieldEditorParent());
		
        this.addField(head);
        this.addField(ent_template);
        this.addField(arc_template);
        this.addField(entarc_template);
        this.addField(pac_template);
        this.addField(testbench_template);
        this.addField(tester_template);


	}
}
