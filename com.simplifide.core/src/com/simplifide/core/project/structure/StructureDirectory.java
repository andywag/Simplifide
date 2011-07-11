package com.simplifide.core.project.structure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StructureDirectory extends StructureBase{

	public static int DIR = 1;
	public static int COPY = 2;
	public static int LINK = 3;
	
	private ArrayList<StructureBase> children;
	
	public StructureDirectory(String name) {
		super(name);
	}
	
	
	private static StructureBase createFile(File ufile, List<StringFile> links, File base) {
		StructureBase struct;
		if (ufile.isDirectory()) struct = new StructureDirectory(ufile.getName());
		else struct = new StructureBase(ufile.getName());
		
		for (StringFile link : links) {
			if (link.file.equals(ufile)) {
				struct.setLinkName(link.string);
			}
		}
		
		
		return struct;
	}
	
	public static StructureBase createStructure(File ufile, List<StringFile> links, File base) {
		StructureBase struct = createFile(ufile, links,base);
		if (ufile.isDirectory()) {
			File[] children = ufile.listFiles();
			for (File child : children) {
				((StructureDirectory)struct).addChild(createStructure(child,links,base));
			}
		}
		return struct;
	}
	
	public StructureDirectory copyShallow() {
		StructureDirectory d = new StructureDirectory(this.getName());
		d.setLinkType(this.getLinkType());
		d.setChildren(this.getChildren());
		return d;
	}
	
	public int getType() {
		return DIR;
	}

	public boolean isDirectory() {
		return true;
	}
	
	public void reload()  {
		this.children.clear();
	}
	
	public void addChild(StructureBase base) {
		if (children == null) this.children = new ArrayList<StructureBase>();
		this.children.add(base);
	}
	
	public void setChildren(ArrayList<StructureBase> children) {
		this.children = children;
	}

	public ArrayList<StructureBase> getChildren() {
		if (children == null) children = new ArrayList<StructureBase>();
		return children;
	}

	public static class StringFile {
		public String string;
		public File file;
		
		public StringFile(String string, File file) {
			this.string = string;
			this.file   = file;
		}
	}
	
	
}
