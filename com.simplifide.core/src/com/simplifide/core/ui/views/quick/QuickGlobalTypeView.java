package com.simplifide.core.ui.views.quick;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.ui.tree.ReferenceItemLabelProvider;
import com.simplifide.core.ui.tree.ReferenceItemTreeProvider;

public class QuickGlobalTypeView extends QuickViewTop{

	public QuickGlobalTypeView(SourceEditor editor,Shell parent, int shellStyle, int treeStyle) {
		super(editor,parent, shellStyle, treeStyle);
	}

	protected String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected TreeViewer createTreeViewer(Composite parent, int style) {
		TreeViewer viewer = super.createTreeViewer(parent, style);
		viewer.setLabelProvider(new ReferenceItemLabelProvider());
		viewer.setContentProvider(new ReferenceItemTreeProvider());
		return viewer;
		
	}

	public void setInput(Object information) {
		//this.getfTreeViewer().setInput();
	}

}
