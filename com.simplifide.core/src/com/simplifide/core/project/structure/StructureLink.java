package com.simplifide.core.project.structure;

/** @deprecated */
public class StructureLink extends StructureDirectory{
	
	private String location;
	
	public StructureLink(String location) {
		super(location);
	}
	
	
	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}


	public static class Copy extends StructureLink {
		public Copy(String location) {
			super(location);
		}
		public int getType() {
			return StructureDirectory.COPY;
		}
	}
	
	public static class Link extends StructureLink {
		public Link(String location) {
			super(location);
		}
		public int getType() {
			return StructureDirectory.LINK;
		}
	}

}
