/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.instance;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.SearchReferenceHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;


public class Entity extends EntityHolder implements SearchReferenceHolder {
    
    /** Pointer to the context found for this object --- Used for related architectures */
    private ReferenceItem searchReference;
    /** Pointer to other fields which are stored in this object */
    private ReferenceItem<ModuleObjectWithList> decReference;    
    /** List of Data which stores all of the context related to this entity */
    private ReferenceItem context;
    
    
    public Entity(){super();}
    public Entity(String moduleName) {
        super(moduleName);
        init();
    }
    
    public List<SystemVar> getAllSignals() {
    	InstanceModule inst = (InstanceModule)this.getInstanceModRef().getObject();
    	inst.getAllVars();
    	return inst.getAllVars();
    }
    
    
    public void updateHdlDoc(ModuleObject parent) {
    	if (this.getInstanceModRef() != null) {
    		InstanceModule mod = (InstanceModule) this.getInstanceModRef().getObject();
    		if (mod.getComponentReference() != null) {
    			Component comp = (Component) mod.getComponentReference().getObject();
    			if (comp != null) {
    				comp.convertHdlDoc(this);
    			}
    		}
    	}
    	super.updateHdlDoc(parent);
    }
    
    public String getTemplateName() {return "entity";}
    
    private void init() {
        this.decReference = new ModuleObjectWithList("Declaration").createReferenceItem();
    }
    
    public int getSearchType() {return ReferenceUtilities.REF_ENTITY;}
    
    public void deleteObject() {
        this.decReference = null;
        super.deleteObject();
    }
    
    public void handleFinalization() {
        if (this.getInstanceModRef() != null) {
            if (this.getInstanceModRef().getObject() != null) {
                InstanceModule instMod = (InstanceModule) this.getInstanceModRef().getObject();
                instMod.setEntityReference(null);
            }
        }
    	
    }
    
    public void finalizeReferences() {
    	this.finalizeReferenceChildren();
    }
    
    
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
    	
        if (item.getItem().getname().equalsIgnoreCase(this.getname())) {
        	int stype = item.getBase().getSearchType();
        	if (ReferenceUtilities.checkType(stype, ReferenceUtilities.REF_ENTITY)== 0) {
            	return this.createReferenceItem();
        	}
        	else if (ReferenceUtilities.checkType(stype, ReferenceUtilities.REF_INSTANCE_MODULE_TOP) == 0) {
        		return this.getInstanceModRef();
        	}
        	else if (ReferenceUtilities.checkType(stype, ReferenceUtilities.REF_HARDWARE_MODULE) == 0) {
        		InstanceModule imod = (InstanceModule) this.getInstanceModRef().getObject();
        		return imod.getArchitectureReference();
        	}
        	else if (stype == ReferenceUtilities.REF_MODULEOBJECT){
        		return this.createReferenceItem();
        	}
        }
        ReferenceItem nitem = super.findBaseReference(item);
        if (nitem != null) return nitem;
        if (this.decReference != null) {
            return this.decReference.findBaseReference(item);
        }
        return null;
    }
    
     public ReferenceItem findSliceReference(ModuleObjectIndexTop item) {
          ReferenceItem nitem = super.findSliceReference(item);
        if (nitem != null) return nitem;
        if (this.decReference != null) {
            return this.decReference.findSliceReference(item);
        }
        return null;
     }
    
      public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
         ArrayList outList = new ArrayList();
         List nlist = super.findPrefixItemList(item,type);
         outList.addAll(nlist);
        if (this.decReference != null) {
            nlist =  this.decReference.findPrefixItemList(item,type);
            outList.addAll(nlist);
        }
        return (List) outList;
    }
      
    public boolean hasNavigatorChildren() {
    	if (super.hasNavigatorChildren()) return true;
    	if (this.decReference != null && this.getDecReference().getObject() != null && this.getDecReference().getnumber() > 0) return true;
    	return false;
    }
    public List<ReferenceItem> getNavigatorList() {
        List<ReferenceItem> list = super.getNavigatorList();
        if (this.decReference != null && this.getDecReference().getObject() != null && this.getDecReference().getnumber() > 0) {
            list.add(this.decReference);
        }
        return list;
    }
    
   
    

    
    
    
    
    public ReferenceItem getSearchReference() {
        return searchReference;
    }
    
    public void setSearchReference(ReferenceItem searchReference) {
        this.searchReference = searchReference;
    }

    public ReferenceItem<ModuleObjectWithList> getDecReference() {
        return decReference;
    }

    public void setDecReference(ReferenceItem<ModuleObjectWithList> decReference) {
        this.decReference = decReference;
    }



	public void setContext(ReferenceItem context) {
		this.context = context;
	}
	public ReferenceItem getContext() {
		return context;
	}



	private static class Declare<T extends ModuleObject> extends ModuleObjectWithList<T> {
        public Declare() {}
        public Declare(String name) {super(name);}
        
        public int getSearchType() {
            return ReferenceUtilities.REF_ENTITY_STATEMENT;
        }
    }
  

   
}
