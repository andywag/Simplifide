package com.simplifide.core.actions.views;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.views.quick.QuickClassHierarchy;
import com.simplifide.core.ui.views.quick.QuickViewTop;

public class QuickClassHierarchyAction extends EditorActionDelegate{

	
	public QuickClassHierarchyAction() {}
	public QuickClassHierarchyAction(SourceEditor editor) {
		super(editor);
	}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		return HardwareChecker.isQuickViewEnabled();
	}

	@Override
	public void run(GeneralEditor editor1) {
		SourceEditor editor = (SourceEditor) editor1;
		QuickViewTop top = new QuickClassHierarchy(editor,editor.getEditorSite().getShell(), 0, 0);
		top.open();
		top.setInput(null);
	}
	
	public static class ObjectHandler extends GeneralHandler {

		@Override
		public GeneralEditorActionDelegate createDelegate(GeneralEditor editor) {
			return new QuickClassHierarchyAction((SourceEditor)editor);
		}
		
	}

	

}
