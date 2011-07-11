/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.reference;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.simplifide.base.basic.struct.ModuleObject;

public class SourceSearchContentProvider implements ITreeContentProvider{

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof SourceSearchResult) {
			SourceSearchResult result = (SourceSearchResult) parentElement;
			return new Object[0];
		}
		ModuleObject ele = (ModuleObject) parentElement;
		return ele.getArrayList().getArrayList().toArray();
	}

	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof SourceSearchResult) return false;
		ModuleObject obj = (ModuleObject) element;
		if (obj.getnumber() == 0) return false;
		return true;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

}
