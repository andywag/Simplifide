/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.base;

import java.util.ArrayList;

import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.base.sourcefile.antlr.stream.TemplateContents;
import com.simplifide.base.sourcefile.antlr.stream.TemplateHandler;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.verilog.parse.grammar.verilog.VerilogTokenTypes;

public class VerilogTemplateHandler extends TemplateHandler{

	public static String TEMPLATE_STRING_BEGIN = "/* AUTO GENERATED (DO NOT EDIT MANUALLY) */";
	public static String TEMPLATE_STRING_END   = "/* END AUTOGENERATION */";


	
	public VerilogTemplateHandler(PositionStream stream, ParseDescriptor desc) {
		super(stream, desc);
	}

	@Override
	protected int getGeneratedToken(TopASTToken tok) {
		String text = tok.getText().trim();
		if (text.equalsIgnoreCase(TEMPLATE_STRING_BEGIN)) return TEMPLATE_START;
		else if (text.equalsIgnoreCase(TEMPLATE_STRING_END)) return TEMPLATE_END;
		//else if (text.equalsIgnoreCase(AUTOARGS)) return TEMPLATE_AUTOARG_START;
		//else if (text.equalsIgnoreCase(AUTOINST)) return TEMPLATE_AUTOINST_START;
		else if (text.equalsIgnoreCase("(")) return TEMPLATE_OPEN_PAREN;
		else if (text.equalsIgnoreCase(")")) return TEMPLATE_CLOSE_PAREN;
		return TEMPLATE_NONE;
	}

	private String parseTemplate(String text) {
		String tstr = "";
		String[] ustr = text.split("\n");
		if (ustr.length > 2) {
			for (int i=1;i<ustr.length-1;i++) {
				tstr += ustr[i] + "\n";
			}
		}
		return tstr;
	}


	

	@Override
	protected boolean templateToken(TopASTToken tok, TemplateContents contents,
			boolean lastValid, ArrayList<TemplateContents> templateList) {
		if (tok.getType() == VerilogTokenTypes.ML_COMMENT) {
			String text = tok.getText();
			if (text.startsWith("/* python")) {
				contents.appendText(this.parseTemplate(text));
				contents.setPosition(tok.getNodePosition());
				contents.setType(TemplateContents.PYTHON);
				return true;
			}
			else if (text.startsWith("/* simplifide")) {
				contents.appendText(this.parseTemplate(text));
				contents.setPosition(tok.getNodePosition());
				contents.setType(TemplateContents.SCALA);
				return true;
			}
		}
		return false;
	}



}
