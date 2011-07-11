/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.condition;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;

public class CaseChoices extends NoSortList{

    public CaseChoices() {}
    
    public static class Default extends CaseChoices {
    	public Default() {}
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        return this.getAllChildDependents();
    }
    
}
