package com.simplifide.core.actions.views;

import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.views.quick.QuickOutlineView;
import com.simplifide.core.ui.views.quick.QuickViewTop;

public class QuickOutlineAction extends EditorActionDelegate{

	
	public QuickOutlineAction() {}
	public QuickOutlineAction(SourceEditor editor) {
		super(editor);
	}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		return true;
	}

	@Override
	public void run(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;
		QuickViewTop top = new QuickOutlineView(editor,editor.getEditorSite().getShell(), 0, 0);
		top.setEditor(editor);
		top.open();
		top.setInput(null);
	}
	
	public static class ObjectHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new QuickOutlineAction((SourceEditor)editor);
		}
		
	}

	

}
