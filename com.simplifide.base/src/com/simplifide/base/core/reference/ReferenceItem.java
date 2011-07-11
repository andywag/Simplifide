/*
 * ReferenceItem.java
 *
 * Created on August 6, 2006, 4:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.var.types.SubTypeVar;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author awagner
 */
public class ReferenceItem<T extends ModuleObject> extends ReferenceItemBase<ReferenceItem> {
    
    
    private T object; // Needs to be converted to weak reference
    private ReferenceLocation location;
    private boolean hidden = false; // Used to Remove from navigator display kind of a kludge
    
    /** May not be a good idea due to memory uses but is convenient */
    private T oldObject; // Used during the deletion process
    
    public ReferenceItem() {}
    /** Creates a new instance of ReferenceItem */
    public ReferenceItem(String name, int type){this(name,type,null);}
    public ReferenceItem(T object) {
        super(object.getname(),object.getSearchType());
        this.object = object;
    }
    public ReferenceItem(T object, Comparator compare) {
        super(object.getname(),object.getSearchType(), compare);
        this.object = object;
    }
    
 
    
    public ModuleObject loadObject() {
    	return this.getObject();
    }
    
    /** Method to identify whether this refernce is a shadow ref (not loaded) */
    public boolean isShadow() {return false;}
    
    /** Supported constructor but only to be used when the object is null */
    public ReferenceItem(String name, int type, T obj) {
        super(name,type);
        this.setObject(obj);
    }
    
    public String getExtraGoToInformation() {
    	if (this.object == null) {
    		return "";
    	}
    	else {
    		return this.object.getExtraGoToInformation();
    	}
    }
    
    public void setname(String name) {
    	if (this.name != null && this.name.equalsIgnoreCase("uvm_printer")) {
    		String beta;
    		beta = "aaa";
    	}
    	super.setname(name);
    }
    
    public void changeName(String name) {
    	this.setname(name);
    	if (this.object != null) this.object.setname(name);
    }
    
    public void deleteObject() {
    	if (this.object != null) {
    		this.object.deleteObject();
    	}
    	this.object = null;
    	
    }
    
   
    
    public int getSearchType() {return this.getType();}
    
    public String getCompletionName() {
        if (this.getObject() != null)
            return this.getObject().getCompletionName();
        return "";
    }
    /** Returns the Html Version of the Completion Name */
    public String getHtmlCompletionName() {
        if (this.getObject() != null)
            return this.getObject().getHtmlCompletionName();
        return "";
    }
    /** Returns the displayName for the completion type */
    public String getCompletionType() {
        if (this.getObject() != null)
            return this.getObject().getCompletionType();
        return "";
    }
    /** Returns an html version for the completion type */
    public String getHtmlCompletionType() {
         if (this.getObject() != null)
            return this.getObject().getHtmlCompletionType();
         return "";
    }
    /** Returns an html version of the completion name */
    public String getHtmlDisplayName() {
        if (this.getObject() != null)
            return this.getObject().getHtmlDisplayName();
        return "";
    }
    
    public ReferenceItem<ModuleObjectWithList> getOutputs() {
        if (this.object != null) {
            return this.object.getOutputs();
        }
        return null;
    }

    public ReferenceItem<ModuleObjectWithList> getDependants() {
        if (this.object != null) {
            return this.object.getDependants();
        }
        return null;
    }

    
    public void finalizeReference() {
    	
    }
    
   
    
    
    /** Removes the object from the reference item, but stores the old version of the object
     * to be deleted at a later time by finalizeObject */
     
    public void clearObject() {
        this.oldObject = object;
        this.object = null;
    }
    
    /** Handles deleting this object given the current object is null */
    public boolean finalizeObject() {
    	if (this.object == null) {
    		if (this.oldObject != null) {
    			this.oldObject.handleFinalization();
    			this.oldObject = null;
    		}
    		return true;
    	}
    	else {
    		this.oldObject = null;
    		this.object.finalizeReference();
    	}
    	return false;
    }
    
    
    /** Checks to see if this usage corresponds to the correct reference item */
    public ReferenceItem checkUsage(ReferenceItem item) {
    	
        if (item != null && this.getLocation() != null && item.getLocation() != null) {
            if (this.getLocation().compareLocation(item.getLocation())) return item;
        }
        else if (item.getObject() instanceof InstanceModule ||
        		 item.getObject() instanceof FunctionHolder ||
        		 item.getObject() instanceof ClassInstanceModule ||
        		 item.getObject() instanceof ClassModule ||
        		 item.getObject() instanceof SubTypeVar) { // Special Case for Sub Calls
        	return item;
        }
        return null;
    }
    
    
    
/** Returns a list of items which is used for rename refactorign and similar */
    
    public List<ReferenceItem> findRenameList(ReferenceItem enclosingItem) {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        list.add(this);
        return list;
    }
    
    public List<ReferenceItem> findHyperlinksList() {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        list.add(this);
        return list;
    }
    
    /** Method which returns the type of this object. Used for creating type mismatch errors */
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        if (this.object != null) {
        	return this.object.getTypeReference();
        }
        return null;
    }
    
    public ReferenceItem findReference(String str, int c) {
        ModuleObjectBaseItem item = new ModuleObjectBaseItem(str);
        return item.findRealReferenceItem(this,c);
    }
    
   
    
    /** This method handles finding operators which contain parenthesis */
    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {
        return null;
    }
    /** This is a redirect from the classes above to find the reference and allow the object iteration to be performed
     *  in the moduleobject find classes */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop top) {
        //return this.findReference(top.getItem(),top.getBase().getType());
        return null;
    }
    
    /** Returns a True List of Object (No References) */
    public ArrayList findPrefixList(String str, int type) {
    	List<? extends ReferenceItem> refList = this.findPrefixItemList(str, type);
    	ArrayList outlist = new ArrayList();
    	for (ReferenceItem ref : refList) {
    		outlist.add(ref.getObject());
    	}
    	return outlist;
    }
    
    /** Convenience Method for findPrefixItemList with ModuleObjectFindItem */
    public List<? extends ReferenceItem> findPrefixItemList(String str, int type) {
        ModuleObjectBaseItem base = new ModuleObjectBaseItem(str);
        return this.findPrefixItemList(base, type);
    }
    
    /** Returns a list of items corresponding to the prefix in the ModuleObjectFindItem */
    public List<ReferenceItem> findParenItemList(ModuleObjectFindItem item, int type) {
        ArrayList<ReferenceItem> ulist = new ArrayList();
        return ulist;
    }
    
    /** Returns a list of items corresponding to the prefix in the ModuleObjectFindItem */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        ArrayList<ReferenceItem> ulist = new ArrayList();
        return ulist;
    }
    
    /** This is a workhorse method which is used to add references to the item */
    public void addReferenceItem(ReferenceItem item){}
    
    
    /** Generified Version of add Reference Item
     *
     */
    public <V extends ModuleObject> ReferenceItem<V> addModuleObject(V obj, ReferenceLocation loc) {
        return null;
    }
    
    
    
    
    public void changeObject(T obj) {
        this.object = obj;
    }
    
    public String debugString() {
        String ustr = this.getname() + "(" + ReferenceUtilities.referenceString(this.getType()) + ")";
        if (this.getObject() != null) ustr += this.getObject().getClass();
        return ustr;
    }
    
    
    
    
    
    public boolean hasObject() {
    	if (this.object == null) return false;
    	return true;
    }
    
   
    
    public T getObject() {
        return object;
    }
    
    public void setObject(T object) {
        this.object = object;
    }
    
    public ReferenceLocation getLocation() {
        return location;
    }
    
    public void setLocation(ReferenceLocation location) {
        this.location = location;
    }
    
    public boolean isHidden() {return hidden;}
    public void setHidden(boolean hidden) {this.hidden = hidden;}
    
    
    
    
    
    
    
}
