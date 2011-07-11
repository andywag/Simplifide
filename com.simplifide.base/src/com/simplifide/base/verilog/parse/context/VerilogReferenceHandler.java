package com.simplifide.base.verilog.parse.context;

import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.core.reference.ReferenceHandler;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class VerilogReferenceHandler extends ReferenceHandler{

	
	private ReferenceItem<ClassModule> getClassModule() {
		ReferenceItem ref = this.getModuleReference();
    	if (ref != null && ref.getObject() instanceof ClassModule) {
    		return ref;
    	}
    	
    	ref = this.getActiveReference();
    	if (ref != null && ref != null && ref.getObject() instanceof ClassModule) {
    		return ref;
    	}
    	return null;
	}
	/** Decodes if value is fixed reference */
    public ReferenceItem getFixedReference(ModuleObjectFindItem prefix)
    {
        ReferenceItem uitem = null;
        if (prefix == null || prefix.getname() == null) return uitem;
        if (prefix.getname().equals("root")) uitem = this.getGlobalReference(); 
        else if (prefix.getname().equals("work")) uitem = this.getProjectReference();
        else if (prefix.getname().equals("this")) {
   
        	ReferenceItem ref = this.getClassModule();
        	if (ref != null) return ref;
        	
        	ref = this.getActiveReference();
        	if (ref != null && ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_TOP_MODULE) == 0) {
        		HardwareModule tmod = (HardwareModule) ref.getObject();
        		InstanceModule inst = (InstanceModule) tmod.getInstModRef().getObject();
        		return ModuleObjectCompositeHolder.dualHolder("", ref,inst.getEntityReference()).createReferenceItem();
        	}
        	
            return this.getActiveReference();
        	
        }
        else if (prefix.getname().equals("super")) {
        	ReferenceItem<ClassModule> hardr = this.getClassModule();
        	if (hardr == null) return null;
        	
        	ClassInstanceModule imod = hardr.getObject().getSuperModule();
        	if (imod != null) return imod.getArchitectureReference();
        	
        	/*ReferenceItem aitem = this.getActiveReference();
        	if (aitem.getObject() instanceof ClassInstanceModule) {
        		ClassInstanceModule mod = (ClassInstanceModule) aitem.getObject();
        		ClassModule cmod = (ClassModule) mod.getArchitecture();
        		return cmod.getSuperModule().getArchitectureReference();
        	}
        	if (aitem.getObject() instanceof ClassModule) {
        		ClassModule cmod = (ClassModule) aitem.getObject();
        		ClassInstanceModule imod = cmod.getSuperModule();
        		if (imod == null) return null;
        		return imod.getArchitectureReference();
        	}
        	if (aitem.getObject() instanceof ModuleObjectCompositeHolder) {
        		ModuleObjectCompositeHolder hold = (ModuleObjectCompositeHolder) aitem.getObject();
        		for (ReferenceItem ref : hold.getGenericSelfList()) {
        			if (ref.getObject() instanceof ClassModule) {
        				ClassModule cmod = (ClassModule) ref.getObject();
                		ClassInstanceModule imod = cmod.getSuperModule();
                		if (imod == null) return null;
                		return imod.getArchitectureReference();
        			}
        			if (ref.getObject() instanceof ClassInstanceModule) {
                		ClassInstanceModule imod = (ClassInstanceModule) ref.getObject();
                		if (imod == null) return null;
                		return imod.getArchitectureReference();
        			}
        		}
        	}*/
        }
        return uitem;
    }
	
}
