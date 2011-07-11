/*
 * ProcessBody.java
 *
 * Created on April 4, 2007, 6:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.segment.basic.process;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy
 */
public class ProcessBody<T extends ModuleObject> extends NoSortList<T>{
    
    /** Creates a new instance of ProcessBody */
    public ProcessBody() {}
    /** Body Constructor 
     * @param name 
     */
    public ProcessBody(String name) {super(name);}

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
