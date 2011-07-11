/*
 * SequenceStatements.java
 *
 * Created on April 4, 2007, 7:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.assign;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @param T 
 * @author Andy
 */
public class SequenceStatements<T extends ModuleObject> extends NoSortList<T> {
    
    /** Creates a new instance of SequenceStatements */
    public SequenceStatements() {}
    /** Constructor 
     * @param name 
     */
    public SequenceStatements(String name) {
        super(name);
    }

     public ReferenceItem getOutputs() {
        ModuleObjectWithList nlist = new ModuleObjectWithList("OutList");
        for (ReferenceItem<? extends T> obj : this.getGenericSelfList()) {
            ReferenceItem<ModuleObjectWithList> list = obj.getOutputs();
            nlist.addAll(list.getObject());
        }
        return nlist.createReferenceItem();
    }

    public ReferenceItem getDependants() {
        ModuleObjectWithList nlist = new ModuleObjectWithList("OutList");
        for (ReferenceItem<? extends T> obj : this.getGenericSelfList()) {
            ReferenceItem<ModuleObjectWithList> list = obj.getDependants();
            nlist.addAll(list.getObject());
        }
        return nlist.createReferenceItem();
    }

    
}
