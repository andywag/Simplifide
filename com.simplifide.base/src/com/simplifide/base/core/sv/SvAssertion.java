package com.simplifide.base.core.sv;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;

public class SvAssertion extends ModuleObjectNew{

	public SvAssertion(String name) {
		super(name);
	}
	
	public int getSearchType() {
		return ReferenceUtilitiesInterface.REF_ASSERTION;
	}
	
}
