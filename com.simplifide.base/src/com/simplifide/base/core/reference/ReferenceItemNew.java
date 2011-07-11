/*
 * ReferenceItemNew.java
 *
 * Created on March 12, 2007, 1:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;

/**
 *
 * @author Andy
 */
public class ReferenceItemNew <T extends ModuleObjectNew> extends ReferenceItem<T> {
    
    /** Creates a new instance of ReferenceItemNew */
    
    public ReferenceItemNew(T object) {
        super(object);
        object.setReference(this);
    }
    public ReferenceItemNew(T object, Comparator compare) {
        super(object,compare);
        object.setReference(this);
    }
    
    public ReferenceItemNew(String name, int type, ReferenceLocation loc) {
    	this.setname(name);
    	this.setType(type);
    	this.setLocation(loc);
    }
    
  
     /** Deletes the object and it's children */
    public void deleteObject() {
        if (this.getObject() != null) {
            this.getObject().deleteObject();
            this.setObject(null);
        }
        super.deleteObject();
        
    }
    
    
    
    /** Changes the object */
    /*
    public void changeObject(T obj) {
        if (this.getObject() != null) this.getObject().setReference(null);
        obj.setReference(this);
    }*/
    
    public boolean hasNavigatorChildren() {
    	if (this.getObject() != null) {
    		return this.getObject().hasNavigatorChildren();
    	}
    	return false;
    }
   /** Returns a list of objects for the navigator */
    public List<ReferenceItem> getNavigatorList() {
        if (this.getObject() != null) {
            return this.getObject().getNavigatorList();
        }
        return new ArrayList<ReferenceItem>();
    }
    /** Overrides the base class to properly add the reference to the object */
    public void addReferenceItem(ReferenceItem item) {
        if (this.getObject() != null) {
            this.getObject().addReferenceItem(item);
        }
    }
    
    public List<ReferenceItem> findParenItemList(ModuleObjectFindItem item, int type) {
        if (this.getObject() != null) {
            return this.getObject().findParenItemList(item,type);
        }
        ArrayList<ReferenceItem> ulist = new ArrayList();
        return ulist;
    }
    
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.getObject() != null) {
            return this.getObject().findPrefixItemList(item,type);
        }
        ArrayList<ReferenceItem> ulist = new ArrayList();
        return ulist;
    }

    /** This is a redirect from the classes above to find the reference and allow the object iteration to be performed
     *  in the moduleobject find classes */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop top) {
        if (this.getObject() != null){
            return this.getObject().findBaseReference(top);
        }
        return null;
    }
    
    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {
        if (this.getObject() != null) {
            return this.getObject().findSliceReference(top);
        }
        return null;
    }
   
    
    public <V extends ModuleObject> ReferenceItem<V> addModuleObject(V obj, ReferenceLocation loc) {
        if (this.getObject() != null) {
            return this.getObject().addModuleObject(obj,loc);
        }
        return null;
    }
    
/** Returns a list of items which is used for rename refactorign and similar */
    
    public List<ReferenceItem> findRenameList(ReferenceItem enclosingItem) {
    	if (this.getObject() != null) {
            return this.getObject().getRenameItemList(this,enclosingItem);
        }
        return new ArrayList();
    }
    
    public List<ReferenceItem> findHyperlinksList() {
        if (this.getObject() != null) {
            return this.getObject().getHyperlinkItemList(this);
        }
        return new ArrayList();
    }
    
    
    
   
    
    

    public String getDisplayName() {
        if (this.getObject() != null) {
            return this.getObject().getDisplayName();
        }
        return super.getDisplayName();
        
    }
    
    
}
