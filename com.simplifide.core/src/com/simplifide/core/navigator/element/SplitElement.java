/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.SplitFile;

public class SplitElement extends TopElement<SplitFile>{

	public SplitElement(ReferenceItem<SplitFile> item) {
		super(item);
		
	}
	
	public Object[] getChildren() {
		SplitFile split = this.getItem().getObject();
		ArrayList outList = new ArrayList();
		List<DesignFile> designList = split.getDesignList();
		for (DesignFile design : designList) {
			outList.add(design.getResource());
		}
		return outList.toArray();
	}
	
	public boolean hasChildren() {
		return true;
	}

	@Override
	public void addActions(IMenuManager manager, IStructuredSelection selection) {
		
		
	}

}
