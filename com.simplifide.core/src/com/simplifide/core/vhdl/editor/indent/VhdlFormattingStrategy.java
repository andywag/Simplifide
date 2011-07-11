/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor.indent;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.format.SourceContentFormattingStrategy;

public class VhdlFormattingStrategy extends SourceContentFormattingStrategy{

	
	public VhdlFormattingStrategy(SourceEditor editor) {
		super(editor);
	}
	
	@Override
	protected int getIndentLevel(String text) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
