/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.condition;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;

public class CaseSingleOutputTop extends CaseStatementTop{

	private ReferenceItem outRef;
	
	public CaseSingleOutputTop(String name, ReferenceItem condRef, ReferenceItem outRef) {
		super(name,condRef);
		this.outRef = outRef;
	}
	
	
	public ReferenceItem<ModuleObjectWithList> getOutputs() {
		if (outRef != null) {
			return outRef.getOutputs();
		}
		return new ModuleObjectWithList().createReferenceItem();
    }
	
}
