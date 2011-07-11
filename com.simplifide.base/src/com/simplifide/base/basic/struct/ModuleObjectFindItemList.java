package com.simplifide.base.basic.struct;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.reference.ReferenceItem;

public class ModuleObjectFindItemList extends UniqueList<ModuleObjectFindItem>{

	 public void addObject(ReferenceItem<? extends ModuleObjectFindItem> inval) {
	        boolean add = true;
	        for (ReferenceItem<? extends ModuleObjectFindItem> item : this.getGenericSelfList()) {
	        	ModuleObjectFindItem inItem = inval.getObject();
	        	ModuleObjectFindItem thItem = item.getObject();
	        	
	            if (inItem.getFindName().equalsIgnoreCase(thItem.getFindName())) {
	            	return;
	            }
	        }
	        if (add) this.addObjectBypass(inval);
	    }    
	
}
