/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.types;

import java.util.List;

import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;


/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:34:34 AM
 * To change this template use File | Settings | File Templates.
 */

/** This class is used to define types and is used for all of the variables for decoding */


public class ClassTypeVar<T extends SystemVar> extends TypeVar<T>
{


	private ReferenceItem<ClassInstanceModule> classR;
	
    public ClassTypeVar() {}
    public ClassTypeVar(ReferenceItem<ClassInstanceModule> classR){
        super(classR.getname());
        this.classR = classR;
    }
 
    
	public int getSearchType() {return ReferenceUtilities.REF_TYPE_STRUCT;}
        public String getTemplateName() {return "type_struct";}
    /** Searches this list which contains systemvars */
    public ReferenceItem<SystemVar> findBaseVarRef(ModuleObjectIndexTop index) {
        return this.classR.findBaseReference(index);
    }
    /** Searches this list which contains a list of systemVars */
    public List<? extends ReferenceItem> findVarPrefixItemList(ModuleObjectFindItem item, int type) {
        return this.classR.findPrefixItemList(item,type);
    }
          

	public void setClassR(ReferenceItem<ClassInstanceModule> classR) {
		this.classR = classR;
	}
	public ReferenceItem<ClassInstanceModule> getClassR() {
		return classR;
	}
}
