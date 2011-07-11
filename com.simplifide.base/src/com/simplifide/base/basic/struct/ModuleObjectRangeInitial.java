package com.simplifide.base.basic.struct;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;

/** @deprecated : Use ModuleObjectRangeListInitial */
public class ModuleObjectRangeInitial extends ModuleObjectNew{

        
    	private ReferenceItem<VarRange> range;
    	private ReferenceItem expressionR;
    	
    	public ModuleObjectRangeInitial(String name, ReferenceItem<VarRange> range) {
    		super(name);
    		this.setRange(range);
    	}

		public void setRange(ReferenceItem<VarRange> range) {
			this.range = range;
		}

		public ReferenceItem<VarRange> getRange() {
			return range;
		}

		public void setExpressionR(ReferenceItem expressionR) {
			this.expressionR = expressionR;
		}

		public ReferenceItem getExpressionR() {
			return expressionR;
		}

		
    
	
}
