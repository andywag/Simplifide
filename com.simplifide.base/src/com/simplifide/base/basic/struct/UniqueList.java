/*
 * ModuleObjectNoSortUniqueList.java
 *
 * Created on March 22, 2007, 3:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

import com.simplifide.base.core.reference.ReferenceItem;

/**
 *
 * @author Andy
 *
 * List implementation which does not allow repeated copies of the object
 */
public class UniqueList<T extends ModuleObject> extends NoSortList<T> {
    
    /** Creates a new instance of ModuleObjectNoSortUniqueList */
    public UniqueList() {}
    
    

    public void addUniqueObject(ReferenceItem<? extends T> inval) {
    	if (inval == null) return;
        boolean add = true;
        for (ReferenceItem<? extends T> item : this.getGenericSelfList()) {
            if (item.getObject() == inval.getObject()) {
                add = false;
            }
        }
        if (add) super.addObject(inval);
    }    
    
    public void addObject(ReferenceItem<? extends T> inval) {
        if (inval == null) return;
    	boolean add = true;
        for (ReferenceItem<? extends T> item : this.getGenericSelfList()) {
            if (item.getname().equalsIgnoreCase(inval.getname())) {
            	return;
            }
        }
        if (add) super.addObject(inval);
    }    
    
    public T getNamedObject(String inName) {
    	for (ReferenceItem<? extends T> item : this.getGenericSelfList()) {
            if (item.getname().equalsIgnoreCase(inName)) {
            	return item.getObject();
            }
        }
    	return null;
    }
    
}
