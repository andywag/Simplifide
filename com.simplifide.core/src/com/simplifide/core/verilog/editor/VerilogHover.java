/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.hover.SourceTextHover;
import com.simplifide.core.freemarker.TemplateGenerator;

public class VerilogHover extends SourceTextHover{

	public VerilogHover(SourceEditor editor) {
		super(editor);
	}

	@Override
	public int getHoverType() {
		return TemplateGenerator.TEMPLATE_VERILOG;
	}
	
	
	
}
