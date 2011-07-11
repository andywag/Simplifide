/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.templates;

import java.io.IOException;

import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionTemplateStore;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;

public final class VerilogTemplateAccess {

	private static final String CUSTOM_TEMPLATES_KEY= "com.simplifide.core.verilog.templates";
	private static VerilogTemplateAccess instance;
	
	private TemplateStore fStore;
	private ContributionContextTypeRegistry fRegistry;
	
	private VerilogTemplateAccess() {}
	
	public static VerilogTemplateAccess getDefault() {
		if (instance == null) {
			instance = new VerilogTemplateAccess();
		}
		
		return instance;
	}
	
	public TemplateStore getTemplateStore() {
		if (fStore == null) {
			fStore= new ContributionTemplateStore(getContextTypeRegistry(),CoreActivator.getDefault().getPreferenceStore(), CUSTOM_TEMPLATES_KEY);
			try {
				fStore.load();
			} catch (IOException e) {
				HardwareLog.logError(e);
			}
		}
		return fStore;
	}
	
	public ContextTypeRegistry getContextTypeRegistry() {
		if (fRegistry == null) {
			fRegistry= new ContributionContextTypeRegistry();
			fRegistry.addContextType(VerilogTemplateContextType.VERILOG_ID);
		}
		return fRegistry;
	}
}
