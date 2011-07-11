/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.project;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class LibraryHolder extends ModuleObjectWithList<CoreProjectBasic>{

	public LibraryHolder() {}
	
	
	public ReferenceItem findLibarayReference(ModuleObjectFindItem item, int type) {
		if (item == null) return null;
		ReferenceItem base = new ReferenceItem(item.getname(),type,null);
   	 	ModuleObjectIndexTop index = new ModuleObjectIndexTop(base,item);
   	 
		for (ReferenceItem ref : this.getGenericSelfList()) {
    		ReferenceItem uref = ref.findBaseReference(index);
    		if (uref != null) return uref;
    	}
    	return null;
	}
	
	public int getSearchType() {
		return ReferenceUtilities.REF_LIBRARY_HOLDER;
	}
}
