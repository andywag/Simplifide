/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.library.storage;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObjectFindItemList;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.library.har.EclipseHarProject;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;

public class DependencyList implements Serializable{
	private static final long serialVersionUID = 3342908761142283782L;
	
	private boolean automaticLoad = false;
	private URI location;
	private ArrayList<ModuleObjectFindItem> depList = new ArrayList<ModuleObjectFindItem>();
	
	public DependencyList(DesignFile dfile) {
		this.location = dfile.getUri();
		
		for (ReferenceItem<? extends ModuleObjectFindItem> nfileRef : dfile.getCompileInfo().getParentItemList().getGenericSelfList()) {
			getDepList().add(nfileRef.getObject());
		}
	}

	public void convertLocationsToHar(EclipseHarProject har) {
		
	}
	
	/** Load up the list of dependencies and write them to the appropriate design file */
	public void convertList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		DesignFile dfile = LocationOperations.getDesignFile(this.location); 
		if (dfile == null) {
			//HardwareLog.logInfo("Isssue Finding" + this.location); // This warning is caused because the file doesn't exist at the correct time
			return;
		}
		ModuleObjectFindItemList mlist = new ModuleObjectFindItemList();
		for (ModuleObjectFindItem item : this.depList) {
			mlist.addObject(item.createReferenceItem());
		}
		dfile.getCompileInfo().setParentItemList(mlist);
		
	}
	


	public void setDepList(ArrayList depList) {
		this.depList = depList;
	}

	public ArrayList getDepList() {
		return depList;
	}

	public void setLocation(URI location) {
		this.location = location;
	}

	public URI getLocation() {
		return location;
	}

	public void setAutomaticLoad(boolean automaticLoad) {
		this.automaticLoad = automaticLoad;
	}

	public boolean isAutomaticLoad() {
		return automaticLoad;
	}
}
