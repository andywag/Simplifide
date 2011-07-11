/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.condition;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;

public class CaseConditionStatementSingle extends CaseConditionStatement{

	private ReferenceItem inRef;
	
	public CaseConditionStatementSingle(String name, ReferenceItem choice, ReferenceItem in) {
		super(name,choice);
		this.inRef = in;
	}

	
	public ReferenceItem<ModuleObjectWithList> getDependants() {
        ReferenceItem<ModuleObjectWithList> outList = super.getDependants();
       
        if (this.inRef != null) {
            ReferenceItem<ModuleObjectWithList> stList = this.inRef.getDependants();
            outList.getObject().addAll(stList.getObject());
        }
        return outList;
    }
	
	public void setInRef(ReferenceItem inRef) {
		this.inRef = inRef;
	}

	public ReferenceItem getInRef() {
		return inRef;
	}
	
}
