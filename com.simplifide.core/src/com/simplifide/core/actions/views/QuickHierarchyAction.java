package com.simplifide.core.actions.views;

import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.views.quick.QuickHierarchy;
import com.simplifide.core.ui.views.quick.QuickViewTop;

public class QuickHierarchyAction extends EditorActionDelegate{

	
	public QuickHierarchyAction() {}
	public QuickHierarchyAction(SourceEditor editor) {
		super(editor);
	}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		return true;
	}

	@Override
	public void run(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;
		QuickViewTop top = new QuickHierarchy(editor,editor.getEditorSite().getShell(), 0, 0);
		top.open();
		top.setInput(null);
	}
	
	public static class ObjectHandler extends GeneralHandler {

		@Override
		public EditorActionDelegate createDelegate(GeneralEditor editor) {
			return new QuickHierarchyAction((SourceEditor)editor);
		}
		
	}

	

}
