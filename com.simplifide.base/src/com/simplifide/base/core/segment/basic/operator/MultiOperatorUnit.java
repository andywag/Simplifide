/*
 * MultiOperatorUnit.java
 *
 * Created on December 12, 2005, 8:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.operator;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author Andy Wagner
 */
public class MultiOperatorUnit extends ModuleObjectNew
{    
    private ReferenceItem opRef;

    /** Creates a new instance of MultiOperatorUnit */
    public MultiOperatorUnit() {}
    public MultiOperatorUnit(ReferenceItem opRef) {
        this.opRef = opRef;
    }
   
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        ModuleObjectWithList outList = new ModuleObjectWithList("Outputs");
        if (this.opRef != null) {
            ReferenceItem<ModuleObjectWithList> chList = this.opRef.getDependants();
            outList.addAll(chList.getObject());
        }
        return outList.createReferenceItem();
    }


    public ReferenceItem<TypeVar> getTypeReference() {
        if (opRef != null) {
            return (ReferenceItem<TypeVar>)opRef.getTypeReference();
        }
        return null;
    }

   
   

    
    
}
