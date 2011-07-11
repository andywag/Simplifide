package com.simplifide.core.project.structure;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

public class StructureBase {
	
	public static final int LINK_NONE = 0;
	public static final int LINK_ECLIPSE      = 1;
	public static final int LINK_DISTRIBUTION = 2;
	public static final int LINK_COPY = 3;
	public static final int LINK_COPY_DISTRIBUTION = 4;
	public static final int LINK_NEW = 5;

	
	private String linkName;
	private String name;
	private StructureBase parent;
	
	
	private int linkType;
	private String location;
	
	public StructureBase(String name) {
		this.name = name;
	}
	
	/** Method to retrieve the Name of the Structure Directory of Null if it doesn't exists */
	protected IFolder getStructureFolder(IProject proj) {
		String fname = this.getStructureName();
		if (fname != null) {
			IFolder folder = proj.getFolder(fname);
			return folder;
		}
		return null;
	}
	
	/** Method to retrieve the Name of the Structure Directory of Null if it doesn't exists */
	protected String getStructureName() {
		String pname = "";
		if (this.getParent() != null) pname += this.parent.getStructureName() + "/";
		pname += this.getName();
		return pname;
	}
	
	public String toString() {
		String name1 = this.getName();
		if (this.linkName != null) name1 += "(" + this.getLinkName() + ")";
		return name1;
	}
	
	public boolean isDirectory() {
		return false;
	}
	
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	
	public String getLinkName() {
		return linkName;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setParent(StructureBase parent) {
		this.parent = parent;
	}

	public StructureBase getParent() {
		return parent;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

}
