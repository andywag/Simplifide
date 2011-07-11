/*
 * MultiOperator.java
 *
 * Created on December 12, 2005, 7:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.operator;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author Andy Wagner
 * This is the base class for the unit contained in an expression
 * 
 */
public class MultiOperator<T extends MultiOperatorUnit> extends TopOperator<T>
{    
    
    
    private ReferenceItem firstItem;
    /** Creates a new instance of MultiOperator */
    public MultiOperator() {}
    public MultiOperator(String name) {super(name);}
    public MultiOperator(String name, ReferenceItem firstItem) {
        super(name);
        this.firstItem = firstItem;
    }

    public ReferenceItem<ModuleObjectWithList> getDependants() {
        ReferenceItem<ModuleObjectWithList> outListRef = this.getAllChildDependents(); 
        if (this.firstItem != null) {
            ReferenceItem<ModuleObjectWithList> chList = this.firstItem.getDependants();
            outListRef.getObject().addAll(chList.getObject());
        }
        return outListRef;
    }
    

    public ReferenceItem<TypeVar> getTypeReference() {
        if (firstItem != null) {
            return (ReferenceItem<TypeVar>)firstItem.getTypeReference();
        }
        return null;
    }

    

    
}
