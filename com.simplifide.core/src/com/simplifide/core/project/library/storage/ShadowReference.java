/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.library.storage;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceItemNew;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;

/** Reference to an object which needs to be created by a compilation 
 *  of the design
 */
public class ShadowReference extends ReferenceItemNew{

	 private boolean loaded = false;
	 private int attemptedLoads = 0;
	 
	 public ShadowReference(String name, int type, ReferenceLocation loc) {
	    	super(name,type,loc);
	 }
	 
	 private void privateLoadObject() {
		 if (this.loaded) {
			 if (super.getObject() != null) {
				 attemptedLoads = 0;
				 return;
			 }
			 if (attemptedLoads > 0) return;
			 attemptedLoads++;
		 }
		 this.loaded = true;
		 
		 ReferenceLocation loc = this.getLocation();
		 DesignFile dfile = LocationOperations.getDesignFile(loc);
		 if (dfile != null) {
			 dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
			 
			 ModuleObjectBaseItem base = new ModuleObjectBaseItem(this.getname());
			
			 ModuleObjectIndexTop top = new ModuleObjectIndexTop(base.createReferenceItem(),base);
			 ReferenceItem ref = dfile.getDesignFileBuilder().getSuperModule().findBaseReference(top);
			 if (ref != null) this.setObject(ref.getObject());
			
		 }
			 
		
	 }
	 
	 public ModuleObject loadObject() {
		 this.privateLoadObject();
		 return super.getObject();
		
	}
	 
	 public ModuleObject getObject() {
		 ModuleObject obj = super.getObject();
		  if (obj == null) {
			  this.privateLoadObject();
			  obj = super.getObject();
		 }
		 return obj;
	 }
	 
	 /** Method to identify whether this refernce is a shadow ref (not loaded) */
	 public boolean isShadow() {return true;}
	 
	 public boolean hasObject() {
	    	return false;
	    }

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public boolean isLoaded() {
		return loaded;
	}
	
}
