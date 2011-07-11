package com.simplifide.base.core.interfac;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class InterfaceInstanceModule extends InstanceModule{

	 public InterfaceInstanceModule(String name, ReferenceItem<CoreProjectBasic> projectRef) {
	    	super(name,projectRef);
	    }
	 
	 public ReferenceItem findBaseReference(ModuleObjectIndexTop item)
	    { 
	        //if (ReferenceUtilities.checkType(ReferenceUtilities.REF_INSTANCE_MODULE,type) == 0) return this;
	        ReferenceItem ref = super.findBaseReference(item);
		 	if (ref == null) ref = this.getEntityReference().findBaseReference(item);
	        if (ref == null) ref = this.getArchitectureReference().findBaseReference(item);
	        return ref;
	    }
	 
	 public boolean hasNavigatorChildren() {
		 return true;
	 }
	 
	 public List<ReferenceItem> getNavigatorList() {
		 ArrayList<ReferenceItem> refs = new ArrayList<ReferenceItem>();
		 refs.addAll(this.getEntityReference().getNavigatorList());
		 refs.addAll(this.getArchitectureReference().getNavigatorList());
		 return refs;
	 }
	 
	 public int getSearchType(){return ReferenceUtilities.REF_INSTANCE_INTERFACE;}
	
}
