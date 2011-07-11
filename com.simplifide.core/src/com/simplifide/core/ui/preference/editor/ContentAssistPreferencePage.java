/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference.editor;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class ContentAssistPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public ContentAssistPreferencePage() {
		super("Content Assist", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		BooleanFieldEditor autoIn = new BooleanFieldEditor(PreferenceConstants.COMPLETE_AUTO_INSERT,
				"Enable Auto Insert",
				getFieldEditorParent());
		addField(autoIn);
		
		BooleanFieldEditor autoEn = new BooleanFieldEditor(PreferenceConstants.COMPLETE_AUTO_ACTIVATION,
				"Enable Auto Activation",
				getFieldEditorParent());
		addField(autoEn);
		
		/*BooleanFieldEditor commaEn = new BooleanFieldEditor(PreferenceConstants.COMPLETE_AUTO_COMMA,
				"Enable Comma Content Assist",
				getFieldEditorParent());
		addField(commaEn);
		*/
		IntegerFieldEditor compTime = new IntegerFieldEditor(PreferenceConstants.COMPLETE_AUTO_TIME,
				"Time Before Popup in ms",
				getFieldEditorParent());
		addField(compTime);
		
		ColorFieldEditor fore = new ColorFieldEditor(PreferenceConstants.COMPLETE_FOREGROUND,
				"Foreground Color",
				getFieldEditorParent());
		addField(fore);
		
		ColorFieldEditor back = new ColorFieldEditor(PreferenceConstants.COMPLETE_BACKGROUND,
				"Background Color",
				getFieldEditorParent());
		addField(back);
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}
	
}
