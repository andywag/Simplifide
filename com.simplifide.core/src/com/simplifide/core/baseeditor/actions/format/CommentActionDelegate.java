package com.simplifide.core.baseeditor.actions.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;

public abstract class CommentActionDelegate extends FormatActionDelegate{

	public CommentActionDelegate() {}
	public CommentActionDelegate(GeneralEditor editor) {
		super(editor);
	}
	
	protected abstract String handleLine(IDocument doc, int spos, String text);
	
	@Override
	public void run(GeneralEditor editor) {
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

	public static class Comment extends CommentActionDelegate {
		public Comment() {}
		public Comment(GeneralEditor editor) {
			super(editor);
		}

		protected String handleLine(IDocument doc, int spos, String text) {
			
			int i;
			for (i=0;i<text.length();i++) {
				char uchar = text.charAt(i);
				if (uchar != ' ' && uchar != '\t') break;
			}
			if (text.trim().length() == 0) return text;
			return this.getCommentCharacter() + " " + text;
		
		}
		
		protected String getCommentCharacter() {
			return "--";
		}
	}
	
	public static class CommentHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new Comment(editor);
		}
		
	}	
	public static class UnComment extends CommentActionDelegate {
		public UnComment() {}
		public UnComment(GeneralEditor editor) {
			super(editor);
		}
		
		 protected String handleLine(IDocument doc, int spos, String text) {
			 String com = this.getCommentCharacter();
		        int i;
		        for (i=0;i<text.length();i++) {
		            char uchar = text.charAt(i);
		            if (uchar != ' ' && uchar != '\t') break;
		        }
		        
		        String li = text.trim();
		        if (li.startsWith(com)) {
		        	String str = text.replaceFirst(com, "");
		        	if (str.length() > 0) return str.substring(1);
		        }
		        return text;
		       
		    }
		protected String getCommentCharacter() {
			return "--";
		}
		
	}
	
	public static class UnCommentHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new UnComment(editor);
		}
		
	}
	
}
