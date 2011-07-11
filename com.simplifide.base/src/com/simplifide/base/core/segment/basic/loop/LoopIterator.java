/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.segment.basic.loop;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.generate.ParameterSpecification;
import com.simplifide.base.core.reference.ReferenceItem;

public class LoopIterator extends ModuleObjectNew{

	
	public ReferenceItem<ModuleObjectWithList> getOtherContext() {
		return null;
	}
	
	public static class For extends LoopIterator {
		
		private ReferenceItem<ParameterSpecification> specRef;
		
		public For(ReferenceItem<ParameterSpecification> specRef) {
			this.specRef = specRef;
		}
		
		public ReferenceItem<ModuleObjectWithList> getOtherContext() {
			return ModuleObjectWithList.singleton(specRef.getObject()).createReferenceItem();
		}
		
	}
	
	public static class While extends LoopIterator {
		
		private ReferenceItem conditionRef;
		
		public While(ReferenceItem conditionRef) {
			this.conditionRef = conditionRef;
		}
		
	}
	
}
