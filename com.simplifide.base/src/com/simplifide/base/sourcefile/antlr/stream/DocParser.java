/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public abstract class DocParser {
	
	 private final static String AT_PORT = "port";
	 private final static String AT_GENERIC = "generic";
	 private final static String AT_PARAM = "param";
	 private final static String AT_RETURN = "return";
	 private final static String AT_AUTHOR = "author";
	 private final static String AT_LATCH  = "latch";
	 private final static String AT_REGISTER  = "register";
	 private final static String AT_CLOCK  = "clock";
	 private final static String AT_DATE  = "date";
	 private final static String AT_MODULE  = "module";
	 private final static String AT_DETAIL = "detail";
	 private final static String AT_BRIEF = "brief";
	
	    
	 protected void consumeToCharacter(StringBuilder build, char ch) {
		 
		 for (int i=0;i<build.length();i++) {
			 char uchar = build.charAt(0);
			 build.deleteCharAt(0);
			 if (uchar == ch) break;
		 }
	 }
	 
	 protected void trimWhiteSpace(StringBuilder buf) {
		 	if (buf.length() <= 0 ||  buf == null) return;
	        while (buf.charAt(0) == ' ' || buf.charAt(0) == '\t') {
	            buf.deleteCharAt(0);
	            if (buf.length() <= 0) break;
	        }
	    }
	 
	 protected void trimWhiteSpace(StringBuilder buf, char uchar) {
		 if (buf.length() <= 0 || buf == null) return;
		 while (buf.charAt(0) == ' ' || buf.charAt(0) == '\t' || buf.charAt(0) == uchar) {
			 buf.deleteCharAt(0);
			 if (buf.length() <= 0) return;
	     }
	    }
	    
	    protected boolean matchString(StringBuilder buf, String instr) {
	        if (instr.length() > buf.length()) return false;
	        for (int i = 0;i<instr.length();i++) {
	            if (instr.charAt(i) != buf.charAt(i)) return false;
	        }
	        buf.delete(0, instr.length());
	        return true;
	    }
	    
	    protected String consumeUntil(StringBuilder buffer, char[] ops) {
	    	StringBuilder outStr = new StringBuilder(16);
	        if (buffer.length() == 0) return "";
	     
	        while (buffer.charAt(0) != ' ') {
	        	 char uchar = buffer.charAt(0);
	        	 buffer.deleteCharAt(0);
	        	 for (char end : ops) {
	        		 if (uchar == end) {
	        			 return outStr.toString();
	        		 }
	        		
	        	 }
	        	 outStr.append(uchar);
	           
	         }
	         return outStr.toString();
	    }
	    
	    protected String stringBeforeSpace(StringBuilder buffer) {
	    	char[] ops = new char[] {' ','\t'};
	        return consumeUntil(buffer,ops);
	    }
	    
	    protected String stringBeforeNewLine(StringBuilder buffer) {
	    	char[] ops = new char[] {'\n'};
	        return consumeUntil(buffer,ops);
	    }
	    
	    private String[] getSourceFromPort(String pname) {
	    	final String[] ustr = pname.split("<s>");
	    	final String[] astr = new String[2];
	    	astr[0] = ustr[0];
	    	if (ustr.length == 2) {
	    		astr[1] = ustr[1].replaceAll("</s>", "");
	    	}
	    	else {
	    		astr[1] = "";
	    	}
	    	return astr;
	    }
	    
	    protected boolean parseAt(HdlDoc doc, StringBuilder buffer, ParseContext context) {
	        
	        if (this.matchString(buffer, AT_PARAM)) {
	            this.trimWhiteSpace(buffer);
	            String paramName = this.stringBeforeSpace(buffer);
	            doc.addParam(paramName, buffer.toString());
	        }
	        else if (this.matchString(buffer, AT_GENERIC)) {
	            this.trimWhiteSpace(buffer);
	            String paramName = this.stringBeforeSpace(buffer);
	            doc.addGeneric(paramName, buffer.toString());
	        }
	        else if (this.matchString(buffer, AT_PORT)) {
	            this.trimWhiteSpace(buffer);
	            String paramName = this.stringBeforeSpace(buffer);
	            String[] astr = this.getSourceFromPort(buffer.toString());
	            doc.addPort(paramName, astr[0],astr[1]);
	        }
	        else if (this.matchString(buffer, AT_RETURN)) {
	            doc.setRet(buffer.toString());
	        }
	        else if (this.matchString(buffer, AT_AUTHOR)) {
	            doc.setAuthor(buffer.toString());
	        }
	        else if (this.matchString(buffer, AT_LATCH)) {
	            doc.setLatch(true);
	        }
	        else if (this.matchString(buffer, AT_REGISTER)) {
	            doc.setLatch(true);
	        }
	        else if (this.matchString(buffer, AT_DATE)) {
	        	
	        }
	        else if (this.matchString(buffer, AT_MODULE)) {
	        	
	        }
	        else if (this.matchString(buffer, AT_CLOCK)) {
	        	this.trimWhiteSpace(buffer);
	        	String paramName = this.stringBeforeSpace(buffer);
	            String[] astr = this.getSourceFromPort(buffer.toString());
	            
	            CoreProjectSuite suite = context.getRefHandler().getGlobalReference().getObject();
	            suite.getSuiteProps().addClock(paramName, astr[0]);
	        }
	        return false;
	    }
	    
	    
	   
	    protected abstract boolean parseDoc(HdlDoc doc, String instr, ParseContext context);
	   
	   
}
