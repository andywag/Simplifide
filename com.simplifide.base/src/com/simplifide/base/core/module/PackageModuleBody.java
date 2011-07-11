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
public class PackageModuleBody extends TopModule{
    
	
    private static final long serialVersionUID = 1L;
    private ReferenceItem<InstancePackage> instancePackage;
	
    private ReferenceLocation lastObjectLocation;

    
    /** Creates a new instance of PackageModule */
    public PackageModuleBody() {super(); }
    public PackageModuleBody(String name1)
    {
      super(name1);
   }
    
    public ReferenceLocation getRefactorAdditionLocation() {
    	return this.lastObjectLocation;
    }
    
    public ModuleObject getBaseSearchClass() {
    	return this.instancePackage.getObject();
    }
    
    public void handleFinalization() {
    	InstancePackage instMod = (InstancePackage) this.getInstancePackage().getObject();
    	if (instMod != null) instMod.removePackageBody();
    }
    
    public void cleanObject() {
        this.clearList();
    }
    
   public int getSearchType() {return ReferenceUtilities.REF_PACKAGE_MODULE_BODY;}
   
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
