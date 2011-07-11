/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.indent;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.format.SourceContentFormattingStrategy;

public class VerilogFormattingStrategy extends SourceContentFormattingStrategy{

	public static String BEGIN = "begin";
	
	public VerilogFormattingStrategy(SourceEditor editor) {
		super(editor);
	}

	
	
	@Override
	protected int getIndentLevel(String text) {
		
		
		
		return 0;
		
	}
	
	
}
