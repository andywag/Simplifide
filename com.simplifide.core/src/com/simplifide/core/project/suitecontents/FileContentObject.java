package com.simplifide.core.project.suitecontents;

public class FileContentObject {

	private String location;
	private boolean link;
	
	public FileContentObject(String location, boolean link) {
		this.setLocation(location);
		this.link = link;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
	
	public void setLink(boolean link) {
		this.link = link;
	}

	public boolean isLink() {
		return link;
	}

	public static class File extends FileContentObject {
		public File(String location, boolean link) {super(location,link);}
	}
	
	public static class Folder extends FileContentObject {
		public Folder(String location, boolean link) {super(location,link);}
	}

	
}
