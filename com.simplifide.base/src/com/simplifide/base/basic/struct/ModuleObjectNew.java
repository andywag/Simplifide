/*
 * ModuleObjectNew.java
 *
 * Created on March 12, 2007, 3:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceItemNew;
import com.simplifide.base.core.reference.ReferenceLocation;

/**
 *
 * @author Andy
 */
public class ModuleObjectNew<T extends ModuleObject> extends ModuleObject<ReferenceItem<? extends T>>{
    
    private ReferenceItem reference;
    /** Creates a new instance of ModuleObjectNew */
    public ModuleObjectNew() {}
    public ModuleObjectNew(String name) {super(name);}
    
 
    public void deleteObject() {
        this.reference = null;
    }
    
 
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item)
    {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        list.add(item);
        return list;
    }
    
    public ReferenceItem createReferenceItemWithLocation(ReferenceLocation loc) {
        ReferenceItem item = this.createReferenceItem();
        item.setLocation(loc);
        return item;
    }
    
    public ReferenceItem createReferenceItem() {
        if (reference == null) reference = new ReferenceItemNew(this);
        return reference;
    }
    
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        return new ArrayList();        
    }
    
    public List<ReferenceItem> findParenItemList(ModuleObjectFindItem item, int type) {
        return new ArrayList();        
    }
    
    /** The method is used by the subclasses which want to delete their children in the case of a null
     * object. 
     */
    public void finalizeReferenceChildren() {
    	for (int i=this.getnumber()-1;i>=0;i--) {
    		ReferenceItem item = this.getObject(i);
    		if (item.getObject() == null) {
    			if (item.finalizeObject()) this.removeObject(item);
    		}	
    	}
    }
    
    public List<T> getRealSelfList() {
    	ArrayList<T> nlist = new ArrayList<T>();
    	if (this.getGenericSelfList() == null) return nlist;
    	for (ReferenceItem<? extends T> item : this.getGenericSelfList()) {
    		if (item != null) nlist.add(item.getObject());
    	}
    	return nlist;
    }
    
    /** Method which searches the reference list based on the reference item */
    public ReferenceItem<? extends T> findReference(ReferenceItem<? extends T> ref) {
        return this.getArrayList().findObject(ref);
    }
    
    /** This is the workhorse method for adding new objects. This may look like it doesn't work properly
     *  but the add object in ReferenceItemSortedArrayList actually correctly replaces the object */
    public void addReferenceItem(ReferenceItem<? extends T> inval) {
        this.getArrayList().addObject(inval);
    }
   
    /** This method is used to find a list of valid possibilities
     * which this reference item could refer to. It's main usage is
     * for the goto operations
     **/
    public List<ReferenceItem> getRenameItemList(ReferenceItem item,ReferenceItem enclosingItem)
    {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        list.add(item);
        return list;
    }
    
  
    
   /** Find a Reference Item based on the find item and type */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        return null;
    }
    /** Handles a find operation which is based on a parenthesis operation */
    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {
        return null;
    }
   
    /** Adds a module object to the list */
    public ReferenceItem<T> addModuleObject(T object, ReferenceLocation loc) {
        return null;
    }
    
   
    /*
    public void addObject(T inval) {
        IDEout.printlnOver("Shouldn't be Here" + inval);
    }
*/

    public ReferenceItem getReference() {
        return reference;
    }

    public void setReference(ReferenceItem reference) {
        this.reference = reference;
    }
  

    protected void valueChanged(ChangeEvent event) {
        
        if (this.reference != null) this.reference.fireChange();
        super.valueChanged(event);
    }
   
    
}
