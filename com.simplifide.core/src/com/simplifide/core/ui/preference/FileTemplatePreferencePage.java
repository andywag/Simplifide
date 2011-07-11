package com.simplifide.core.ui.preference;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.simplifide.core.CoreActivator;

public abstract class FileTemplatePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	
	 public FileTemplatePreferencePage() {
	        super("File Templates", FieldEditorPreferencePage.INFORMATION);
	        this.setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
	    }
	@Override
	protected void createFieldEditors() {
		

	}

	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}

}
