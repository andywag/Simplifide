/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor.indent;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.indent.SourceIndentStrategy;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.vhdl.editor.VhdlScanner;


public class VhdlIndentStrategy extends SourceIndentStrategy {

    private static String[] IND_INDENT_STRING = { "then", "begin", "is" };

    private static String[] IND_DEDENT_STRING = { "end" };

    private static String[] IND_BOTH_STRING = { "else" };

    
    
    // This is really kind of a pain to handle
    
    
    public VhdlIndentStrategy() {
    }

    @Override
    public String[] getBothStrings() {
       return IND_BOTH_STRING;
    }

    @Override
    public String[] getDedentStrings() {
        return IND_DEDENT_STRING;
    }

    @Override
    public String[] getIndentStrings() {
        return IND_INDENT_STRING;
    }

    private void handleKeyword(IDocument d, String key, int sp, int ep) {
    	String k = key.toUpperCase();
    	if (!k.equals(key)) {
    		try {
				d.replace(sp, ep-sp+1, k);
			} catch (BadLocationException e) {
				HardwareLog.logError(e);
			}
    	}
    	
    }
    
    private boolean isCommentLine(IDocument d, int offset) throws BadLocationException{
    	IRegion reg = d.getLineInformationOfOffset(offset);
    	String line = d.get(reg.getOffset(), reg.getLength());
    	String trLine = line.trim();
    	if (trLine.length() > 2) {
    		String com = trLine.substring(0,2);
        	if (com.equalsIgnoreCase("--")) return true;
        	return false;
    	}
    	return false;
    
    }
    
    /** Needs Binary Search over keywords */
    private void checkForKeywords(IDocument d, DocumentCommand c) {
    	int off = c.offset - 1;
    	
    	char u;
		try {
			if (this.isCommentLine(d, c.offset)) return;
			int ep = off;
			u = d.getChar(off);
			String key = "";
			while (Character.isJavaIdentifierPart(u)) {
	    		key = u + key;
				off = off - 1;
				u = d.getChar(off);
	    	}
			int sp = off+1;
		for (String ukey : VhdlScanner.keywords) {
			if (ukey.equalsIgnoreCase(key)) {
				this.handleKeyword(d,key,sp,ep);
			}
		}
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
    	
    }
    
    /** Command which handles the nodes */
    public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
    	super.customizeDocumentCommand(d, c);
    	if (CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.VHDL_CAP_KEYWORDS)) {    		
    		if (c.length == 0 && c.text.length() > 0 && !Character.isJavaIdentifierPart(c.text.charAt(0))) {
    			this.checkForKeywords(d,c);
        	}
    	}
    	
        
    }    

    

}
