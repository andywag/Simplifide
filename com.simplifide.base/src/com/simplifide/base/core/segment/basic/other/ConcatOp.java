/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.other;



import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;

public class ConcatOp extends NoSortList<ModuleObject>{

	public ConcatOp() {}
	
	
	public ReferenceItem<ModuleObjectWithList> getOutputs() {
		return this.getAllChildDependents();
	}
	
	public ReferenceItem<ModuleObjectWithList> getDependants() {
		return this.getAllChildOutputs();
	}
	
	
	public String getDisplayName() {
		String op = "{";
		boolean first = true;
		for (ModuleObject obj : this.getRealSelfList()) {
			if (!first) op += ",";
			op += obj.getDisplayName();
		}
		op += "}";
		return op;
	}
}
