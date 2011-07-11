/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.realvars;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;

public class DelegateVar extends SystemVar{

	private ReferenceItem<ModuleObjectWithList> deps;
    private ReferenceItem<SystemVar> delegateVar;
    private ReferenceItem<SystemVar> baseVar;
    
    public DelegateVar(SystemVar delegateVar, ReferenceItem<SystemVar> baseVar) {
        super(delegateVar.getname(), delegateVar.getTypeReference(), delegateVar.getOpTypeVar());
        this.delegateVar = delegateVar.createReferenceItem();
        this.baseVar = baseVar;
      
    }
    
    public DelegateVar(SystemVar delegateVar, ReferenceItem<SystemVar> baseVar, 
    		ReferenceItem<ModuleObjectWithList> deps) {
        super(delegateVar.getname(), delegateVar.getTypeReference(), delegateVar.getOpTypeVar());
        this.delegateVar = delegateVar.createReferenceItem();
        this.baseVar = baseVar;
        this.deps = deps;
    }
    
    public ModuleObject getBaseSearchClass() {
    	return this.baseVar.getObject();
    }
    
    public String getDisplayName() {
    	return this.getBaseVar().getDisplayName() + this.delegateVar.getDisplayName();
    }
    
    public void setUsed(boolean used) {
        super.setUsed(used);
        if (delegateVar.getObject() != null) {
            delegateVar.getObject().setUsed(used);
        }
    }
    
    public void setAssigned(boolean used) {
        super.setAssigned(used);
        if (delegateVar.getObject() != null) {
            delegateVar.getObject().setAssigned(used);
        }
    }
    
    public ReferenceItem<ModuleObjectWithList> getOutputs() {
       return this.getBaseVar().getObject().getOutputs();
    }

    public ReferenceItem<ModuleObjectWithList> getDependants() {
    	ModuleObjectWithList list = new ModuleObjectWithList();
    	list.addAll(this.deps.getObject());
    	list.addAll(this.getBaseVar().getObject().getDependants().getObject());
        return list.createReferenceItem();
    }

	public void setDeps(ReferenceItem<ModuleObjectWithList> deps) {
		this.deps = deps;
	}

	public ReferenceItem<ModuleObjectWithList> getDeps() {
		return deps;
	}

	public void setBaseVar(ReferenceItem<SystemVar> baseVar) {
		this.baseVar = baseVar;
	}

	public ReferenceItem<SystemVar> getBaseVar() {
		return baseVar;
	}
    
}
