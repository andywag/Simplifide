/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.types;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.SystemVar;


/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:34:34 AM
 * To change this template use File | Settings | File Templates.
 */

/** This class is a pointer type which delegates to holdVar */


public class AccessTypeVar extends TypeVar {
    
    
    private ReferenceItem<? extends TypeVar> typeReference;
    
    public AccessTypeVar() {}
    public AccessTypeVar(String name) {
        super(name);
    }
    
    public AccessTypeVar(String name, ReferenceItem<? extends TypeVar> ref) {
        super(name);
        this.typeReference = ref;
    }
    
    
    
    public String getTemplateName() {return "type_sub";}
            
    public TypeVar getBaseVar() {
        if (this.typeReference != null) {
            return this.typeReference.getObject().getBaseVar();
        }
        return this;
    }
    
    /** Method Overides to use the type reference to search */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        if (this.typeReference != null) {
            return this.typeReference.findBaseReference(item);
        }
        return null;
    }
    

    public ReferenceItem<SystemVar> findSliceVarRef(ModuleObjectIndexTop index, SystemVar var) {
        if (this.typeReference != null) {
           
            return this.typeReference.getObject().findSliceVarRef(index,var);
        }
        return null;
    }
    
    public boolean hasNavigatorChildren() {
    	if (this.typeReference != null) return true;
    	return false;
    }
    public List<ReferenceItem> getNavigatorList() {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        if (this.typeReference != null) {
            list.add(this.typeReference);
        }

        return list;
    }
    

    public List<? extends ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.getTypeReference() != null) {
            return this.getTypeReference().findPrefixItemList(item,type);
        }
        return new ArrayList<ReferenceItem>();
    }
    
    
    
    
    
    
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        return typeReference;
    }
    
    public void setTypeReference(ReferenceItem<? extends TypeVar> typeReference) {
        this.typeReference = typeReference;
    }
    

    

  
    
    
}
