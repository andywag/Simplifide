package com.simplifide.base.core.hierarchy;

import java.util.ArrayList;

/** Tree Element used to store module paths. These classes are a bit
 *  convoluted as PathTreeElement also has these uses. The hierarchical 
 *  direction should probably be moved here instead
 */
public class PathTotalTreeElement {

	private PathTotalElement pathElement;
	private PathTotalTreeElement parent;
	private ArrayList<PathTotalTreeElement> children = new ArrayList<PathTotalTreeElement>();
	

	public PathTotalTreeElement(PathTotalElement pathElement) {
		this.setPathElement(pathElement);
	}

	public void addChild(PathTotalTreeElement child) {
		this.children.add(child);
		child.setParent(this);
	}
	
	public String toString() {
		if (this.pathElement != null) return this.pathElement.toString();
		return "ROOT?";
	}
	
	
	

	public void setPathElement(PathTotalElement pathElement) {
		this.pathElement = pathElement;
	}


	public PathTotalElement getPathElement() {
		return pathElement;
	}


	public void setParent(PathTotalTreeElement parent) {
		this.parent = parent;
	}


	public PathTotalTreeElement getParent() {
		return parent;
	}


	public void setChildren(ArrayList<PathTotalTreeElement> children) {
		this.children = children;
	}


	public ArrayList<PathTotalTreeElement> getChildren() {
		return children;
	}
}
