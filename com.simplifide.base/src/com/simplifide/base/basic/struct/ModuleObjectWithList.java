/*
 * ModuleObjectWithList.java
 *
 * Created on March 8, 2007, 9:12 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.struct;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.lists.ArrayListWrap;
import com.simplifide.base.basic.lists.ReferenceItemSortedArrayList;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;

/**
 *
 * @author awagner
 *
 * Class which contains a list of reference items instead of actual objects
 *
 */
public class ModuleObjectWithList<V extends ModuleObject> extends ModuleObjectNew<V>{
    
    
    
    /** Creates a new instance of ModuleObjectWithList */
    public ModuleObjectWithList() {}
    public ModuleObjectWithList(String name) {super(name);}
    
    public static ModuleObjectWithList singleton(ModuleObject obj) {
        ModuleObjectWithList nobj = new ModuleObjectWithList("SingleTon");
        nobj.addObject(obj.createReferenceItem());
     
        return nobj;
    }
    
    public void updateHdlDoc(ModuleObject parent) {
    	
    	for (V obj : this.getRealSelfList()) {
            if (obj != null) {
    		obj.updateHdlDoc(this);
            }
    	}
    }
    
    /** Deletes the object and it's children */
    public void deleteObject() {
        
        for (int i=this.getnumber()-1;i>=0;i--) {
        	ReferenceItem item = this.getObject(i);
            if (item != null) item.deleteObject();
            this.removeObject(i);
        }
        super.deleteObject();
    }
    
    /** Returne the Comparator for use on the reference list */
    protected ModuleObjectComparator getComparator() {
        
        return ReferenceItemComparator.getInstance();
    }
    
    public boolean hasNavigatorChildren() {
    	return (this.getnumber() > 0);
    }
    
    public List<ReferenceItem> getNavigatorList() {
        return this.getReferenceList().getArrayList();
    }
    
    protected ArrayListWrap createNewArrayList() {
        return new ReferenceItemSortedArrayList<ReferenceItem<? extends V>>(this.getComparator());
    }
    
    
    public void addAll(ModuleObjectWithList<V> obj) {
        for (ReferenceItem<? extends V> uitem : obj.getGenericSelfList()) {
            this.addObject(uitem);
        }
    }
    
    public ReferenceItem<ModuleObjectWithList> getAllChildOutputs() {
        ModuleObjectWithList outList = new ModuleObjectWithList("Outputs");
        for (ReferenceItem<? extends V> itemRef : this.getGenericSelfList()) {
            V item = itemRef.getObject();
            ReferenceItem<ModuleObjectWithList> depList = item.getOutputs();
            outList.addAll(depList.getObject());
        }
        return outList.createReferenceItem();
    }
    
    public ReferenceItem<ModuleObjectWithList> getAllChildDependents() {
        ModuleObjectWithList outList = new ModuleObjectWithList("Outputs");
        for (ReferenceItem<? extends V> itemRef : this.getGenericSelfList()) {
            V item = itemRef.getObject();
            ReferenceItem<ModuleObjectWithList> depList = item.getDependants();
            outList.addAll(depList.getObject());
        }
        return outList.createReferenceItem();
    }
    
    /** Method which converst the object to a reference and adds it to the list of objects */
    public ReferenceItem<V> addModuleObject(V object, ReferenceLocation loc) {
        ReferenceItem<V> uitem = object.createReferenceItem();
        uitem.setLocation(loc);
        this.addObject(uitem);
        return uitem;
    }
    
    /** Convenience method which converts the list of references to objects to a list
     *  of objects and returns it */
    public List<V> getObjectList() {
        ArrayList<V> list = new ArrayList();
        for (ReferenceItem<? extends V> object : this.getGenericSelfList()) {
            list.add(object.getObject());
        }
        return list;
    }
    
    
    public ReferenceItem findNewBaseReference(String name, int type) {
    	ReferenceItem base = new ReferenceItem(name,type);
    	ModuleObjectBaseItem it = new ModuleObjectBaseItem(name);
    	ModuleObjectIndexTop top = new ModuleObjectIndexTop(base,it);
    	return this.findBaseReference(top);
    }
    
    public ReferenceItem findBaseReference(String name, int type) {
    	ReferenceItem item = new ReferenceItem(name, type);
    	return this.findBaseReference(item);
    }
    
    public ReferenceItem findBaseReference(ReferenceItem item) {
        if (this.getReferenceList() != null) {
            return  (ReferenceItem) this.getReferenceList().findObject(item);
        }
        return null;
    }
    
    /** Find a Reference Item based on the find item and type */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
    	return this.findBaseReference(item.getBase());
       
    }
    
    /** @todo : Need to clean up types */
    public List findRealPrefixItemList(ModuleObjectFindItem item, int type) {
        ArrayList list = new ArrayList();
        List<ReferenceItem> itemList = this.findPrefixItemList(item, type);
        for (ReferenceItem nitem : itemList) {
            list.add(nitem.getObject());
        }
        return list;
        
    }
    
    /** @todo : Need to clean up types */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.getname() == null) return new ArrayList<ReferenceItem>();
        if (this.getReferenceList() == null) return new ArrayList<ReferenceItem>();
    	ReferenceItem ref = new ReferenceItem(item.getname(),type,null);
        return this.getReferenceList().findPrefixItemList(ref);
    }
       
    public ReferenceItemSortedArrayList getReferenceList() {
        return (ReferenceItemSortedArrayList) this.getArrayList();
    }
    
    /** Converts the internal list to an arraylist of real values not references */
    public ArrayList<V> getRealArrayList() {
    	ArrayList<V> outList = new ArrayList<V>();
    	ArrayList<ReferenceItem<? extends V>> baseList = this.getList();
    	for (ReferenceItem<? extends V>  item: baseList) {
    		outList.add(item.getObject());
    	}
    	return outList;
    }
    
    
}
