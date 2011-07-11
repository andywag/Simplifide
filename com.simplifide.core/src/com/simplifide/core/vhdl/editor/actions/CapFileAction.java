/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.vhdl.editor.VhdlScanner;

public abstract class CapFileAction extends Action{
	
	private SourceEditor editor;
	
	public  CapFileAction(SourceEditor editor, String name) {
		super(name);
		this.setEditor(editor);
	}
		
	public abstract String convertString(String inval);
	
	private boolean isIdentifier(char c) {
		return Character.isJavaIdentifierPart(c);
		
	}
	
	
	
	public void run() {
		
		ITextSelection tsel = (ITextSelection) this.getEditor().getSelectionProvider().getSelection();
		Region region = new Region(tsel.getOffset(),tsel.getLength());
		if (region.getLength() == 0) {
			region = new Region(this.getEditor().getCaretPosition(),1);
		}
		IDocument doc = this.getEditor().getDocumentProvider().getDocument(this.getEditor().getEditorInput());
		
		
		
		String text = doc.get();
		StringBuilder out = new StringBuilder();
		StringBuilder current = new StringBuilder();
		boolean comment = false;
		
		for (int i=0;i<text.length();i++) {
			char a = text.charAt(i);
			if (a == '-' && i < text.length() && text.charAt(i+1) == '-') {
				comment = true;
			}
			
			if (this.isIdentifier(a)) {
				current.append(a);
			}
			else {
				if (!comment && VhdlScanner.isKeyWord(current.toString())) {
					String newS = this.convertString(current.toString());
					out.append(newS);
				}
				else {
					out.append(current);
				}
				
				out.append(a);
				current = new StringBuilder();
			}
			if (a == '\n') {
				comment = false;
			}
		}
		
		try {
			String ostr = out.toString();
			doc.replace(0, doc.getLength(), ostr);
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		} 
	}

	public static class Capitalize extends CapFileAction {
		public Capitalize(SourceEditor editor) {
			super(editor,"Capitalize File Keywords");
		}

		public String convertString(String inval) {
			return inval.toUpperCase();
		}
	}
	
	public static class UnCapitalize extends CapFileAction {
		public UnCapitalize(SourceEditor editor) {
			super(editor,"UnCapitalize File Keywords");
		}
		
		public String convertString(String inval) {
			return inval.toLowerCase();
		}
		
	}
	
	private void setEditor(SourceEditor editor) {
		this.editor = editor;
	}

	private SourceEditor getEditor() {
		return editor;
	}
	
	
	

}
