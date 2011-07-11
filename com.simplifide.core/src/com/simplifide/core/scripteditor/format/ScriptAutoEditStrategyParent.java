/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.scripteditor.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

public abstract class ScriptAutoEditStrategyParent implements IAutoEditStrategy {

   

    public static final int IND_NONE = 0;  
  
    
    // This is really kind of a pain to handle
    
    
    public ScriptAutoEditStrategyParent() {
    }

  

   
    protected int findEndOfWhiteSpace(IDocument document, int offset, int end)
            throws BadLocationException {
        while (offset < end) {
            char c = document.getChar(offset);
            if (c != ' ' && c != '\t' && c != '-') {
                return offset;
            }
            offset++;
        }
        return end;
    }
    
    protected void autoIndentAfterNewLine(IDocument d, DocumentCommand c) {

        if (c.offset == -1 || d.getLength() == 0)
            return;
        try {
            // find start of line
            int endLine = (c.offset == d.getLength() ? c.offset - 1 : c.offset);
            IRegion info = d.getLineInformationOfOffset(endLine);
            int start = info.getOffset();
            
          
            // find white spaces
            int endWhiteSpace = findEndOfWhiteSpace(d, start, c.offset);
           
            String ws = d.get(start,endWhiteSpace-start);
            String delim = c.text;
            String newText = "";
            
            
            
            newText += delim + ws;
            c.text = newText;
          

        } catch (BadLocationException excp) {
            // stop work
        }
    }
    
    protected void autoTabAfterNewLine(IDocument d, DocumentCommand c) {
    	if (c.offset == -1 || d.getLength() == 0)
            return;
        try {
            // find start of line
            IRegion currentInfo = d.getLineInformationOfOffset(c.offset);
            IRegion lastInfo    = d.getLineInformationOfOffset(currentInfo.getOffset() - 1);
            
            String current = d.get(currentInfo.getOffset(),currentInfo.getLength());
            String last = d.get(lastInfo.getOffset(),lastInfo.getLength());
            
            if (current.contains("\t") || last.contains("\t")) return;
            
            int currentDiff = c.offset - currentInfo.getOffset();
            if (currentDiff > lastInfo.getLength()) return; // End of Line Condition
            int lastPosition = lastInfo.getOffset() + currentDiff;
            
            int endWhiteSpace = findEndOfWhiteSpace(d, lastPosition, lastInfo.getOffset() + lastInfo.getLength());
            if (endWhiteSpace == lastInfo.getOffset() + lastInfo.getLength()) return;
            if (endWhiteSpace == lastPosition) return;
            String ws = d.get(lastPosition,endWhiteSpace-lastPosition);
            c.text = ws;
          

        } catch (BadLocationException excp) {
            // stop work
        }
    }

   
}
