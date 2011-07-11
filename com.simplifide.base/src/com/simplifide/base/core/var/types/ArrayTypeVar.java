/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.types;
import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.vhdl.types.VhdlArrayTypeVar;


/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:34:34 AM
 * To change this template use File | Settings | File Templates.
 */

/** This class is used to define types and is used for all of the variables for decoding */


public class ArrayTypeVar extends TypeVar {
    

    private ReferenceItem rangeReference;
    private ReferenceItem<TypeVar> typeReference;
    
    public String getVerilogName() {
    	VarRange range = this.getRange();
    	if (range != null) {
    		return range.getVerilogName();
    	}
    	return "";
    }
    
  
    public ArrayTypeVar() {}
    
    public ArrayTypeVar(String name) {
        super(name);
    }
    
    public ArrayTypeVar(String name, ReferenceItem range, ReferenceItem<TypeVar> type) {
        super(name);
        this.rangeReference = range;
        this.setTypeReference(type);
    }
       
    public String getTemplateName() {return "type_array";}
    
    public String getVerilogDisplayName() {
    	if (this.rangeReference != null && this.rangeReference.getObject() != null) {
    		String rname = this.typeReference.getObject().getVerilogDisplayName();
    		ModuleObject obj = this.rangeReference.getObject();
    		if (obj instanceof VarRange) {
    			VarRange range = (VarRange) obj;
    			rname += range.getVerilogName();
    	    	return rname;
    		}
    	}
    	return "";
    }
    
    
    
    public String getVhdlDisplayName() {
    	return this.getDisplayName();
    }
    
    public int getWidth() {
    	VarRange range = this.getRange();
    	if (range != null) {
    		return range.getWidth();
    	}
    	else {
    		return 1;
    	}
    }
    
    /** One level of inderection for finding a prefix list */
    public List<? extends ReferenceItem> findVarPrefixItemList(ModuleObjectFindItem item, int type) {
    	if (this.typeReference != null && this.typeReference.getObject() != null) {
    		return this.typeReference.getObject().findVarPrefixItemList(item, type);
    	}
        return new ArrayList();
    }
    /** One level of inderection for finding a prefix list */
    public List<? extends ReferenceItem> findVarParenItemList(ModuleObjectFindItem item, int type) {
    	if (this.rangeReference != null) {
    		return this.rangeReference.findPrefixItemList(item, type);
    	}
        return new ArrayList();
    }    
    
    public String createVhdlWireDeclaration() {
    	String outstr = this.getname();
    	if (this.getRange() != null) {
    		outstr += this.getRange().getVhdlName();
    	}
    	return outstr;
    }
        
    /** Convenience Method to return the range */
    public VarRange getRange() {
        if (this.rangeReference != null && this.rangeReference.getObject() != null) {
        	ModuleObject obj = this.rangeReference.getObject();
        	if (obj instanceof VarRange) return (VarRange) obj;
        }
        return null;
    }
    
    /** Convenience Method to return type type */
    public TypeVar getType() {
        if (this.typeReference != null) {
            return this.typeReference.getObject();
        }
        return null;
    }
    
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
    	if (rangeReference != null) return rangeReference.getDependants();
    	return new ModuleObjectWithList().createReferenceItem();
    }
    
    public int getSearchType() {return ReferenceUtilities.REF_TYPE_ARRAY;}
    
    /** One level of indirection for finding a slice of a variable */
    public ReferenceItem<SystemVar> findSliceVarRef(ModuleObjectIndexTop index, SystemVar var) {
        ReferenceItem base = index.getBase();
        if (ReferenceUtilities.checkType(base.getType(),ReferenceUtilities.REF_VAR_RANGE) == 0) {
            ReferenceItem<TypeVar> typeRef = this.findSliceReference(index);
            String varName = StringOps.addParens(base.getDisplayName());
            SystemVar newVar = new SystemVar(varName,typeRef,var.getOpTypeVar());
            // Problematic that there is not a location for this
            return newVar.createReferenceItem();
        }
        else if (ReferenceUtilities.checkType(base.getType(),ReferenceUtilities.REF_NO_SORT_LIST) == 0) {
            NoSortList list = (NoSortList) base.getObject();
            // Not a good condition for finding a variable type but ...
            if (list.getnumber() == 1) {
                ReferenceItem<TypeVar> typeRef = this.getTypeReference();
                ReferenceItem uitem = (ReferenceItem) list.getObject(0);
                String varName = StringOps.addParens(uitem.getDisplayName());
                SystemVar newVar = new SystemVar(varName,typeRef,var.getOpTypeVar());
                return newVar.createReferenceItem();
            }
            else {
            	ReferenceItem<TypeVar> typeRef = this.getTypeReference();
                String varName = StringOps.addParens(base.getDisplayName());
                SystemVar newVar = new SystemVar(varName,typeRef,var.getOpTypeVar());
                return newVar.createReferenceItem();
            }
        }
        
        return null;
    }
    /** Directly creates a new copy of this array item with a different index */
    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {

        ReferenceItem base = top.getBase();
        if (ReferenceUtilities.checkType(base.getType(),ReferenceUtilities.REF_VAR_RANGE) == 0) {
            ReferenceItem<VarRange> rangeRef = top.getBase();
            VhdlArrayTypeVar utype = new VhdlArrayTypeVar(this.getname(),rangeRef,this.getTypeReference());
            if (this.getTypeReference() != null && this.getTypeReference().getLocation() != null) 
            	return utype.createReferenceItemWithLocation(this.getTypeReference().getLocation());
        }
        return null;
    }
    
   
    protected String getArrayName() {
    	VarRange range = this.getRange();
        if (range != null) {
            return range.getDisplayName();
        }
        return "";
    }
    
     
     public String getDisplayName() {
        return this.getname() +" "+ this.getArrayName();
    }
    
    public boolean hasNavigatorChildren() {
    	if (this.typeReference != null) return true;
    	return false;
    }
    public List<ReferenceItem> getNavigatorList() {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        if (getTypeReference() != null) list.add(this.getTypeReference());
        return list;
    }
    
   
    
    /**
     * 
     * @return 
     */
    public ReferenceItem<VarRange> getRangeReference() {
        return rangeReference;
    }

    /**
     * 
     * @param rangeReference 
     */
    public void setRangeReference(ReferenceItem<VarRange> rangeReference) {
        this.rangeReference = rangeReference;
    }

    public ReferenceItem<TypeVar> getTypeReference() {
        return typeReference;
    }

    /**
     * 
     * @param typeReference 
     */
    public void setTypeReference(ReferenceItem<TypeVar> typeReference) {
        this.typeReference = typeReference;
    }


    
  
   
    
   
    
    
    
}
