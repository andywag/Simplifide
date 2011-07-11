package com.simplifide.base.core.hierarchy;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;

public class HierarchyList extends NoSortList<ConnectorModule>{

	
	private ArrayList<PathTreeElement> pathTree = new ArrayList<PathTreeElement>();
	private PathTreeElement treeRoot = new PathTreeElement();
	
	public HierarchyList(String name) {super(name);}
	
	
	public ArrayList<PathTreeElement> getPathsToEntity(InstanceModule module) {
		ArrayList<PathTreeElement> elList = new ArrayList<PathTreeElement>();
		for (PathTreeElement el : getPathTree()) {
			elList.addAll(el.getPathsToEntity(module));
		}
		return elList;
	}
	
	/** Creates a tree of connections for this suite */
	public void createTree() {
		ArrayList<PathTreeElement> tree = new ArrayList<PathTreeElement>();
		treeRoot = new PathTreeElement();
		for (ReferenceItem <? extends ConnectorModule> umodRef : this.getGenericSelfList()) {
			ConnectorModule umod = umodRef.getObject();
			PathTreeElement nroot = umod.createTree(null,0);
			tree.add(nroot);
			getTreeRoot().addElement(nroot);
		}
		this.setPathTree(tree);
	}
	
	public ArrayList<PathTreeElement> getFullList() {
		ArrayList<PathTreeElement> elList = new ArrayList<PathTreeElement>();
		for (PathTreeElement el : getPathTree()) {
			elList.addAll(el.getFullList());
		}
		return elList;
	}

	
	public void setPathTree(ArrayList<PathTreeElement> pathTree) {
		this.pathTree = pathTree;
	}


	public ArrayList<PathTreeElement> getPathTree() {
		return pathTree;
	}


	public void setTreeRoot(PathTreeElement treeRoot) {
		this.treeRoot = treeRoot;
	}


	public PathTreeElement getTreeRoot() {
		return treeRoot;
	}
	
	
	
	
}
