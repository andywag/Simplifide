package com.simplifide.core.baseeditor.actions.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;

public abstract class CapActionDelegate extends FormatActionDelegate{

	public CapActionDelegate() {}
	public CapActionDelegate(GeneralEditor editor) {super(editor);}
	
	
	public abstract String handleText(String instring);
	
	private boolean isWhiteSpace(char c) {
		if (c == ' ' | c == '\t' | c == '\n' | c == '\r') return true;
		return false;
	}

	@Override
	public void run(GeneralEditor editor) {
		ITextSelection tsel = (ITextSelection) this.getEditor().getSelectionProvider().getSelection();
		Region region = new Region(tsel.getOffset(),tsel.getLength());
		if (region.getLength() == 0) {
			region = new Region(this.getEditor().getCaretPosition(),1);
		}
		IDocument doc = this.getEditor().getDocumentProvider().getDocument(this.getEditor().getEditorInput());
	
	
		try {
			String text = doc.get(region.getOffset(), region.getLength());
			String utext = this.handleText(text);
			doc.replace(region.getOffset(),region.getLength(), utext);
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
		
		
		/*
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
		*/
	}

	public static class Capitalize extends CapActionDelegate {
		public Capitalize() {}
		public Capitalize(GeneralEditor editor) {super(editor);}
		public String handleText(String instring) {
			return instring.toUpperCase();
		}
	}
	
	public static class CapitalizeHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Capitalize(editor);
		}
		
	}
	
	public static class UnCapitalize extends CapActionDelegate {
		public UnCapitalize() {}
		public UnCapitalize(GeneralEditor editor) {super(editor);}
		public String handleText(String instring) {
			return instring.toLowerCase();
		}
	}
	
	public static class UnCapitalizerHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new UnCapitalize(editor);
		}
		
	}
	
}
