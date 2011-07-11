package com.simplifide.core.baseeditor.actions.format;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorPart;

import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.actions.GeneralEditorActionDelegate;

public abstract class FormatActionDelegate extends GeneralEditorActionDelegate{

	public FormatActionDelegate() {}
	public FormatActionDelegate(GeneralEditor editor) {
		super(editor);
		this.setEnabled(true);
	}
	
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		super.setActiveEditor(action, targetEditor);
		if (action != null) action.setEnabled(true);
	}
	@Override
	public boolean checkEnabled(GeneralEditor editor) {
		return true;
	}
	
	
	
}
