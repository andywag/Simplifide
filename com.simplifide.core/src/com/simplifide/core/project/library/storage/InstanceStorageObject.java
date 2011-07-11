/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.library.storage;

import java.io.Serializable;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;

/**
 * Object which creates another storage file containing the subelements
 *
 */
public class InstanceStorageObject implements Serializable{

	
	
	private static final long serialVersionUID = -958293853619163686L;
	private String name;
	private int type;
	private ReferenceLocation location;
	
	public InstanceStorageObject(ReferenceItem ref) {
		if (ref != null) {
			this.name = ref.getname();
			this.setType(ref.getSearchType());
			if (ref.getLocation() != null) {
				this.setLocation(ref.getLocation());
			}
		}
	}
	
	public ReferenceItem loadStore() {
		return new ShadowReference(this.name,this.type,this.location);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setLocation(ReferenceLocation location) {
		this.location = location;
	}

	public ReferenceLocation getLocation() {
		return location;
	}

	
}
