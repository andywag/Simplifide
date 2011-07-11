package com.simplifide.base.core.sv;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class SvClocking extends ModuleObjectNew{

	public SvClocking(String name) {
		super(name);
	
	}
	
	public int getSearchType() {
		return ReferenceUtilities.REF_CLOCKING;
	}
}
