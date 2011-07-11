/*
 * VhdlDocParser.java
 *
 * Created on April 14, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.base;

import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.stream.DocParser;

/**
 *
 * @author Andy
 */
public final class VerilogDocParser extends DocParser{
    
	private static final  String OPENCOM = "//";
	private static final  String OPEN = "/**";
	private static final  String OPENMULTI = "/*";
	private static final String CLOSE = "*/";
	
    private static VerilogDocParser instance;
    
    public static final int STATE_NONE = 0;
    public static final int STATE_DESC = 1;
    public static final int STATE_AT = 2;
    
    
    
    /** Creates a new instance of VhdlDocParser */
    private VerilogDocParser() {}
    
    public static VerilogDocParser getInstance() {
        if (instance == null) instance = new VerilogDocParser();
        return instance;
    }

 
    public String trimEndClose(String instr) {
    	
    	String ostr = instr.trim();
    	
    	if (ostr.endsWith("*/")) {
    		ostr = ostr.substring(0,ostr.length()-2);
    	}
    	return ostr;
    }
    
    private String trimClose(String instr) {
    	if (instr.endsWith("*/")) {
    		instr = instr.substring(0,instr.length()-2);
    	}
    	return instr;
    }
    
    
    private void parseDescription(StringBuilder builder, HdlDoc doc) {
    	String ustr = builder.toString();
    	ustr = this.trimClose(ustr);
    	doc.addDescription(ustr); 
    }
    
    private void parseLine(HdlDoc doc, StringBuilder builder, ParseContext context) {
 
    	this.trimWhiteSpace(builder, '*'); // Remove WhiteSpace and * from Line
    	if (builder.length() == 0) {
    		doc.addDescription("");
    		return;
    	}
    	if (builder.charAt(0) == '/') return;
    	if (this.matchString(builder,"@")) this.parseAt(doc, builder,context);
    	else this.parseDescription(builder, doc);
    }
    
    private void parseRemainingLine(HdlDoc doc, StringBuilder builder, ParseContext context) {
    	if (this.matchString(builder,"@")) this.parseAt(doc, builder,context);
    	else this.parseDescription(builder, doc);
    }
    
    private void parseSingeLine(HdlDoc doc, StringBuilder builder, ParseContext context) {
    	//this.consumeToCharacter(builder, '/');
    	//this.consumeToCharacter(builder, '/');
    	this.parseRemainingLine(doc, builder, context);
    }
    
    private void parseMultiStart(HdlDoc doc, StringBuilder builder, ParseContext context) {
    	this.consumeToCharacter(builder, '/');
    	this.consumeToCharacter(builder, '*');
    	if (builder.length() > 0 && builder.charAt(0) == '*') builder.deleteCharAt(0);
    }
    
	@Override
	protected boolean parseDoc(HdlDoc doc, String instr, ParseContext context) {
		
		String[] ustr = instr.split("\n");
		StringBuilder builder = new StringBuilder(ustr[0]);
		
		if (!this.matchString(builder, OPEN) && !this.matchString(builder, OPENMULTI)) return false; // Look for the Open Doc
		this.parseLine(doc, builder, context);
		
		for (int i=1;i<ustr.length;i++) {
			String astr = this.trimEndClose(ustr[i]);
			builder = new StringBuilder(astr);
			this.parseLine(doc,builder,context);
		}
		
		
		return true;
	}
	
	protected boolean parseSingle(HdlDoc doc,String instr, ParseContext context) {
		StringBuilder builder = new StringBuilder(instr);
		if (!this.matchString(builder, OPENCOM)) return false; // Look for the Open Doc
		this.parseSingeLine(doc,builder,context);
		doc.setSingleLine(true);
		return true;
	}
	
	
    

    
    
    
}
