/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.base.core.reference.ReferenceItem;

public class BaseElement extends TopElement{

	
	public BaseElement(ReferenceItem item) {
		super(item);
	}
	
	public Object[] getChildren() {
		
		return null;
	}

	public String getName() {
		
		return this.getItem().getname();
	}

	public boolean hasChildren() {
		return false;
	}

	@Override
	public void addActions(IMenuManager manager, IStructuredSelection selection) {

		
	}

}
