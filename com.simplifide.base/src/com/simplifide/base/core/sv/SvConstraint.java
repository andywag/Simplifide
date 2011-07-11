package com.simplifide.base.core.sv;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.reference.ReferenceUtilities;


// constraint_declaration : ("static")? "constraint" constraint_identifier constraint_block

public class SvConstraint extends ModuleObjectNew{

	public SvConstraint(String name) {
		super(name);
	
	}
	
	public int getSearchType() {
		return ReferenceUtilities.REF_CONTRAINT;
	}
}
