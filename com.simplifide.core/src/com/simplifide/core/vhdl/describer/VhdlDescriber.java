/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.describer;

import java.net.URI;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.source.SourceDescriber;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class VhdlDescriber extends SourceDescriber{

	
	public DesignFile createDesignFile(URI file) {
		boolean vhdl = CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.VHDL_ENABLE);
		if (vhdl) return new VhdlFile(file);
		return null;
	}
	
}
