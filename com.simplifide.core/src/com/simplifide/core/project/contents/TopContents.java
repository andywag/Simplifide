package com.simplifide.core.project.contents;

import com.simplifide.core.project.structure.StructureDirectory;

public class TopContents {

	private String name;
	private StructureDirectory structure;
	
	private int linkType;
	private String linkLocation;

	public TopContents(String name) {
		this.name = name;
	}

	public void setLink(int linkType, String linkLocation) {
		this.linkType = linkType;
		this.linkLocation = linkLocation;
	}
	
	public void setStructure(StructureDirectory structure) {
		this.structure = structure;
	}

	public StructureDirectory getStructure() {
		return structure;
	}

	public void setLinkLocation(String linkLocation) {
		this.linkLocation = linkLocation;
	}

	public String getLinkLocation() {
		return linkLocation;
	}

	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}

	public int getLinkType() {
		return linkType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	
	
}
