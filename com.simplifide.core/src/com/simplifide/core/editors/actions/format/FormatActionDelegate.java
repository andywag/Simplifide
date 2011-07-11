package com.simplifide.core.editors.actions.format;


import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;

import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;

public class FormatActionDelegate extends EditorActionDelegate{

	public FormatActionDelegate() {}
	public FormatActionDelegate(SourceEditor editor) {super(editor);}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		return true;
	}

	@Override
	public void run(GeneralEditor editor) {
		SourceViewer viewer = (SourceViewer) editor.getSourceViewerReal();
		viewer.doOperation(ISourceViewer.FORMAT);
	}

	public static class FormatHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new FormatActionDelegate((SourceEditor)editor);
		}
		
	}
	
}
