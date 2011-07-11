/*
 * VerilogArrayType.java
 *
 * Created on April 23, 2007, 1:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.core.types;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.types.ArrayTypeVar;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author Andy
 */
public class VerilogArrayType extends ArrayTypeVar{
    
    /** Creates a new instance of VerilogArrayType */
    public VerilogArrayType() {}
    /**
     * Verilog Version of an Array Type
     * @param range 
     * @param type 
     */
    public VerilogArrayType(ReferenceItem<VarRange> range,ReferenceItem<TypeVar> type) {
        this("",range,type);
    }
    /**
     * 
     * @param name 
     * @param range 
     * @param type 
     */
    public VerilogArrayType(String name,ReferenceItem<VarRange> range,ReferenceItem<TypeVar> type) {
        super(name,range,type);
    }
    
}
