/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.outline;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.ui.tree.ReferenceItemTreeProvider;

public class SourceContentPaneTreeProvider extends ReferenceItemTreeProvider{

	
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		ReferenceItem item = (ReferenceItem) parentElement;
		
		List<ReferenceItem> outList = new ArrayList<ReferenceItem>();
		List<ReferenceItem> itemList = item.getNavigatorList();
		
		for (ReferenceItem outItem : itemList) {
			if (outItem == null) continue;
			if (outItem.getType() == ReferenceUtilities.REF_PACKAGE_MODULE) {
				if (outItem.getname().endsWith("_Context")) {
					continue;
				}
			}
			if (outItem.getType() != ReferenceUtilities.REF_ENUM_HOLDER) {
				outList.add(outItem);
			}
			
		}
		
		return outList.toArray();
		
	}
	
}
