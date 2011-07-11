/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.preference.editor;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.text.hyperlink.DefaultHyperlinkPresenter;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public class HyperlinkPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public HyperlinkPreferencePage() {
		super("Hyperlinks", FieldEditorPreferencePage.INFORMATION);
		this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		
			ColorFieldEditor color = new ColorFieldEditor(DefaultHyperlinkPresenter.HYPERLINK_COLOR, "Hyperlink Color", 
	   		this.getFieldEditorParent());
	   this.addField(color);
   
	   
		
	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

}
