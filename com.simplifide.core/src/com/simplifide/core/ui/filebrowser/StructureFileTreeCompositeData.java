package com.simplifide.core.ui.filebrowser;

import java.io.File;
import java.util.ArrayList;

import com.simplifide.core.project.structure.StructureDirectory.StringFile;

public class StructureFileTreeCompositeData {

	public static String LINK_DESIGN    = "design";
	public static String LINK_PROJECTS  = "projects";
	public static String LINK_LIBRARIES = "libraries";
	public static String LINK_SCRIPT    = "script";
	public static String LINK_DOC       = "docs";
	public static String LINK_TEST      = "test";
	public static String LINK_BUILD     = "build";
	public static String LINK_SYNTH     = "synthesis";
	public static String LINK_SUB       = "subprojects";
	
	private ArrayList<StringFile> links = new ArrayList<StringFile>();
	
	
	public void clearLinks() {
		getLinks().clear();
	}
	
	public void addLink(String string, File file) {
		StringFile link = new StringFile(string, file);
		getLinks().add(link);
	}
	
	public void removeLink(File ufile) {
		for (StringFile link : this.getLinks()) {
			if (link.file.equals(ufile)) {
				this.getLinks().remove(link);
				break;
			}
		}
	}
	
	public String getLink(File ufile) {
		for (StringFile link : this.getLinks()) {
			if (link.file.equals(ufile)) {
				return link.string;
			}
		}
		return null;
	}

	public void setLinks(ArrayList<StringFile> links) {
		this.links = links;
	}

	public ArrayList<StringFile> getLinks() {
		return links;
	}
	
	
	
	
}
