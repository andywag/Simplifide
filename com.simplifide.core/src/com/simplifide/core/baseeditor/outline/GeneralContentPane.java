package com.simplifide.core.baseeditor.outline;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.simplifide.core.baseeditor.GeneralEditor;

public abstract class GeneralContentPane extends SourceOutlineContentPage{

	private Display display;
	private GeneralEditor editor;
	
	public GeneralContentPane(GeneralEditor editor) {
		this.setEditor(editor);
	}
	
	protected abstract void setInput();
	protected abstract void createActions();
	protected abstract void createTreeViewer();
	protected abstract void removeActions();
	
	
	public void dispose() {
    	super.dispose();
    	this.removeActions();
    }
	
	public void changeInput(Display display1) {
		Display ndisplay = display1;
		if (ndisplay == null) ndisplay = this.getDisplay();
		if (ndisplay != null) {
			ndisplay.asyncExec(new Runnable() {
				public void run() {
					setInput();
				}
			});	
		}
		
	}
	
	public void createControl(Composite parent) {
        super.createControl(parent);
        this.createTreeViewer();
        this.createActions();
        this.display = parent.getDisplay();
        this.setInput();
	}
	
	
	public void setEditor(GeneralEditor editor) {
		this.editor = editor;
	}

	public GeneralEditor getEditor() {
		return editor;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Display getDisplay() {
		return display;
	}
	
}
