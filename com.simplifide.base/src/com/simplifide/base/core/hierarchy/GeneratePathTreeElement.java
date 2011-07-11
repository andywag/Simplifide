package com.simplifide.base.core.hierarchy;

import java.util.ArrayList;

import com.simplifide.base.core.module.InstanceModule;


/** Path which handles a generate statement. This is a pass through other
 *  than in the hierarchy window. This shoudl show in the window, but only 
 *  be pass through for normal operations
 * 
 * @author andy
 *
 */
public class GeneratePathTreeElement extends PathTreeElement {

	private String name;
	
	public GeneratePathTreeElement(String name) {
		super(null,null);
		this.setName(name);
	}
	
	
	public ArrayList<PathTreeElement> getAllPathsToModule(InstanceModule umod) {
		ArrayList<PathTreeElement> outList = new ArrayList<PathTreeElement>();
		for (PathTreeElement el : this.getPathList()) {
			outList.addAll(el.getAllPathsToModule(umod));
		}
		return outList;
	}
	
	public ArrayList<PathTreeElement> getPathsToEntity(InstanceModule module) {
		ArrayList<PathTreeElement> elList = new ArrayList<PathTreeElement>();
		for (PathTreeElement el : this.getPathList()) {
			elList.addAll(el.getPathsToEntity(module));
		}
		return elList;
	}
	
	/** Returns a full list of elements in the tree */
	public ArrayList<PathTreeElement> getFullList() {
		ArrayList<PathTreeElement> treeList = new ArrayList<PathTreeElement>();
		for (PathTreeElement el : this.getPathList()) {
			treeList.addAll(el.getFullList());
		}
		return treeList;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

	
}
