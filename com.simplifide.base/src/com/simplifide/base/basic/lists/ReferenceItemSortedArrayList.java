/*
 * ReferenceItemSortedList.java
 *
 * Created on March 8, 2007, 12:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.lists;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectComparator;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;

/**
 *
 * @author awagner
 */
public class ReferenceItemSortedArrayList<T extends ReferenceItem> extends SortedArrayList<T> {
    
    /** Creates a new instance of ReferenceItemSortedList */
    public ReferenceItemSortedArrayList() {}
    public ReferenceItemSortedArrayList(ModuleObjectComparator compare) {
        super(compare);
    }
    
    /** Adds the module object to the appropriate location */
    public <V extends ModuleObject> T addModuleObject(V object, ReferenceLocation location) {
        T item = (T) object.createReferenceItem();
        int spos = this.search(item);
        if (spos < 0) {
            this.addObject(-spos,item);
        } else {
            item = this.getObject(spos);
            item.changeObject(object);
        }
        item.setLocation(location);
        return item;
    }
    
     public <V extends ModuleObject> void removeModuleObject(V object) {
        T item = (T) object.createReferenceItem();
        this.removeObject(item);
     }

    public void addObject(T object) {
    	if (object == null) return;
    	T item = object;
        int spos = this.search(object);
        if (spos < 0) {
            this.addObject(-spos-1,item);
        }
        else {
            item = this.getObject(spos);
            item.changeObject(object.getObject());   
        }
       item.setLocation(object.getLocation());
    }
    
    /** Searches the list and returns the object matching the input object */
    public T findObject(T inputobject) {
        int position = this.search(inputobject);
        if (position >= 0) return this.getObject(position);
        else return null;
    }
    
   
    
    
    
    
    
}
