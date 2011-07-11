/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.source.design;



import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectFindItemList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.source.LocationOperations;

public class DesignFileCompileInfo {

	/** List of Files which depend on this object being compiled */
	private UniqueList<DesignFile> dependentList = new UniqueList<DesignFile>();

	/** List of Files which this file is dependent on */
	private UniqueList<DesignFile> parentList = new UniqueList<DesignFile>();
	
	/** List of ModuleObjectFindItems this project is dependent on */
	private ModuleObjectFindItemList parentItemList = new ModuleObjectFindItemList();
	
	/** Design File which this class supports */
	private DesignFile designFile;
	
	/** Variable used to delineate whether the design file has been compiled or not. This
	 *  is used initially when creating the compile list. 
	 **/
	private boolean finished = false;
	
	private boolean inProgress = false;
	
	
	public DesignFileCompileInfo(DesignFile designFile) {
		this.designFile = designFile;
	}
		
	/** Convert the list of dependencies to the proper DesignFile 
	 * 
	 */
	public void convertContextList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		// The dependent can't be cleared because other objects may have added themselves first
		//this.dependentList.clearList();
		this.parentList.clearList();
		this.convertParentList(suiteRef);
		this.finished = false;
	}
	
	/** @todo : This operation needs to be fixed because of the conversion
	 *  to ModuleObjectFindItem
	 */
	public void handleParentDeleted(List<DesignFile> dfileList) {
		/*for (DesignFile deletedFile : dfileList) {
			for (int i=parentList.getnumber()-1;i>=0;i--) {
				DesignFile dfile = parentList.getObject(i).getObject();
				if (dfile.equals(deletedFile)) {
					parentList.removeObject(i);
				}
			}
		}*/
		
	}
	
	
	
	/** Method which creates the compile list for the project */
	
	public UniqueList<DesignFile> createCompileList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		if (this.inProgress) {
			HardwareLog.logInfo("Found Circular Dependency in " + this.designFile.getname());
			return new UniqueList<DesignFile>();
		}
		
		this.inProgress = true;
		UniqueList<DesignFile> designList = new UniqueList<DesignFile>();

		for (DesignFile dfile : this.parentList.getRealSelfList()) {
			if (!dfile.getCompileInfo().isFinished()) {
				if (!dfile.equals(this.designFile)) {
					designList.addAll(dfile.getCompileInfo().createCompileList(suiteRef));
				}
			}
		}
		designList.addObject(this.designFile.createReferenceItem());
		this.setFinished(true);
		this.inProgress = false;
		return designList;
	}
	
	
	
	
	private ReferenceLocation convertFindItem(ModuleObjectFindItem item,
			ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		ReferenceLocation storeLoc = null;
		
		ReferenceItem baseRef = suiteRef;
		ReferenceItem<InstancePackage> packRef = null;
		// Hack to support the use of work rather than a correct library name
		if (item.getname().equalsIgnoreCase("work")) {
			 baseRef = this.designFile.getProjectRef();
			 if (item.getNext() != null) {
				 packRef = item.getNext().findRealReferenceItem(baseRef,ReferenceUtilities.REF_INSTANCE_PACKAGE);
			 }
		}
		else {
			packRef = item.findRealReferenceItem(baseRef,ReferenceUtilities.REF_INSTANCE_PACKAGE);
		}
		if (packRef == null) {
			packRef = item.findRealReferenceItem(this.designFile.getProjectRef(), ReferenceUtilities.REF_INSTANCE_PACKAGE);
		}
		
		// Incredible kludge to deal with an instance module being
		// stored as an instance package
		if (packRef != null) {
			ReferenceItem ref = packRef;
			ModuleObject obj = ref.getObject();
			if (!(obj instanceof InstancePackage)) {
				packRef = null;
			}
		}
		
		if (packRef != null ) {
			if (packRef.getObject() != null) {
				
				packRef.getObject().clearShadowReferences();
				InstancePackage pack = packRef.getObject();
			}
			ReferenceItem<PackageModule> pack;
			if (item.getNext() == null) {
				pack = packRef.getObject().getPackageModuleReference();
			}
			else {
				 pack = item.getNext().findRealReferenceItem(packRef,ReferenceUtilities.REF_PACKAGE_MODULE);
				 if (pack == null) pack = packRef.getObject().getPackageModuleReference();
			}			
			if (pack != null) {
				storeLoc = pack.getLocation();
				
			}
		}
		if (storeLoc == null) {
			ReferenceItem<InstanceModule> instRef = item.findRealReferenceItem(suiteRef,ReferenceUtilities.REF_INSTANCE_MODULE);
			

			if (instRef == null) instRef = item.findRealReferenceItem(this.designFile.getProjectRef(), ReferenceUtilities.REF_INSTANCE_MODULE);
			if (instRef == null) {
				instRef = suiteRef.getObject().findLibraryReference(item, ReferenceUtilities.REF_INSTANCE_MODULE);
			}
			if (instRef != null && instRef.getType() == ReferenceUtilities.REF_INSTANCE_MODULE) {
				if (instRef.getObject() != null) {
					instRef.getObject().clearShadowReferences();

				}
				ReferenceItem<Entity> entRef = instRef.getObject().getEntityReference();
				if (entRef != null)
					storeLoc = entRef.getLocation();
				
			}
		}
		return storeLoc;
	}

	
	
	/** Function which parses the supermodule to calculate the dependencies for
	 *  for this module and also places this as depepndant module on this list
	 * 
	 * @param suiteRef
	 */
	private void convertParentList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		TopObjectBase base = this.getParentItemList();
		int a;
		if (base != null) {
			for (int i = 0; i < base.getnumber(); i++) {
				ReferenceItem uitem = (ReferenceItem) base.getObject(i);
				if (uitem != null) {
					ModuleObjectFindItem item = (ModuleObjectFindItem) uitem.getObject();
					ReferenceLocation storeLoc = this.convertFindItem(item, suiteRef);
					
					if (storeLoc != null) {
						DesignFile sfile = LocationOperations.getDesignFile(storeLoc);
						if (sfile != null) {
							this.getParentList().addObject(sfile.createReferenceItem());
							sfile.getDependentList().addObject(designFile.createReferenceItem()); // Add to Source List
						}
					}
				}
				
			}
		}
	}
	
	public List<DesignFile> getParentArrayList() {
		ArrayList<DesignFile> files = new ArrayList<DesignFile>();
		for (ReferenceItem<? extends DesignFile> dfR : this.parentList.getGenericSelfList()) {
			files.add(dfR.getObject());
		}
		return files;
	}
	

	public void setDependentList(UniqueList<DesignFile> dependentList) {
		this.dependentList = dependentList;
	}

	public UniqueList<DesignFile> getDependentList() {
		return dependentList;
	}

	

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isFinished() {
		return finished;
	}

	private void setParentList(UniqueList<DesignFile> parentList) {
		this.parentList = parentList;
	}

	private UniqueList<DesignFile> getParentList() {
		return parentList;
	}

	public void setParentItemList(ModuleObjectFindItemList parentItemList) {
		this.parentItemList = parentItemList;
	}

	public ModuleObjectFindItemList getParentItemList() {
		return this.parentItemList;
	}

	
}
