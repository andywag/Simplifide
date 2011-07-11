/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.SourceEditor;

public abstract class CommentActions2 extends Action{
	
	public static String ACTION_COMMENT_ID = CoreActivator.PLUGIN_ID + ".action.comment";
	public static String ACTION_UNCOMMENT_ID = CoreActivator.PLUGIN_ID + ".action.uncomment";
	
	private SourceEditor editor;
	
	public  CommentActions2(String name, SourceEditor editor) {
		super(name);
		this.setEditor(editor);
	}
	
	protected abstract String handleLine(IDocument doc, int spos, String text);
	
	public void run() {
		
		ITextSelection tsel = (ITextSelection) this.getEditor().getSelectionProvider().getSelection();
		Region region = new Region(tsel.getOffset(),tsel.getLength());
		if (region.getLength() == 0) {
			region = new Region(this.getEditor().getCaretPosition(),1);
		}
		IDocument doc = this.getEditor().getDocumentProvider().getDocument(this.getEditor().getEditorInput());
		
		int offset = region.getOffset() + region.getLength();
		int sp, ep;
		try {
			int eline = doc.getLineOfOffset(offset);
			int sline = doc.getLineOfOffset(region.getOffset());
			sp = doc.getLineInformation(sline).getOffset();
			ep = doc.getLineInformation(eline).getOffset() +  doc.getLineInformation(eline).getLength();

		} catch (BadLocationException e1) {
			HardwareLog.logError(e1);
			return;
		}
		
		String outString = "";
		
		int line = 0;
		String lastDelim = "";
		while (offset > region.getOffset()) {
			try {

				IRegion lineReg = doc.getLineInformationOfOffset(offset);
				

				int linea = doc.getLineOfOffset(offset);
				
				String text = doc.get(lineReg.getOffset(), lineReg.getLength());
				outString = this.handleLine(doc, lineReg.getOffset(), text) + lastDelim +  outString;
				
				lastDelim = doc.getLineDelimiter(linea);

				offset = lineReg.getOffset()-1;
			} catch (BadLocationException e) {
				HardwareLog.logError(e);
				return;
			}
		}
		try {
			doc.replace(sp, ep-sp, outString);
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
	}
	
	
	protected void setEditor(SourceEditor editor) {
		this.editor = editor;
	}

	protected SourceEditor getEditor() {
		return editor;
	}

	public static class Comment extends CommentActions2 {
		public Comment(SourceEditor editor) {
			super("Comment",editor);
			this.setActionDefinitionId(ACTION_COMMENT_ID);
		
		}

		protected String handleLine(IDocument doc, int spos, String text) {
				
			int i;
			for (i=0;i<text.length();i++) {
				char uchar = text.charAt(i);
				if (uchar != ' ' && uchar != '\t') break;
			}
			if (text.length() < i +2) return text;
			
			//String utext = text.substring(i,i+2);
			//if (!utext.equals("--")) {
				return "-- " + text;
			//}
			//return text;
		}
	
		
	}
	
	public static class UnComment extends CommentActions2 {
	    public UnComment(SourceEditor editor) {
	    	super("UnComment",editor);
			this.setActionDefinitionId(ACTION_UNCOMMENT_ID);
	    }

	    protected String handleLine(IDocument doc, int spos, String text) {

	        int i;
	        for (i=0;i<text.length();i++) {
	            char uchar = text.charAt(i);
	            if (uchar != ' ' && uchar != '\t') break;
	        }
	        
	        String li = text.trim();
	        if (li.startsWith("--")) {
	        	String str = text.replaceFirst("--", "");
	        	if (str.length() > 0) return str.substring(1);
	        }
	        return text;
	       
	    }
	}


}
