/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference.editor;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class HoverPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public HoverPreferencePage() {
		super("Hover", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}
	@Override
	protected void createFieldEditors() {
		BooleanFieldEditor hovEn = new BooleanFieldEditor(PreferenceConstants.HOVER_ENABLED,
				"Enable Text Hovers",
				getFieldEditorParent());
		this.addField(hovEn);
		
		BooleanFieldEditor hovEn1 = new BooleanFieldEditor(PreferenceConstants.HOVER_HTML_ENABLED,
				"Enables Html For Parsing",
				getFieldEditorParent());
		this.addField(hovEn1);
		
		BooleanFieldEditor naturalEn = new BooleanFieldEditor(PreferenceConstants.HOVER_HTML_ENABLED,
				"Enables Natural Docs for Parsing (Disables Html)",
				getFieldEditorParent());
		this.addField(hovEn1);
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

}
