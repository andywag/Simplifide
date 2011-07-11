/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.module;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;



public class HardwareModule<T extends ModuleObject> extends TopModule<T>  {
    
	private static final long serialVersionUID = -6140098489459299990L;

	/** Pointer to Instance Module */
    private ReferenceItem<InstanceModule> instModRef;
    
    private ReferenceLocation lastSignalLocation;
    private ReferenceLocation declarationStartLocation;
    private ReferenceLocation declarationEndLocation;

    /** List of all places where io and parameters are initialize. Used to convert to ansi ports for verilog */
    private ArrayList<ReferenceLocation> ioDeclarationList = new ArrayList<ReferenceLocation>();
    
    private String archName;
    
    public HardwareModule() {super(); }
    public HardwareModule(String name) {super(name);}
    public HardwareModule(String modName, String archName, ReferenceItem<InstanceModule> iref) {
        super(modName);
        this.setArchName(archName);
        this.instModRef = iref;
    }
    
    public ModuleObject getBaseSearchClass() {
    	return this.instModRef.getObject();
    }
    
    public void deleteObject() {
        this.instModRef = null;
        super.deleteObject();
    }
    
    /* Returns a list of module instantiations for this module */
    public List<ReferenceItem> getInstantiations() {
    	List<ReferenceItem> refList =(List<ReferenceItem>) this.findPrefixItemList(new ModuleObjectBaseItem(""), ReferenceUtilities.REF_MODINSTANCE_CONNECT);
    	return refList;
    }

    
    
    public void handleFinalization() {
        if (this.getInstModRef() != null) {
            if (this.getInstModRef().getObject() != null) {
                InstanceModule instMod = (InstanceModule) this.getInstModRef().getObject();
                instMod.cleanConnector();
            }
        }
    	
    }
    
    
    
    public void cleanObject() {
        ArrayList<ReferenceItem<? extends T>> ulist = this.getGenericSelfList();
        for (int i=ulist.size()-1;i>=0;i--) {
            ReferenceItem item = ulist.get(i);
            // Changed from Return to continue
            if (item.getType() == ReferenceUtilities.REF_MODINSTANCE_CONNECT) { 
                continue;
            }
            else if (item.getType() == ReferenceUtilities.REF_GENERATE_STATEMENT) {
                item.getObject().cleanObject();
            }
            else {
                this.removeObject(item);
            }
        }
       
    }
    
    
    
   
    
    public int getSearchType() {return ReferenceUtilities.REF_HARDWARE_MODULE;}
    
   
    
    public String getDisplayName() {
    	String o = this.getname();
    	if (this.getArchName() != null && !this.getArchName().equalsIgnoreCase("null")) {
    		o = o +  StringOps.addParens(this.getArchName());
    	}
        return o;
    }
    
   
    
    public String getArchName() {
        return archName;
    }
    
    public void setArchName(String archName) {
        this.archName = archName;
    }
    
    public ReferenceItem<InstanceModule> getInstModRef() {
        return instModRef;
    }
    
    public void setInstModRef(ReferenceItem<InstanceModule> instModRef) {
        this.instModRef = instModRef;
    }
	public void setLastSignalLocation(ReferenceLocation lastSignalLocation) {
		this.lastSignalLocation = lastSignalLocation;
	}
	public ReferenceLocation getLastSignalLocation() {
		return lastSignalLocation;
	}
	public void setIoDeclarationList(ArrayList<ReferenceLocation> ioDeclarationList) {
		this.ioDeclarationList = ioDeclarationList;
	}
	public ArrayList<ReferenceLocation> getIoDeclarationList() {
		return ioDeclarationList;
	}
	public void setDeclarationStartLocation(ReferenceLocation declarationStartLocation) {
		this.declarationStartLocation = declarationStartLocation;
	}
	public ReferenceLocation getDeclarationStartLocation() {
		return declarationStartLocation;
	}
	public void setDeclarationEndLocation(ReferenceLocation declarationEndLocation) {
		this.declarationEndLocation = declarationEndLocation;
	}
	public ReferenceLocation getDeclarationEndLocation() {
		return declarationEndLocation;
	}

   
    
    
    
    
    
    
    
    
    
}
