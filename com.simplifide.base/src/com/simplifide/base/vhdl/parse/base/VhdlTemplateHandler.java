/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.base;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.stream.PositionStream;
import com.simplifide.base.sourcefile.antlr.stream.TemplateContents;
import com.simplifide.base.sourcefile.antlr.stream.TemplateHandler;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;
import com.sun.xml.internal.bind.CycleRecoverable.Context;

public class VhdlTemplateHandler extends TemplateHandler{

	public static String TEMPLATE_STRING_BEGIN = "-- AUTO GENERATED (DO NOT EDIT MANUALLY)";
	public static String TEMPLATE_STRING_END   = "-- END AUTOGENERATION";
	
	
	
	public VhdlTemplateHandler(PositionStream stream, ParseDescriptor desc) {
		super(stream, desc);
	}
	
	protected int getStartDeleteOffset(TopASTToken tok) {
		int pos = tok.getEndPosition();
		return pos + 1;
	}
	
	@Override
	public int getGeneratedToken(TopASTToken tok) {
		String text = tok.getText().trim();
		if (text.equalsIgnoreCase(TEMPLATE_STRING_BEGIN)) return TEMPLATE_START;
		else if (text.equalsIgnoreCase(TEMPLATE_STRING_END)) return TEMPLATE_END;
		else if (text.equalsIgnoreCase("(")) return TEMPLATE_OPEN_PAREN;
		else if (text.equalsIgnoreCase(")")) return TEMPLATE_CLOSE_PAREN;
		return TEMPLATE_NONE;
	}
	
	
	
	
	 
	@Override
	protected boolean templateToken(TopASTToken tok, TemplateContents contents,
			boolean lastValid, ArrayList<TemplateContents> templateList) {
		if (tok.getType() == VhdlTokenTypes.NEWLINE) {
			return lastValid;
		}
		if (tok.getType() == VhdlTokenTypes.COMMENT) {
    		String text = tok.getText();
    		if (text.startsWith(TEMPLATE_STRING_BEGIN)) return false;
    		if (text.startsWith(TEMPLATE_STRING_END)) return false;
    		if (text.startsWith("-- python")) {
    			contents.setType(TemplateContents.PYTHON);
    			return true;
    		}
    		else if (text.startsWith("-- simplifide")) {
    			contents.setType(TemplateContents.SCALA);
    			return true;
    		}
    		else if (text.startsWith("-- end_simplifide")) {
    			contents.appendText(text.substring(3) + "\n");
    			//if (contents.getPosition() == null) {
    			//	contents.setPosition(tok.getNodePosition().copy());
    			//}
    			contents.getPosition().setEndPos(tok.getNodePosition().getEndPos() + 1);
    			return false;
    		}
    		if (lastValid) {
    			contents.appendText(text.substring(3) + "\n");
    			if (contents.getPosition() == null) {
    				contents.setPosition(tok.getNodePosition().copy());
    				//contents.getPosition().setStartLine(contents.getPosition().getStartLine()-2);
    			}
    			contents.getPosition().setEndPos(tok.getNodePosition().getEndPos() + 1);
    			return true;
    		}
    		
    	}
    	return false;
	}
	
	
	
}
