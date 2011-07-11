/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.fan;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.simplifide.base.core.hierarchy.PathTotalTreeElement;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.source.LocationOperations;

public class FanInSearchPage extends AbstractTextSearchViewPage{

	private TreeViewer viewer;
	
	public FanInSearchPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_TREE);
	}
	
	@Override
	protected void clear() {
	}

	@Override
	protected void configureTableViewer(TableViewer viewer) {
		
	}

	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		viewer.setContentProvider(new FanSearchContentProvider());
		viewer.setLabelProvider(new FanSearchContentProvider.Label());	
        viewer.setSorter(new ViewerSorter());
        this.viewer = viewer;		
		viewer.getControl().addMouseListener(new MouseGoToListener());

	}

	@Override
	protected void elementsChanged(Object[] objects) {
		if (objects.length > 0) {
			if (objects[0] instanceof PathTotalTreeElement) {
				PathTotalTreeElement el = (PathTotalTreeElement) objects[0];
				this.viewer.setInput(el);
			}
		}
	}
	
	public class MouseGoToListener extends MouseAdapter{
		public void mouseDoubleClick(MouseEvent e) {
			IStructuredSelection select = (IStructuredSelection) viewer.getSelection();
			Object obj = select.getFirstElement();
			if (obj instanceof PathTotalTreeElement) {
				PathTotalTreeElement el = (PathTotalTreeElement) obj;
				ReferenceItem item = el.getPathElement().getVariable().createReferenceItem();
				ReferenceLocation loc = item.getLocation();
				if (loc != null) {
					LocationOperations.goToPosition(loc);
				}
			}
			
		}
	}

}
