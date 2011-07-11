package com.simplifide.core.python.context;

import com.simplifide.core.editors.SourceEditor;

public class EditorContext implements ContextInterface.Editor{
  
	private SourceEditor editor;
	public EditorContext(SourceEditor editor) {
		this.editor = editor; 
	}
	
	public String getname() {
		return this.editor.getDesignFile().getname();
	}
   
	
	public Source getSource() {
		return new DesignContext(this.editor.getDesignFile());
	}

}
