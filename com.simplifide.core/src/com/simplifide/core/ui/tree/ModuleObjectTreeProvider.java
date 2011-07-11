/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.simplifide.base.basic.struct.ModuleObject;

public class ModuleObjectTreeProvider implements ITreeContentProvider{

	
	public ModuleObjectTreeProvider() {}
	
	
	public Object[] getChildren(Object parentElement) {
		ModuleObject par = (ModuleObject) parentElement;
		return par.getGenericSelfList().toArray();
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		ModuleObject obj = (ModuleObject) element;
		if (obj.getnumber() > 0) return true;
		return false;
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
