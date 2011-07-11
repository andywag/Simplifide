package com.simplifide.core.baseeditor.actions.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.vhdl.editor.VhdlScanner;

public abstract class CapFileActionDelegate extends FormatActionDelegate{

	public CapFileActionDelegate() {}
	public CapFileActionDelegate(GeneralEditor editor) {super(editor);}
	
	public abstract String convertString(String inval);
	
	private boolean isIdentifier(char c) {
		return Character.isJavaIdentifierPart(c);
		
	}
	
	@Override
	public void run(GeneralEditor editor) {
		
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

	public static class Capitalize extends CapFileActionDelegate {
		public Capitalize() {}
		public Capitalize(GeneralEditor editor) {
			super(editor);
		}

		public String convertString(String inval) {
			return inval.toUpperCase();
		}
	}
	
	public static class CapitalizeHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Capitalize(editor);
		}
		
	}	
	public static class UnCapitalize extends CapFileActionDelegate {
		public UnCapitalize() {}
		public UnCapitalize(GeneralEditor editor) {
			super(editor);
		}
		
		public String convertString(String inval) {
			return inval.toLowerCase();
		}
		
	}
	
	public static class UnCapitalizerHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new UnCapitalize(editor);
		}
		
	}
	
}
