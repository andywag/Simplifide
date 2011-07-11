/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.hover.SourceTextHover;
import com.simplifide.core.freemarker.TemplateGenerator;

public class VhdlHover extends SourceTextHover{

	public VhdlHover(SourceEditor editor) {
		super(editor);
	}
	
	@Override
	public int getHoverType() {
		return TemplateGenerator.TEMPLATE_VHDL;
	}
	
}
