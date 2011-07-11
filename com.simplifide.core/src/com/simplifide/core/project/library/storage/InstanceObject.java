/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.library.storage;

import java.io.File;
import java.io.Serializable;
import java.net.URI;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.project.library.har.EclipseHarProject;
import com.simplifide.core.project.library.zip.ZipFileStore;
import com.simplifide.core.project.library.zip.ZipFileSystem;
import com.simplifide.core.source.design.DesignFile;

public class InstanceObject implements Serializable{

	
	private static final long serialVersionUID = -1277435825454507455L;
	private String name;
	private int type;
	private ReferenceLocation location;
	
	public InstanceObject(ReferenceItem ref) {
		if (ref != null) {
			this.name = ref.getname();
			this.setType(ref.getSearchType());
			if (ref.getLocation() != null) {
				this.setLocation(ref.getLocation());
			}
		}
	}
	
	public void convertLocationToHar(EclipseHarProject har, URI baseLocation) {
		if (this.location == null) return;
		URI u = this.location.getUri();
		if (u.getScheme().equalsIgnoreCase(ZipFileSystem.SCHEME_ZIP)) return;
		URI rel = baseLocation.relativize(u);
		//ZipFileStore root = har.getInformation().getMainHarFile();
		ZipFileStore rels = har.getInformation().getZipFileStore(rel.toString());
		URI resUri = rels.toURI();
		location.setUri(resUri);
		
	}
	
	public void convertLocation(UniqueList<DesignFile> sourceList) {
		if (location == null) return;
		//File ufile = new File(location.getUri());
		File ufile = new File(location.getUri().getPath());
		for (ReferenceItem<? extends DesignFile> dfileRef : sourceList.getGenericSelfList()) {
			String fname = dfileRef.getObject().getname();
			if (ufile.getName().equalsIgnoreCase(fname)) {
				location.setUri(dfileRef.getObject().getUri());
				return;
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
