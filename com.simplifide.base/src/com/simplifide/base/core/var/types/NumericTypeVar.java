/*
 * NumericTypeVar.java
 *
 * Created on March 20, 2007, 12:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.var.types;

import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy
 *
 *  Special Type class which is to delineate type mismatch errors
 */
public class NumericTypeVar extends TypeVar{
    
    private static NumericTypeVar instance = new NumericTypeVar();
    private static ReferenceItem<NumericTypeVar> refinstance = instance.createReferenceItem();
    /** Creates a new instance of NumericTypeVar */
    public NumericTypeVar() {}
    
    public static NumericTypeVar getInstance() {return instance;}
    public static ReferenceItem<NumericTypeVar> getInstanceRef() {return refinstance;}
    
    public int compareTo(TypeVar utype) {
        return 0;
        //if (utype.getname().equalsIgnoreCase("integer") || utype.getname().equalsIgnoreCase("natural")) return 0;
        //else return -1;
    }
    
}
