/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.vhdl.types;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.types.ArrayTypeVar;
import com.simplifide.base.core.var.types.TypeVar;


/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:34:34 AM
 * To change this template use File | Settings | File Templates.
 */

/** This class is used to define types and is used for all of the variables for decoding */


public class VhdlUnconstrainedArrayTypeVar extends ArrayTypeVar {
    
    
    
    public VhdlUnconstrainedArrayTypeVar() {}
    public VhdlUnconstrainedArrayTypeVar(String name) {
        super(name);
    }
    public VhdlUnconstrainedArrayTypeVar(String name, ReferenceItem<TypeVar> pointVar) {
        super(name,null,pointVar);
    }
    
    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {
        // Laziness that will undoubtedly cost me time later
        if (top.getBase().getType() == ReferenceUtilities.REF_VAR_RANGE) {
            ReferenceItem<VarRange> rangeRef = top.getBase();
            if (rangeRef != null && this.getTypeReference() != null) {
                VhdlArrayTypeVar utype = new VhdlArrayTypeVar(this.getname(),rangeRef,this.getTypeReference());
                return utype.createReferenceItemWithLocation(this.getTypeReference().getLocation());
            }
        }
        else if (top.getBase().getObject() instanceof NoSortList) {
        	NoSortList li = (NoSortList) top.getBase().getObject();
        	ReferenceItem ref = (ReferenceItem) li.getObject(0);
        	if (ref.getType() == ReferenceUtilities.REF_VAR_RANGE) { // EXACTLY SAME AS ABOVE NEEDS NEW FUNCTION
                ReferenceItem<VarRange> rangeRef = ref;
                if (rangeRef != null && this.getTypeReference() != null) {
                    VhdlArrayTypeVar utype = new VhdlArrayTypeVar(this.getname(),rangeRef,this.getTypeReference());
                    return utype.createReferenceItemWithLocation(this.getTypeReference().getLocation());
                }
            }
        	
        }
        return null;
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_TYPE_ARRAY_UNCONSTRAINED;
    }
    
    protected String getArrayName() {
        return ""; //(natural range <>)";
    }
    
    
    
    
    
}
