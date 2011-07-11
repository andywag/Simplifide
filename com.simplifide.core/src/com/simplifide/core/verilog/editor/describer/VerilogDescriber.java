/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.describer;

import java.net.URI;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.source.SourceDescriber;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class VerilogDescriber extends SourceDescriber{


	public DesignFile createDesignFile(URI file) {
		boolean ver = CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.VERILOG_ENABLE);
		if (ver) {
			try {
				return new SystemVerilogFile(file);
			}
			catch (Exception e) {
				HardwareLog.logError(e);
			}
			return new VerilogFile(file);
			
		}
		return null;
	}
	
}
