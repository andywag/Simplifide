/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.types;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.SystemVar;

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:34:34 AM
 * To change this template use File | Settings | File Templates.
 */

/** This class is used to define types and is used for all of the variables for decoding */


public class TypeVar<T extends SystemVar> extends ModuleObjectWithList<T>
{
    
    
    public TypeVar() {}
    public TypeVar(String name) {
        super(name);
    }
    
    public String getVerilogName() {
    	return "";
    }
    
    
    public String getVerilogDisplayName() {
    	return this.getname();
    }
    
    public String getVhdlDisplayName() {
    	return "";
    }
    
    /** Convenience Function for Templates to return the range */
    public VarRange getRange() {
    	return null;
    }
    
    public int getWidth() {
    	return 0;
    }
    

    public ReferenceItem getRangeReference() {
    	return null;
    }
    
    
    public String getTypeString() {
        int type = this.getSearchType();
        if (type == ReferenceUtilities.REF_TYPE_ARRAY) return "Array";
        if (type == ReferenceUtilities.REF_TYPE_ARRAY_UNCONSTRAINED) return "Array";
        if (type == ReferenceUtilities.REF_TYPE_ENUM) return "Enumeration";
        if (type == ReferenceUtilities.REF_TYPE_STRUCT) return "Record";
        if (type == ReferenceUtilities.REF_TYPE_SUB) return "SubType";
        return "Type";
    }
    
    public int getSearchType() {return ReferenceUtilities.REF_TYPEVAR;}
     
    public boolean isCompleteItem() {return true;}
    
    /** One level of indirection for finding a variable */
    public ReferenceItem<SystemVar> findBaseVarRef(ModuleObjectIndexTop index) {
        return null;
    }
    /** One level of inderection for finding a prefix list */
    public List<? extends ReferenceItem> findVarPrefixItemList(ModuleObjectFindItem item, int type) {
        return new ArrayList();
    }
    /** One level of inderection for finding a prefix list */
    public List<? extends ReferenceItem> findVarParenItemList(ModuleObjectFindItem item, int type) {
        return new ArrayList();
    }
    /** One level of indirection for finding a slice of a variable */
    public ReferenceItem<SystemVar> findSliceVarRef(ModuleObjectIndexTop index, SystemVar var) {
        return null;
    }
    
    
    /** Gave one extra level to this operation so I could make more easy changes */
    public int typeCheckCompare(TypeVar utype) {
        return this.compareTo(utype);
    }
    /** This is a regrettable thing to do but there is a lot to be done. I am not creating a global type comparison operation
     *  in place. This needs to be done and will probably have to be done before adding this operation */
    public int compareTo(TypeVar utype) {
       if (utype instanceof NumericTypeVar) {
           return 0;
       }
       return this.getname().compareToIgnoreCase(utype.getname());
    }
    
    /** This is a compare operation which needs to be less inclusive for completion */
    public int compareToForSearch(TypeVar utype) {
        return this.getname().compareToIgnoreCase(utype.getname());
    }
    
    
    /** Returns the true type represented by this type. This is mainly of subtype vars which point to other
     *  variables */
    public TypeVar getBaseVar() {
        return this;
    }
    
    
    
    public static class Inconsistent extends TypeVar {
        public Inconsistent() {super("Inconsistent");}
    }



	public String createVhdlWireDeclaration() {
		return this.getname();
	}
	
	public static class NotDefined extends TypeVar {
		public NotDefined(String name) {
			super(name);
		}
	}
    
}















