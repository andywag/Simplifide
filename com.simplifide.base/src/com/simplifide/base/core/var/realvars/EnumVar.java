/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.realvars;


import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: awagner
 * Date: Oct 18, 2004
 * Time: 7:33:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnumVar extends SystemVar  {
    
    
   
    
    public EnumVar() {}
    public EnumVar(String name) {super(name);}
    public EnumVar(String name, ReferenceItem type, OperatingTypeVar op) {
        super(name,type,op);
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_ENUMVAR;
    }
    
}
