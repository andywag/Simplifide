/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.vhdl.types;
import com.simplifide.base.core.reference.ReferenceItem;
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


public class VhdlArrayTypeVar extends ArrayTypeVar
{

   
    
    public VhdlArrayTypeVar() {}
    public VhdlArrayTypeVar(String name)
    {
        super(name);
    }
    public VhdlArrayTypeVar(String name, ReferenceItem range, ReferenceItem<TypeVar> type) {
        super(name,range,type);
    }
    
    
    
    
   
}
