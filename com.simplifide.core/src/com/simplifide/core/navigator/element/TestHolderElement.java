/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.test.EclipseTest;

public class TestHolderElement extends TopElement<EclipseTest>{

	public TestHolderElement(ReferenceItem<EclipseTest> item) {
		super(item);
		
	}

	@Override
	public void addActions(IMenuManager manager, IStructuredSelection selection) {
		
		
	}

	
}
