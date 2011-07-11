package com.simplifide.base.core.sv;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class SvSequence extends ModuleObjectNew{

	public SvSequence(String name) {
		super(name);
	}
	
	public int getSearchType() {
		return ReferenceUtilities.REF_SEQUENCE;
	}
	
}
