/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.tree;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.simplifide.base.core.reference.ReferenceItem;

public class ReferenceItemTreeSorter extends ViewerSorter {

	public int compare(Viewer viewer, Object e1, Object e2) {
		ReferenceItem ref1 = (ReferenceItem) e1;
		ReferenceItem ref2 = (ReferenceItem) e2;
		
		int type1 = ref1.getType();
		int type2 = ref2.getType();
		
		if (type1 != type2)
			return type1 - type2;
		
		return ref1.getname().compareTo(ref2.getname());
	}
	
}
