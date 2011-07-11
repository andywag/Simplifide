package com.simplifide.core.baseeditor.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionDelegate;

import com.simplifide.core.baseeditor.EditorUtilities;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.editors.SourceEditor;

public abstract class GeneralEditorActionDelegate extends ActionDelegate implements IEditorActionDelegate{

	private IAction action;
	private GeneralEditor editor;
	
	public GeneralEditorActionDelegate() {}
	public GeneralEditorActionDelegate(GeneralEditor editor) {
		this.editor = editor;
	}
	
	public abstract void run(GeneralEditor editor);
	public abstract boolean checkEnabled(GeneralEditor editor);
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.setAction(action);
		if (targetEditor instanceof GeneralEditor) {
			this.editor = (GeneralEditor) targetEditor;
		}
	}
	public boolean isEnabled() {
		return this.getAction().isEnabled();
	}
	public void setEnabled(boolean bool) {
		if (this.getAction() != null) this.getAction().setEnabled(bool);
	}
	public void enableUpdate() {
		boolean enabled = this.checkEnabled(this.getEditor());
		this.setEnabled(enabled);
	}
	
	 public void run(IAction action) {
	    	this.run(this.getEditor());
	    }
	
	
	public void setEditor(SourceEditor editor) {
		this.editor = editor;
	}
	public GeneralEditor getEditor() {
		if (editor == null) {
			editor = EditorUtilities.getActiveGeneralEditor();
		}
		return editor;
	}
	
	

	public void setAction(IAction action) {
		this.action = action;
	}
	public IAction getAction() {
		return action;
	}
	
	public abstract static class GeneralHandler extends AbstractHandler{
    	
    	private GeneralEditorActionDelegate action;
    	private GeneralEditor editor;
    	
    	public GeneralHandler() {
    		this.editor = EditorUtilities.getActiveGeneralEditor();
    		this.action = this.createDelegate(editor);
    	}
    	public abstract GeneralEditorActionDelegate createDelegate(GeneralEditor editor);
		 
		public Object execute(ExecutionEvent event) throws ExecutionException {
			this.action.run(editor);
			return null;
		}
		public void setEnabled(Object evaluationContext) {}
		public boolean isEnabled() {
			boolean en = this.action.checkEnabled(editor);
			return en;
		}
    	
    }

	
}
