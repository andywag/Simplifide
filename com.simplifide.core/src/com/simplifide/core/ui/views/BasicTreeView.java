/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.views;


import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;

import com.simplifide.core.ui.tree.ReferenceItemLabelProvider;
import com.simplifide.core.ui.tree.ReferenceItemTreeProvider;

public class BasicTreeView extends ViewPart {
	
	private TreeViewer treeView;
	private Display display;

	public BasicTreeView() {

	}
	
	protected ITreeContentProvider getContentProv() {
		return new ReferenceItemTreeProvider();
	}
	
	protected LabelProvider getLabelProv() {
		return new ReferenceItemLabelProvider();
	}
	
	
	@Override
	public void createPartControl(Composite parent) {
		this.setTreeView(new TreeViewer(parent));
		this.getTreeView().setContentProvider(this.getContentProv());
		this.getTreeView().setLabelProvider(this.getLabelProv());
		this.setDisplay(parent.getDisplay());
	}

	public void changeInput(final Object input) {
		if (getDisplay() != null) {
			getDisplay().asyncExec(new Runnable() {
				public void run() {
					Object[] expanded = getTreeView().getExpandedElements();
					getTreeView().setInput(input);
					getTreeView().setExpandedElements(expanded);
				}
			});
		} else {
			getTreeView().setInput(input);
		}
	}

	@Override
	public void setFocus() {
		getTreeView().getControl().setFocus();

	}

	public void setTreeView(TreeViewer treeView) {
		this.treeView = treeView;
	}

	public TreeViewer getTreeView() {
		return treeView;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Display getDisplay() {
		return display;
	}

}
