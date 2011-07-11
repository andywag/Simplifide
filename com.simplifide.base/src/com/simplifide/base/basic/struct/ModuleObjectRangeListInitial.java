package com.simplifide.base.basic.struct;

import java.util.ArrayList;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;

public class ModuleObjectRangeListInitial extends ModuleObjectNew{

        
    	private ArrayList<ReferenceItem<VarRange>> ranges;
    	private ReferenceItem expressionR;
    	
    	public ModuleObjectRangeListInitial(String name, 
    			ArrayList<ReferenceItem<VarRange>> ranges,
    			ReferenceItem expressionR) {
    		super(name);
    		this.setRanges(ranges);
    		this.expressionR = expressionR;
    	}

		

		public void setExpressionR(ReferenceItem expressionR) {
			this.expressionR = expressionR;
		}

		public ReferenceItem getExpressionR() {
			return expressionR;
		}



		public void setRanges(ArrayList<ReferenceItem<VarRange>> ranges) {
			this.ranges = ranges;
		}



		public ArrayList<ReferenceItem<VarRange>> getRanges() {
			return ranges;
		}

		
    
	
}
