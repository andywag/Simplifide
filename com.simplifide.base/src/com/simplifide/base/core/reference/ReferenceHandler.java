/*
 * ReferenceHandler.java
 *
 * Created on August 7, 2006, 4:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.reference;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.project.CoreProjectSuite;

/**
 *
 * @author awagner
 * 
 * This class is used for searching the projects for usages. Most usages are based on 
 * references rather than direct links for storage reasons. 
 */
public class ReferenceHandler 
{
    
    private ReferenceItem<? extends CoreProjectSuite> globalReference;
    private ReferenceItem<SuperModule> superModuleReference;
    private ReferenceItem projectReference;
    private ReferenceItem searchReference;
    private ReferenceItem activeReference;
    
    private ReferenceItem secondaryReference;
    private ReferenceItem localReference;
    
    private ReferenceItem<Entity> entityReference;
    private ReferenceItem<HardwareModule> moduleReference;
    
    // Contains the current package reference
    private ReferenceItem<PackageModule> fileReference; // Only Used for Verilog
    
    /** Creates a new instance of ReferenceHandler */
    public ReferenceHandler() {
        init();   
    }

    /*
    public ReferenceHandler(ReferenceItem globalReference)
    {
        this.globalReference = globalReference;
        init();
    }*/
    
    private void init()
    {
      
        //this.setSearchReference(new ReferenceItemWithList("search",0,null));
    }
    
    public String toString()
    {
        return "Global : " + this.globalReference + " : " + "Search : " + this.searchReference + " Active : " + this.activeReference
                + "Secondary : " + this.secondaryReference + " Module : " + this.moduleReference;
                
    }
    
   /** This method finds the appropriate reference based on the type of object which is requested 
    *  this involves decoding the find item. Unlike the previous method this will allow return types of 
    *  null */
   
   
    
    /** Search from the suite project for things top level objects. Added a case to handle work which 
     *  is a common project name. I don't know that this is a good idea or even the correct way to do this, 
     *  but ...*/
    public ReferenceItem findGlobalObject(ModuleObjectFindItem item, int type)
    {
        ReferenceItem uitem = null;
        if (item.getname().equalsIgnoreCase("Work")) {
            uitem = this.getProjectReference();
            if (uitem != null && item.getNext() != null) {
                return this.findReference(uitem,item.getNext(),type);
            }
                
        }
        else {
             uitem = this.findReference(globalReference,item,type);  
        }
        return uitem; 
    }
    
    
    public ReferenceItem findProjectAndLibraryObject(ModuleObjectFindItem item, int type) {
    	ReferenceItem projItem = this.findProjectObject(item, type);
    	if (projItem != null) return projItem;
    	
    	return this.globalReference.getObject().findLibraryReference(item,type);
    }
    
    /** Search from the project reference to find objects */
     public ReferenceItem findProjectObject(ModuleObjectFindItem item, int type)
    {
        ReferenceItem uitem = this.findReference(this.projectReference,item,type);
        return uitem; 
    } 
    
    public ReferenceItem findActiveObject(ModuleObjectFindItem item, int type)
    {
        ReferenceItem uitem = this.findReference(this.activeReference,item,type);
        return uitem; 
    }
       
    
    public ReferenceItem findHardwareModuleObject(ModuleObjectFindItem item, int type) {
    	ReferenceItem modR = this.getModuleReference();
    	return this.findReference(modR, item, type);
    }
    /** Search Blocks in Context for any objects. */
    public ReferenceItem findContextObject(ModuleObjectFindItem item, int type)
    {
    	ReferenceItem localReference = this.findReference(this.getActiveReference(), item, type);
        if (localReference != null) return localReference;
    	localReference = this.findReference(this.getLocalReference(),item,type);
        if (localReference != null) return localReference;
        if (this.fileReference != null) {
        	ReferenceItem fileRef = this.findReference(this.fileReference, item, type);
        	if (fileRef != null) return fileRef;
        }
        if (this.globalReference != null) {
        	localReference = this.findReference(this.globalReference, item, type);
        	if (localReference != null) return localReference;
        }
        if (this.projectReference != null) {
        	localReference = this.findReference(this.projectReference, item, type);
        	if (localReference != null) return localReference;
        }
        localReference = this.findReference(this.getSearchReference(), item, type);
        if (localReference != null) return localReference;
        if (this.globalReference != null) {
        	CoreProjectSuite suite = this.globalReference.getObject();
        	localReference = suite.findProjectOrLibaryReference(item, type);
        	if (localReference != null) return localReference;
        }
        return null;
        
        /* End Disable for Generic Reference Fixes */
        
        //return localReference;
    }
    
    /** Search Based only on local reference ignoring context : Mainly used for internal blocks */
    public ReferenceItem findLocalObject(ModuleObjectFindItem item, int type)
    {
    	ReferenceItem ref = this.findReference(this.getActiveReference(), item, type);
    	if (ref == null) ref =  this.findReference(this.returnLocalReference(),item,type);
    	return ref;
    }
     
    /** Breaks the find reference down to its final search value */
    public ReferenceItem findReference(ReferenceItem ref, ModuleObjectFindItem item, int type)
    {
    	if (item == null) return null;
        ReferenceItem nitem = item.findRealReferenceItem(ref,type);
        return nitem;
    }
   

   
    
    public ReferenceItem returnLocalReference()
    {
        if (this.getLocalReference() == null) return this.getActiveReference();
        else return this.getLocalReference();
        /*
        ReferenceComposite comp = new ReferenceComposite("Composite",0);
        comp.addObject(this.getActiveReference());
        if (this.getSecondaryReference() != null) comp.addObject(this.getSecondaryReference());
        return comp;
         */
    }
    
    /** Decodes if value is fixed reference */
    public ReferenceItem getFixedReference(ModuleObjectFindItem prefix)
    {
        ReferenceItem uitem = null;
        if (prefix != null && prefix.getname() != null) {
            if (prefix.getname().equals("root")) uitem = this.getGlobalReference(); 
            else if (prefix.getname().equals("this")) {
            	ReferenceItem locR = this.getLocalReference();
            	if (locR != null) {
            		return ModuleObjectCompositeHolder.dualHolder("", getActiveReference(), locR).createReferenceItem();
            	}
            	return getActiveReference();
            }
            else if (prefix.getname().equals("work")) uitem = this.getProjectReference();
        }

        

        return uitem;
    }
    
   
    
    
    public List<ReferenceItem> findReferenceListNew(ModuleObjectFindItem item, int type)
    {
        ArrayList<ReferenceItem> list = new ArrayList<ReferenceItem>();
        return list;
    }
    
    
    
    
    /** Finds A single object in the reference List Used for searching 
     *  1. Search for constant expresssions
     *  2. Search Global Context for Project
     *  3. Search Context Expressions
     * 
     */
    public ReferenceItem findContextObjectWithFixed(ModuleObjectFindItem prefix)
    {
        ReferenceItem uitem = this.getFixedReference(prefix);
        if (uitem != null)
        {
            if (prefix.getNext() != null) uitem = this.findReference(uitem,prefix.getNext(),ReferenceUtilities.REF_MODULEOBJECT);
        }
        else
        {
            uitem = this.findContextObject(prefix,ReferenceUtilities.REF_MODULEOBJECT);
        }
        return uitem;
    }
       
    public String getDebugString()
    {
        
        String ustr = "Global : " + this.getGlobalReference().debugString() + "\n";
        ustr += "Context : " + this.getSearchReference().debugString() + "\n";
        ustr += "Local : " + this.getActiveReference().debugString() +"\n";
        return ustr;
    }
    
    
    public ReferenceItem getLocalReference() {return this.localReference;}
    public void setLocalReference(ReferenceItem localReference) {this.localReference = localReference;}
    
    public ReferenceItem<? extends CoreProjectSuite> getGlobalReference() {
        return globalReference;
    }

    public void setGlobalReference(ReferenceItem<? extends CoreProjectSuite> globalReference) {
        this.globalReference = globalReference;
    }
    
    public ReferenceItem getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(ReferenceItem globalReference) {
        this.projectReference = globalReference;
    }

    public ReferenceItem getSearchReference() {
        return searchReference;
    }

    public void setSearchReference(ReferenceItem searchReference) {
        this.searchReference = searchReference;
    }

    public ReferenceItem getActiveReference() {
        return activeReference;
    }

    public void setActiveReference(ReferenceItem activeReference) {
        this.activeReference = activeReference;
    }

    public ReferenceItem getSecondaryReference() {
        return secondaryReference;
    }

    public void setSecondaryReference(ReferenceItem secondaryReference) {
        this.secondaryReference = secondaryReference;
    }

    public ReferenceItem<HardwareModule> getModuleReference() {
        return moduleReference;
    }

    public void setModuleReference(ReferenceItem<HardwareModule> moduleReference) {
        this.moduleReference = moduleReference;
    }

	public void setSuperModuleReference(ReferenceItem<SuperModule> superModuleReference) {
		this.superModuleReference = superModuleReference;
	}

	public ReferenceItem<SuperModule> getSuperModuleReference() {
		return superModuleReference;
	}

	public void setFileReference(ReferenceItem<PackageModule> fileReference) {
		this.fileReference = fileReference;
	}

	public ReferenceItem<PackageModule> getFileReference() {
		return fileReference;
	}

	public void setEntityReference(ReferenceItem<Entity> entityReference) {
		this.entityReference = entityReference;
	}

	public ReferenceItem<Entity> getEntityReference() {
		return entityReference;
	}

   
    
}
