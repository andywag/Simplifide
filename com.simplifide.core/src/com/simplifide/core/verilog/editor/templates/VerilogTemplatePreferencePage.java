/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.templates;


import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import com.simplifide.core.CoreActivator;

public class VerilogTemplatePreferencePage extends TemplatePreferencePage{

	public VerilogTemplatePreferencePage() {
		setPreferenceStore(CoreActivator.getDefault().getPreferenceStore());
		setTemplateStore(VerilogTemplateAccess.getDefault().getTemplateStore());
	    setContextTypeRegistry(VerilogTemplateAccess.getDefault().getContextTypeRegistry());
	}
	
	public boolean performOk() {
	  //VhdlTemplateAccess.getDefault().getTemplateStore().restoreDefaults();
  	  boolean ok = super.performOk();
  	  CoreActivator.getDefault().savePluginPreferences();
  	  return ok;
  }
	
}
