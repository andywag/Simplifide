package com.simplifide.base.core.interfac;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class ProgramInstanceModule extends InstanceModule{

	 public ProgramInstanceModule(String name, ReferenceItem<CoreProjectBasic> projectRef) {
	    	super(name,projectRef);
	    }
	 
	 public int getSearchType(){return ReferenceUtilities.REF_INSTANCE_INTERFACE;}
	
}
