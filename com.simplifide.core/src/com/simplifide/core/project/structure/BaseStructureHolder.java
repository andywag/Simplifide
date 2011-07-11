package com.simplifide.core.project.structure;

import java.util.HashMap;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

/**
 * This class deals with loading up the structure for the workspace and projects. 
 *
 */
public class BaseStructureHolder {

	private StructureDirectory structureDirectory;
	private HashMap<String,StructureBase> linkMap = new HashMap<String,StructureBase>();

	public BaseStructureHolder() {}
	public BaseStructureHolder(StructureDirectory directory) {
		this.structureDirectory = directory;
	}

	/** Handle the single element being parsed */
	protected void parseSingleStructureBase(StructureBase dir) {
		String linkName = dir.getLinkName();
		if (linkName != null && !linkName.equalsIgnoreCase("")) {
			this.linkMap.put(linkName, dir);
		}
	}
	/** Parse the directories. Used to convert the directories to the internal format **/
	protected final void parseStructureDirectory(StructureDirectory dir) {
		this.parseSingleStructureBase(dir);
		for (StructureBase base : dir.getChildren()) {
			if (base.isDirectory()) {
				this.parseStructureDirectory((StructureDirectory)base);
			}
		}
	}
	
	/** Method to retrieve the Name of the Structure Directory of Null if it doesn't exists */
	protected IFolder getStructureFolder(IProject proj, String name) {
		String fname = this.getStructureName(name);
		if (fname != null) {
			IFolder folder = proj.getFolder(fname);
			return folder;
		}
		return null;
	}
	
	/** Method to retrieve the Name of the Structure Directory of Null if it doesn't exists */
	protected String getStructureName(String name) {
		StructureBase dir = this.getLinkMap().get(name);
		if (dir == null) return null;
		else return dir.getName();
	}
	
	public void setStructureDirectory(StructureDirectory structureDirectory) {
		this.structureDirectory = structureDirectory;
	}

	public StructureDirectory getStructureDirectory() {
		return structureDirectory;
	}

	public void setLinkMap(HashMap<String,StructureBase> linkMap) {
		this.linkMap = linkMap;
	}

	public HashMap<String,StructureBase> getLinkMap() {
		return linkMap;
	}
}
