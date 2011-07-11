/*
 * InstanceModule.java
 *
 * Created on January 19, 2007, 5:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.module;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author awagner
 */
public class InstancePackage<T extends ModuleObject> extends InstanceModuleTop<T>{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReferenceItem<PackageModule> packageModuleReference;
    private ReferenceItem<PackageModuleBody> packageBodyReference;
    
    public static InstancePackage BASE_INSTANCE_PACKAGE = new InstancePackage("",null);
    
    /** Creates a new instance of InstanceModule */
    public InstancePackage() {}
   
    public InstancePackage(String name, ReferenceItem<CoreProjectBasic> basic) {
    	super(name,basic);
    }
    
    public PackageModule getPackageModule() {
        if (this.packageModuleReference != null) {
            return this.packageModuleReference.getObject();
        }
        return null;
    }
    
    /** Location of Base Template Location for Generating Documentation */
    public String getBaseDocTemplateFolder() {
    	return "vhdl";
    }
    
    private void finalizeObject() {
    	if (this.packageBodyReference == null && this.packageModuleReference == null) {
    		this.getProjectRef().getObject().removeObject(this.createReferenceItem());
    	}
    }
    
    /** Remove the package references if they are shadows */
    public void clearShadowReferences() {
    	/*
    	if (this.packageBodyReference != null && this.packageBodyReference.isShadow()) {
    		this.packageBodyReference = null;
    	}
    	if (this.packageModuleReference != null && this.packageModuleReference.isShadow()) {
    		this.packageModuleReference = null;
    	}*/
    }
    
    /** Remove the entity from this object */
    public void removePackage() {
    	this.packageModuleReference = null;
    	this.finalizeObject();
    }
    /** Remove the architecture from this object */
    public void removePackageBody() {
    	this.packageBodyReference = null;
    	this.finalizeObject();
    }
    
     public Object getTemplateObject() {
        if (this.packageModuleReference != null)
            return this.packageModuleReference.getObject();
        return null;
    }
    
    public int getSearchType(){return ReferenceUtilities.REF_INSTANCE_PACKAGE;}
    //public ReferenceItem createReferenceItem() {return new InstancePackageReferenceItem(this);}

    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.getPackageModuleReference() != null) return this.getPackageModuleReference().findPrefixItemList(item, type);
        else return new ArrayList<ReferenceItem>();
    }
 
    /** Not quite sure about this operation now. It has to do with the way the context search works. I don't know if it
     *  will be required to have a context search and a non-context search */
    public ReferenceItem findBaseReference(ModuleObjectIndexTop index) {
        int type = index.getBase().getType();
        ModuleObjectFindItem item = index.getItem();
        if (item.getname().equalsIgnoreCase(this.getname())) {
            //if (ReferenceUtilities.checkType(ReferenceUtilities.REF_INSTANCE_PACKAGE,type) == 0) return this;
            if (type == ReferenceUtilities.REF_PACKAGE_MODULE) return (ReferenceItem) this.getPackageModuleReference();
            else if (type == ReferenceUtilities.REF_PACKAGE_MODULE_BODY) return (ReferenceItem) this.getPackageBodyReference();
        }   
        // This may be another hack to get the path extensions to work properly
        
        return this.getPackageModuleReference().findBaseReference(index);
        
        
    }
    
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item) {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        if (this.getPackageModuleReference() != null) list.add(this.getPackageModuleReference());
        if (this.getPackageBodyReference() != null) list.add(this.getPackageBodyReference());
        return list;
    }
    
    public ReferenceItem<T> addModuleObject(T obj, ReferenceLocation loc)  {
        int type = obj.getSearchType();
        if (type == ReferenceUtilities.REF_PACKAGE_MODULE) {
            if (this.packageModuleReference == null) this.packageModuleReference = obj.createReferenceItem();
            else this.packageModuleReference.changeObject((PackageModule)obj);
            this.packageModuleReference.setLocation(loc);
            return (ReferenceItem<T>) this.getPackageModuleReference();
        } else if (type == ReferenceUtilities.REF_PACKAGE_MODULE_BODY) {
            if (this.packageBodyReference == null) this.packageBodyReference = obj.createReferenceItem();
            else this.packageBodyReference.changeObject((PackageModuleBody)obj);
            this.packageBodyReference.setLocation(loc);
            return (ReferenceItem<T>) this.getPackageBodyReference();
        }
        return null;
    }
    
    /** @todo : I am not 100% sure of this method as it is not checking if the module or body is 
     /*  equivalent to the one stored 
    public void removeObject(ReferenceItem<? extends T> object) {
        if (object.getType() == ReferenceUtilities.REF_PACKAGE_MODULE) {
            this.packageModuleReference = null;
        }
        else if (object.getType() == ReferenceUtilities.REF_PACKAGE_MODULE_BODY) {
            this.packageBodyReference = null;
        }
        if (this.packageBodyReference == null && this.packageModuleReference == null) {
            this.deleteObject();
        }
    }*/
    
    public ReferenceItem<PackageModule> getPackageModuleReference() {
        return packageModuleReference;
    }

    public void setPackageModuleReference(ReferenceItem<PackageModule> packageModuleReference) {
        this.packageModuleReference = packageModuleReference;
    }

    public ReferenceItem<PackageModuleBody> getPackageBodyReference() {
        return packageBodyReference;
    }

    public void setPackageBodyReference(ReferenceItem<PackageModuleBody> packageBodyReference) {
        this.packageBodyReference = packageBodyReference;
    }
    
}
