package com.simplifide.base.basic.struct;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.newfunction.FunctionHolder;
import com.simplifide.base.core.newfunction.InstanceFunction;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class ModuleObjectContextHolder extends ModuleObjectWithList<ModuleObject>{
	  /** Pointer to the Suite Project */
    private ReferenceItem<? extends CoreProjectSuite> suiteReference;
    /** Pointer to a list of functions stored in context */
    private ReferenceItem<ModuleObjectWithList> functionReference;
    /** Pointer to a list of enumeration variables which are stored in context */
    private ReferenceItem<ModuleObjectWithList> enumReference;
    /** Reference to a Standard Set of Functions */
    private ReferenceItem standardReference;
    /** Internal References */
    private ModuleObjectWithList<ModuleObject> internal;
    
    /** Creates a new instance of ModuleObjectContextHolder */
    public ModuleObjectContextHolder() {
    	this("",null);
    }
    public ModuleObjectContextHolder(String name, ReferenceItem<? extends CoreProjectSuite> suiteRef) {
        super(name);
        this.suiteReference = suiteRef;
        this.functionReference = new ModuleObjectWithList("FunctionHolder").createReferenceItem();
        this.enumReference = new ModuleObjectWithList("EnumHolder").createReferenceItem();
        this.setInternal(new ModuleObjectWithList("Internal"));
    }
    
    public ModuleObjectContextHolder copy() {
    	ModuleObjectContextHolder holder = new ModuleObjectContextHolder(this.getname(),this.getSuiteReference());
    	holder.setFunctionReference(this.getFunctionReference());
    	holder.setEnumReference(this.getEnumReference());
    	holder.setStandardReference(this.getStandardReference());
    	ModuleObjectWithList nint = new ModuleObjectWithList("internal");
    	for (ReferenceItem refa : internal.getGenericSelfList()) {
    		nint.addObject(refa);
    	}
    	holder.setInternal(nint);
    	return holder;
    }
    
    
   
    
    
    public void deleteObject() {
        this.functionReference = null;
        this.enumReference = null;
    }
    
    /** Add all of the values to the internal list to aid in searching time
     * 
     */
    public void appendObject(ReferenceItem ref) {
    	if (ref == null) return;
    	if (!(ref.getname().equalsIgnoreCase("internal"))) {
    		this.addObject(ref);
    	}
    	
    	// This is probably bad on multiple levels. This creates issue for completion at least
    	this.internal.addObject(ref);
    	ModuleObjectNew obj = (ModuleObjectNew) ref.getObject();
    	List<ReferenceItem> refList =obj.findPrefixItemList(new ModuleObjectBaseItem(""), ReferenceUtilities.REF_MODULEOBJECT);
    	for (ReferenceItem nref : refList) {
    		/*if (nref.getObject() instanceof FunctionHolder) {
    			this.appendObject(nref);
    		}
    		else {*/
    			this.getInternal().addReferenceItem(nref);
    		//}
    		
    	}
    }
    
    public void appendFunction(ReferenceItem item) {
        List<? extends ReferenceItem<FunctionHolder<InstanceFunction>>> holdList = item.findPrefixItemList("",ReferenceUtilities.REF_FUNCTION_HOLDER);
        for (ReferenceItem<FunctionHolder<InstanceFunction>> hold : holdList) {
            ModuleObjectBaseItem baseItem = new ModuleObjectBaseItem(hold.getname());
            FunctionHolder nfunc = new FunctionHolder(hold.getname());
            ReferenceItem nRef = baseItem.findRealReferenceItem(this.functionReference,ReferenceUtilities.REF_FUNCTION_HOLDER);
            if (nRef == null) {
                nRef = nfunc.createReferenceItem();
                functionReference.addReferenceItem(nRef);
            } 
            for (ReferenceItem inst : hold.getObject().getGenericSelfList()) {
                nRef.addReferenceItem(inst);
            }
        }
    }
    
     /** @todo : Need to clean up types */
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
    	List<ReferenceItem> items = this.getInternal().findPrefixItemList(item, type);
    	List<ReferenceItem> nitems = new ArrayList<ReferenceItem>();
    	for (ReferenceItem ref : items) {
    		if (ref.getType() != ReferenceUtilities.REF_ENTITY &&
    			ref.getType() != ReferenceUtilities.REF_HARDWARE_MODULE &&
    			ref.getType() != ReferenceUtilities.REF_PACKAGE_MODULE &&
    			ref.getType() != ReferenceUtilities.REF_PACKAGE_MODULE_BODY &&
    			!(ref.getname().equalsIgnoreCase("internal") &&
    			!(ref.getname().startsWith("Context")))) {
    			nitems.add(ref);
    		}
    	}
    	
    	/*
        ArrayList< ReferenceItem> newList = new ArrayList();
        ArrayList<ReferenceItem> list = this.getReferenceList().getArrayList();
        for (ReferenceItem refItem : list) {
            List<ReferenceItem> ulist = refItem.findPrefixItemList(item,type);
            for (ReferenceItem addItem : ulist) {
            	if (addItem.getType() != ReferenceUtilities.REF_FUNCTION_HOLDER) {
            		newList.add(addItem);
            	}
            }
        }*/
        nitems.addAll(this.functionReference.findPrefixItemList(item,type));
        return nitems;
    }
    
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        
        
        
        // Searches through the functions first
        ReferenceItem aitem = this.functionReference.findBaseReference(item);
        if (aitem != null) return aitem;
        // Searches through the list of enums next
        aitem = this.enumReference.findBaseReference(item);
        if (aitem != null) return aitem;
        
        // Searches through it's list of references
        aitem = this.getInternal().findBaseReference(item);
        if (aitem != null) return aitem;
         // Checks Standard Items in Suite Reference
         if (this.getSuiteReference() != null)
         {
             ReferenceItem packRef = this.standardReference;
             if (packRef != null) {
                 ReferenceItem uitem = packRef.findBaseReference(item);
                 if (uitem != null) return uitem;
             }
         }
         
         // Looks for References in Suite
         if (this.getSuiteReference() != null)
         {
             ReferenceItem uitem = this.getSuiteReference().findBaseReference(item);
             if (uitem != null) return uitem;
         }
         return null;
    }

    public ReferenceItem<? extends CoreProjectSuite> getSuiteReference() {
        return suiteReference;
    }

    public void setSuiteReference(ReferenceItem<? extends CoreProjectSuite> suiteReference) {
        this.suiteReference = suiteReference;
    }

    public ReferenceItem<ModuleObjectWithList> getFunctionReference() {
        return functionReference;
    }

    public void setFunctionReference(ReferenceItem<ModuleObjectWithList> functionReference) {
        this.functionReference = functionReference;
    }

    public ReferenceItem<ModuleObjectWithList> getEnumReference() {
        return enumReference;
    }

    public void setEnumReference(ReferenceItem<ModuleObjectWithList> enumReference) {
        this.enumReference = enumReference;
    }
	public void setStandardReference(ReferenceItem standardReference) {
		this.standardReference = standardReference;
	}
	public ReferenceItem getStandardReference() {
		return standardReference;
	}
	public void setInternal(ModuleObjectWithList internal) {
		this.internal = internal;
	}
	public ModuleObjectWithList getInternal() {
		return internal;
	}

    
    
    
}
