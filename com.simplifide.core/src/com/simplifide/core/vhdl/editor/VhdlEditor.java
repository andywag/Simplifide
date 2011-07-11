/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor;


import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.contexts.IContextService;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.python.MenuLoader;
import com.simplifide.core.python.context.EditorContext;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.vhdl.describer.VhdlFileAlone;
import com.simplifide.core.vhdl.editor.actions.CommentActions2;


public class VhdlEditor extends SourceEditor{
	
	public static final String VHDLEDITORCONTEXT = "#VhdlEditorContext";

	
	public VhdlEditor() {
		super();
		this.setDocumentProvider(new VhdlDocumentProvider());
		this.setSourceViewerConfiguration(new VhdlConfiguration(this.getColorManager(),this));
		this.setEditorContextMenuId(VHDLEDITORCONTEXT);
	}
	
	public void createPartControl(Composite parent) {
		IContextService contextService = (IContextService) getSite()
		.getService(IContextService.class);
		contextService.activateContext(VHDLEDITORCONTEXT);
		
		
		super.createPartControl(parent);
	}
	
	protected DesignFile getDesignFileAlone(IFileStore fileStore) {
		return new VhdlFileAlone(fileStore.toURI());	
	}
	
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		EditorContext context = new EditorContext(this);
		MenuLoader.createEditorActions2(menu, context);
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,vmenu);
	
		//this.addAction(menu, SourceEditor.GROUP_SIMPLIFIDE, CommentActions.ACTION_COMMENT_ID);
		//this.addAction(menu, SourceEditor.GROUP_SIMPLIFIDE, CommentActions.ACTION_UNCOMMENT_ID);
		
		//menu.appendToGroup(SourceEditor.GROUP_SIMPLIFIDE, new CapAction.Capitalize(this));
		//menu.appendToGroup(SourceEditor.GROUP_SIMPLIFIDE, new CapAction.UnCapitalize(this));
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE, new EmacsMenu.Vhdl(this));
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE, new VhdlFormatMenu(this));
	}
	
	public IAction getAction(String actionID) {
		// TODO Auto-generated method stub
		if (actionID.equals(CommentActions2.ACTION_COMMENT_ID)) return new CommentActions2.Comment(this);
		if (actionID.equals(CommentActions2.ACTION_UNCOMMENT_ID)) return new CommentActions2.UnComment(this);
		return super.getAction(actionID);
	}
	
	
	
}
