/*
 * PackageModule.java
 *
 * Created on December 2, 2005, 2:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.module;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author Andy Wagner
 */
public class PackageModule extends TopModule {
    
    private ReferenceItem searchReference;
    private ReferenceItem<InstancePackage> instancePackage;
	
    private ReferenceLocation lastObjectLocation;
    
    /** Creates a new instance of PackageModule */
    public PackageModule() {super(); }
    public PackageModule(String name1) {
        super(name1);
    }
    
    public ReferenceLocation getRefactorAdditionLocation() {
    	return this.lastObjectLocation;
    }
    
    public int getSearchType() {return ReferenceUtilities.REF_PACKAGE_MODULE;}
    public String getTemplateName() {return "package";}
    
   
    public ModuleObject getBaseSearchClass() {
    	if (this.instancePackage == null) return null;
    	return this.instancePackage.getObject();
    }
    
    public void handleFinalization() {
    	if (this.instancePackage != null) {
    		InstancePackage instMod = (InstancePackage) this.getInstancePackage().getObject();
    		if (instMod != null) {
    			instMod.removePackage();
    		}
    	}
    }
  
    public ReferenceItem getSearchReference() {
        return searchReference;
    }
    
    public void setSearchReference(ReferenceItem searchReference) {
        this.searchReference = searchReference;
    }
	public void setInstancePackage(ReferenceItem<InstancePackage> instancePackage) {
		this.instancePackage = instancePackage;
	}
	public ReferenceItem<InstancePackage> getInstancePackage() {
		return instancePackage;
	}
	public void setLastObjectLocation(ReferenceLocation lastObjectLocation) {
		this.lastObjectLocation = lastObjectLocation;
	}
	public ReferenceLocation getLastObjectLocation() {
		return lastObjectLocation;
	}
	
}
