/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference.editor;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class EditorPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public EditorPreferencePage() {
		super("Editor", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}
	@Override
	protected void createFieldEditors() {

		
		BooleanFieldEditor indentEnable = new BooleanFieldEditor(
				PreferenceConstants.INDENT_ENABLE,
				"Enable Formatting",
				getFieldEditorParent());
		addField(indentEnable);
		
		BooleanFieldEditor markEnable = new BooleanFieldEditor(
				PreferenceConstants.EDITOR_MARK_OCCURENCE,
				"Enable Mark Occurences",
				getFieldEditorParent());
		addField(markEnable);
		
		BooleanFieldEditor parenEnable = new BooleanFieldEditor(
				PreferenceConstants.EDITOR_PAREN_MATCH,
				"Enable Paren Matching",
				getFieldEditorParent());
		addField(parenEnable);
		
		BooleanFieldEditor autoEnable = new BooleanFieldEditor(
				PreferenceConstants.EDITOR_ENABLE_AUTO_EDITS,
				"Enable Auto Edits",
				getFieldEditorParent());
		addField(autoEnable);
		
		ColorFieldEditor mark = new ColorFieldEditor(PreferenceConstants.EDITOR_MARK_COLOR,
				"Mark Color",
				getFieldEditorParent());
		addField(mark);
		
		ColorFieldEditor paren = new ColorFieldEditor(PreferenceConstants.EDITOR_PAREN_COLOR,
				"Paren Match Color",
				getFieldEditorParent());
		addField(paren);
		// Removed to use the default settings for the editor
		/*BooleanFieldEditor indentTab = new BooleanFieldEditor(
				PreferenceConstants.INDENT_TAB,
				"Use Tab for Indent",
				getFieldEditorParent());
		addField(indentTab);
		
		
		IntegerFieldEditor indentSpaces = new IntegerFieldEditor(
				PreferenceConstants.INDENT_LENGTH,
				"&Number of spaces to indent:",
		 		getFieldEditorParent());
			indentSpaces.setTextLimit(5);
			addField(indentSpaces);	
			
		*/
		
		
		
				
	}
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}
}
