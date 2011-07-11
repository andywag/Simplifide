package com.simplifide.core.ui.views;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ui.tree.ReferenceItemTreeProvider;

public class ObjectContentProvider extends ReferenceItemTreeProvider{

	
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		ReferenceItem item = (ReferenceItem) parentElement;
		List<ReferenceItem> refList =  item.getNavigatorList();
		ArrayList lis = new ArrayList();
		for (ReferenceItem ref : refList) {
			if (!ref.getname().endsWith("_Context")) {
				lis.add(ref);
			}
		}
		return lis.toArray();
	}
	
	public boolean hasChildren(Object element) {
		ReferenceItem item = (ReferenceItem) element;
		return item.hasNavigatorChildren();
	}
	
}
