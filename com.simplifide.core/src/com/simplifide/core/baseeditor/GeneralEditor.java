package com.simplifide.core.baseeditor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.outline.GeneralContentPane;
import com.simplifide.core.baseeditor.source.GeneralFile;

public abstract class GeneralEditor extends TextEditor{

	
	private GeneralContentPane contentPane;
	
	public GeneralEditor() {}
	
	protected abstract GeneralContentPane createContentPane();
	
	public void dispose() {
		this.contentPane = null;
		super.dispose();
		
	}
	
	public IDocument getDocument() {
		return this.getDocumentProvider().getDocument(this.getEditorInput());
	}
	
	public ColorManager getColorManager() {
		return ColorManager.getInstance();
	}
	
	public int getCaretPosition() {
		int caretOffset = this.getSourceViewer().getTextWidget().getCaretOffset();
		return caretOffset;
	}
	
	/** Called when there is a save on the design file which updates the 
	 * content page
	 */
	public void updateContentPane() {
		if (this.contentPane != null)
			this.contentPane.changeInput(this.getEditorSite().getShell().getDisplay());
	}
	
	
	
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (contentPane == null) this.contentPane = this.createContentPane();
			return this.contentPane;
		}
		return super.getAdapter(required);

	}
	
	public void goToPosition(int pos) {
		
		int newpos = pos;
		
		this.getSourceViewer().getTextWidget().setCaretOffset(newpos);
		this.getSourceViewer().getTextWidget().setSelection(newpos);
	}
	
	public void goToPosition(int pos, int length) {
		
		int newpos = pos;
		
		this.getSourceViewer().getTextWidget().setCaretOffset(newpos);
		this.getSourceViewer().getTextWidget().setSelection(newpos,newpos + length);
	}
	
	public ISourceViewer getSourceViewerReal() {
		return this.getSourceViewer();
	}

	public GeneralFile getGeneralFile() {
		return null;
	}
	
	public void parseFinished() {}
	
}
