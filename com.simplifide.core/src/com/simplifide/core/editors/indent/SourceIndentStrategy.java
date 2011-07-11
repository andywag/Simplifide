/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.indent;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;

import com.simplifide.core.HardwareLog;

public abstract class SourceIndentStrategy implements IAutoEditStrategy {

   

    public static final int IND_NONE = 0;  
    public static final int IND_DEDENT = 1;
    public static final int IND_INDENT = 2;
    public static final int IND_BOTH = 3;
    
    // This is really kind of a pain to handle
    
    
    public SourceIndentStrategy() {
    }

    public abstract String[] getIndentStrings();
    public abstract String[] getDedentStrings();
    public abstract String[] getBothStrings();
    
    public static int getIndentLength() {
    	return EditorsUI.getPreferenceStore().getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH);
    }

   
    protected int findEndOfWhiteSpace(IDocument document, int offset, int end)
            throws BadLocationException {
        while (offset < end) {
            char c = document.getChar(offset);
            if (c != ' ' && c != '\t') {
                return offset;
            }
            offset++;
        }
        return end;
    }

    

    private int checkStartforDedent(String value) {
        if (TextUtilities.startsWith(this.getBothStrings(), value.trim()) != -1) return IND_BOTH;
        else if (TextUtilities.startsWith(this.getDedentStrings(), value.trim()) != -1) return IND_DEDENT;
        else return IND_NONE;
    }
    
    private int checkEndforIndent(String ustring) {
        if (TextUtilities.endsWith(this.getIndentStrings(), ustring) != -1) return IND_INDENT;
        else return IND_NONE;
    }
    
    private int checkPreviousLine(int start, int end, IDocument doc) {
        try {
            String ustring = doc.get(start, end - start);
            int type = this.checkStartforDedent(ustring);
            if (type != 0) return type;
            return this.checkEndforIndent(ustring);
          
        } catch (BadLocationException e) {
            HardwareLog.logError(e);
        }
        return IND_NONE;
    }

    
    public int numberOfSpaces(String s) {
        int len = 0;
        for (int i=0;i<s.length();i++) {
            if (s.charAt(i) == ' ') len++;
            else if (s.charAt(i) == '\t') len+= getIndentLength();
        }
        return len;
    }
    
    public String getIndentString() {
        int line = getIndentLength();
        String str = "";
        for (int i=0;i<line;i++) {
            str += " ";
        }
        return str;
    }
    
    private boolean afterLastLine(IDocument d, int offset, String ws) throws BadLocationException {
        int endLine = offset;
        IRegion info = d.getLineInformationOfOffset(endLine);
        int start = info.getOffset();
       
        // find white spaces
        int endWhiteSpace = findEndOfWhiteSpace(d, start, offset);
       
        String ws2 = d.get(start,endWhiteSpace-start);
        if (this.numberOfSpaces(ws) >= this.numberOfSpaces(ws2)) {
            return true;
        }
   
        return false;
    }
    
    private void autoIndentAfterNewLine(IDocument d, DocumentCommand c) {

        if (c.offset == -1 || d.getLength() == 0)
            return;
        try {
            // find start of line
            int endLine = (c.offset == d.getLength() ? c.offset - 1 : c.offset);
            IRegion info = d.getLineInformationOfOffset(endLine);
            int start = info.getOffset();
            int mode = this.checkPreviousLine(start, endLine, d);
            
          
            // find white spaces
            int endWhiteSpace = findEndOfWhiteSpace(d, start, c.offset);
           
            String ws = d.get(start,endWhiteSpace-start);
            String text = d.get(endWhiteSpace,endLine-endWhiteSpace);
            String delim = c.text;
            String newText = "";
            
            if (mode == IND_DEDENT || mode == IND_BOTH) {
                if (this.afterLastLine(d,start-1,ws)) {
                    if (ws.endsWith("\t")) {
                        ws = ws.substring(0,ws.length()-1);
                    }
                    else if (ws.endsWith(this.getIndentString())) {
                        ws = ws.substring(0,ws.length() - getIndentLength());
                    }
                    c.offset = start;
                    c.length = endLine - start;
                    newText = ws + text;
                }
            }
            
            if (mode == IND_NONE || mode == IND_DEDENT) {
                newText += delim + ws;
            }
            else if (mode == IND_INDENT || mode == IND_BOTH) {
                newText += delim + ws + this.getIndentString();
            }
            c.text = newText;
            //else if (mode == IND_DEDENT) this.dedent(start, endLine, 0,endWhiteSpace,c, mode);
          

        } catch (BadLocationException excp) {
            // stop work
        }
    }

    /** Command which handles the nodes */
    public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
        if (c.length == 0 && c.text != null
                && TextUtilities.endsWith(d.getLegalLineDelimiters(), c.text) != -1)
            autoIndentAfterNewLine(d, c);
        
    }

}
