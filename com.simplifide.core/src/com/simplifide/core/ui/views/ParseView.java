/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.views;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.tree.ASTNodeTreeProvider;

public class ParseView extends BasicTreeView implements ISelectionListener{

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
		
	}

	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
	}
	
	
	@Override
	protected ITreeContentProvider getContentProv() {
		return new ASTNodeTreeProvider();
	}

	@Override
	protected LabelProvider getLabelProv() {
		return new ASTNodeTreeProvider.Label();
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof SourceEditor) {
			SourceEditor editor = (SourceEditor) part;
			if (editor == null) return;
			if (editor.getDesignFile() == null) return;
			if (editor.getDesignFile().getParseDescriptor() == null) return;
			TopASTNode root = editor.getDesignFile().getParseDescriptor().getRoot();
			if (root != null) {
				this.changeInput(root);
			}
		}
	}

	

}
