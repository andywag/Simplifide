/*
 * VhdlDocParser.java
 *
 * Created on April 14, 2007, 11:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.base;

import com.simplifide.base.BaseLog;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.stream.DocParser;


    
/**
 *
 * @author Andy
 */
public final class VhdlDocParser extends DocParser{
    
    private static VhdlDocParser instance;
    
    private static final String COM = "--";
    private static final String OPEN = "--*";
    private static final String OPEND = "--!";
    private static final String AT ="@";
   
    
    /** Creates a new instance of VhdlDocParser */
    private VhdlDocParser() {
    	super();
    }
    
    public static VhdlDocParser getInstance() {
        if (instance == null) instance = new VhdlDocParser();
        return instance;
    }
    
    
   
    public boolean parseDoc(HdlDoc doc, String inStr, ParseContext context) {
    	try {
    		StringBuilder buffer = new StringBuilder(inStr);
            if (!this.matchString(buffer, OPEN) && !this.matchString(buffer, OPEND) && !this.matchString(buffer, COM)) return false; // Look for the Open Doc
            if (buffer.length() < 0) return false;
            this.trimWhiteSpace(buffer);  // Trim the initial white space
            
            if (this.matchString(buffer, AT)) {
                return this.parseAt(doc, buffer, context);
            }
            else {
                doc.addDescription(buffer.toString());
            }
            
            return true;
    	}
    	catch (Exception e) {
    		BaseLog.logError(e);
    		return false;
    	}
        
    }
    
    
    
}
