package com.simplifide.core.vhdl.editor.actions;

import org.eclipse.jface.action.MenuManager;

import com.simplifide.core.editors.SourceEditor;

public class VhdlFormatMenu extends MenuManager{

	public VhdlFormatMenu(SourceEditor editor) {
		super("Format");
		this.add(new CapAction.Capitalize(editor));
		this.add(new CapAction.UnCapitalize(editor));
		this.add(new CommentActions2.Comment(editor));
		this.add(new CommentActions2.UnComment(editor));
		this.add(new CapFileAction.Capitalize(editor)); 
		this.add(new CapFileAction.UnCapitalize(editor));  

		
	}
	
}
