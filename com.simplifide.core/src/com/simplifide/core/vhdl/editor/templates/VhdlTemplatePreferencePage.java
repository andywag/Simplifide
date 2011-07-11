/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor.templates;


import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import com.simplifide.core.CoreActivator;

public class VhdlTemplatePreferencePage extends TemplatePreferencePage{

	public VhdlTemplatePreferencePage() {
		setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
		setTemplateStore(VhdlTemplateAccess.getDefault().getTemplateStore());
	    setContextTypeRegistry(VhdlTemplateAccess.getDefault().getContextTypeRegistry());
	}
	
	public boolean performOk() {
	  //VhdlTemplateAccess.getDefault().getTemplateStore().restoreDefaults();
  	  boolean ok = super.performOk();
  	  CoreActivator.getDefault().savePluginPreferences();
  	  return ok;
  }
	
}
