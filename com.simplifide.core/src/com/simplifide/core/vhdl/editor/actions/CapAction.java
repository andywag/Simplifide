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

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.editors.SourceEditor;

public abstract class CapAction extends Action{
	

	public static String ACTION_CAPITALIZE_ID = CoreActivator.PLUGIN_ID + ".action.capitalize";
	public static String ACTION_UNCAPITALIZE_ID = CoreActivator.PLUGIN_ID + ".action.uncapitalize";

	private SourceEditor editor;
	
	public  CapAction(SourceEditor editor, String name) {
		super(name);
		this.setEditor(editor);
	}
	
	public abstract String handleText(String instring);
	
	private boolean isWhiteSpace(char c) {
		if (c == ' ' | c == '\t' | c == '\n' | c == '\r') return true;
		return false;
	}
	
	public void run() {
		
		ITextSelection tsel = (ITextSelection) this.getEditor().getSelectionProvider().getSelection();
		Region region = new Region(tsel.getOffset(),tsel.getLength());
		if (region.getLength() == 0) {
			region = new Region(this.getEditor().getCaretPosition(),1);
		}
		IDocument doc = this.getEditor().getDocumentProvider().getDocument(this.getEditor().getEditorInput());
		int cpos = tsel.getOffset();
		
		try {
			int start = -1;
			int stop = -1;
			while (cpos > 0) {
				char c = doc.getChar(cpos);
				if (this.isWhiteSpace(c)) {start = cpos + 1; cpos=cpos+1; break; }
				cpos--;
			}
			while (cpos < doc.getLength()) {
				char c = doc.getChar(cpos);
				if (this.isWhiteSpace(c)) {stop = cpos - 1; break;}
				cpos++;
			}
			String text = this.handleText(doc.get(start, stop-start+1));   
			doc.replace(start, stop-start+1, text);
			
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
		
		
		
	}

	private void setEditor(SourceEditor editor) {
		this.editor = editor;
	}

	private SourceEditor getEditor() {
		return editor;
	}
	
	public static class Capitalize extends CapAction {
		public Capitalize(SourceEditor editor) {
			super(editor,"Capitalize");
			this.setActionDefinitionId(ACTION_CAPITALIZE_ID);
		}

		
		public String handleText(String instring) {
			return instring.toUpperCase();
		}
	}

	public static class UnCapitalize extends CapAction {
		public UnCapitalize(SourceEditor editor) {
			super(editor,"UnCapitalize");
			this.setActionDefinitionId(ACTION_UNCAPITALIZE_ID);
		}
		
		public String handleText(String instring) {
			return instring.toLowerCase();
		}
	}
	

}
