/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.simplifide.base.core.reference.ReferenceItem;

public class ReferenceItemTreeProvider implements ITreeContentProvider{

	public ReferenceItemTreeProvider() {}

	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		ReferenceItem item = (ReferenceItem) parentElement;
		return item.getNavigatorList().toArray();
		
	}

	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren(Object element) {
		ReferenceItem item = (ReferenceItem) element;
		return item.hasNavigatorChildren();
	}

	public Object[] getElements(Object inputElement) {
		return this.getChildren(inputElement);
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}
}
