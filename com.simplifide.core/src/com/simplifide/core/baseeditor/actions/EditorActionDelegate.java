package com.simplifide.core.baseeditor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.IEditorPart;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.EditorUtilities;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.design.DesignFile;

public abstract class EditorActionDelegate extends GeneralEditorActionDelegate{

	
	private SourceEditor editor;
	
	
	public EditorActionDelegate() {super();}
	public EditorActionDelegate(SourceEditor editor) {this.setEditor(editor);}
	
	
	
	
    
	
	
	
		
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		super.setActiveEditor(action, targetEditor);
		if (targetEditor instanceof SourceEditor) {
			((SourceEditor)targetEditor).addListener(this);
		}
	}
	
	public DesignFile getDesignFile() {
		return this.getEditor().getDesignFile();
	}
	
	public EditorFindItem getFindItem() {
		return this.getEditor().getFindItemHolder().getFindItem();
	}
	
	public ReferenceItem getFindItemObject() {
		EditorFindItem item = this.getFindItem();
		if (item != null) {
			ReferenceItem ref = item.getItem();
			return ref;
		}
		return null;
	}
	
	public ReferenceLocation getLocation() {
		int cpos = getEditor().getCaretPosition();
    	int lnum = -1;
    	try {
			lnum = getEditor().getDocument().getLineOfOffset(cpos);
		} catch (BadLocationException e) {
			HardwareLog.logError(e);
		}
    	ReferenceLocation loc = new ReferenceLocation(getEditor().getDesignFile().getUri(),cpos,0,lnum);
    	return loc;
	}
	
	
   
    
 
    
    public void setEditor(SourceEditor editor) {
		this.editor = editor;
	}
	public SourceEditor getEditor() {
		if (editor == null) {
			editor = EditorUtilities.getActiveSourceEditor();
		}
		return editor;
	}





    
}
