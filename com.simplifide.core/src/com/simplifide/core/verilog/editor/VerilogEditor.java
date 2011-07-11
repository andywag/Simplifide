/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.contexts.IContextService;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.python.MenuLoader;
import com.simplifide.core.python.context.EditorContext;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.verilog.editor.describer.VerilogFileAlone;

public class VerilogEditor extends SourceEditor{
	
	public static final String VERILOGEDITORCONTEXT = "#VerilogEditorContext";
	
	public VerilogEditor() {
		super();
		this.setDocumentProvider(new VerilogDocumentProvider());
		this.setSourceViewerConfiguration(new VerilogConfiguration(this.getColorManager(),this));
		this.setEditorContextMenuId(VERILOGEDITORCONTEXT);
	} 
	
	public void createPartControl(Composite parent) {
		IContextService contextService = (IContextService) getSite()
		.getService(IContextService.class);
		contextService.activateContext(VERILOGEDITORCONTEXT);
		super.createPartControl(parent);
	}
	
	protected DesignFile getDesignFileAlone(IFileStore fileStore) {
		return new VerilogFileAlone(fileStore.toURI());	
	}

	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE,vmenu);
	
		//this.addAction(menu, SourceEditor.GROUP_SIMPLIFIDE, CommentActions.ACTION_COMMENT_ID);
		//this.addAction(menu, SourceEditor.GROUP_SIMPLIFIDE, CommentActions.ACTION_UNCOMMENT_ID);
		
		//menu.appendToGroup(SourceEditor.GROUP_SIMPLIFIDE, new CapAction.Capitalize(this));
		//menu.appendToGroup(SourceEditor.GROUP_SIMPLIFIDE, new CapAction.UnCapitalize(this));
		//menu.insertAfter(SourceEditor.GROUP_SIMPLIFIDE, new EmacsMenu.Verilog(this));
		EditorContext context = new EditorContext(this);
		MenuLoader.createEditorActions2(menu, context);
	}
	
}
