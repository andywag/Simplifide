package com.simplifide.core.ui.views.quick;

import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.outline.SourceContentPaneTreeProvider;
import com.simplifide.core.ui.tree.ReferenceItemLabelProvider;

public class QuickOutlineView extends QuickViewTop{

	public QuickOutlineView(SourceEditor editor, Shell parent, int shellStyle, int treeStyle) {
		super(editor, parent, shellStyle, treeStyle);
	}

	protected String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected TreeViewer createTreeViewer(Composite parent, int style) {
		TreeViewer viewer = super.createTreeViewer(parent, style);
		 viewer.setContentProvider(new SourceContentPaneTreeProvider());
	     viewer.setLabelProvider(new ReferenceItemLabelProvider());
		return viewer;
		
	}

	public void setInput(Object information) {
		SourceEditor editor = this.getEditor();
		TreeViewer view = this.getTreeViewer();
		if (editor != null) {
			if (editor.getDesignFile() != null) {
				ReferenceItem item = editor.getDesignFile().getModuleRef();
				if (item != null) {

					view.setInput(item);
					view.expandToLevel(2);
					//view.expandAll();
				}
				
			} 
		} else {
			view.setInput(null);
		}
	}

}
