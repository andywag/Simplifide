/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.resources.IconManager;

public class DocElement extends TopElement<EclipseSuite>{

	public DocElement(ReferenceItem suiteElement) {
		super(suiteElement);
	}
	
	public String getName() {
		return "Doc";
	}
	
	public Image getImageDescriptor() {
		return IconManager.getImage(IconManager.REF_CORE_PROJECT_FOLDER);
	}

	@Override
	public void addActions(IMenuManager manager, IStructuredSelection selection) {
		// TODO Auto-generated method stub
	}
	
	public Object[] getChildren() {
		return new Object[0];
	}
	
	public boolean hasChildren() {	
		return false;
	}
}
