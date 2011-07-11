package com.simplifide.core.project.structure;

public class StructureFile extends StructureBase{

	private String contents;
	
	public StructureFile(String name) {
		super(name);
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}
}
