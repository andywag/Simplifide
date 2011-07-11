package com.simplifide.base.core.hierarchy;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.var.realvars.SystemVar;

/** Convenience class which contains a tree full of the chip hierarchy. A new
 *  list is being used due to the complexity of ConnectorHolder and 
 *  Connector Wrapper. This set of classes is going to be used for creating 
 *  connections in refactoring
 **/
public class PathTreeElement {
	

	
	private ArrayList<PathTreeElement> pathList = new ArrayList<PathTreeElement>();
	
	private InstanceModule module;
	private ModInstanceConnect connection;
	private PathTreeElement parent;
	
	public PathTreeElement() {}
	
	public PathTreeElement(InstanceModule module,ModInstanceConnect connection) {
		this.setModule(module);
		this.connection = connection;
	}
	
	public ArrayList<PathTreeElement> getAllPathsToModule(InstanceModule umod) {
		ArrayList<PathTreeElement> outList = new ArrayList<PathTreeElement>();
		if (umod.equals(this)) outList.add(this);
		else {
			for (PathTreeElement el : pathList) {
				outList.addAll(el.getAllPathsToModule(umod));
			}
		}
		return outList;
	}
	
	/** Find the path from the leaf node to an upper node in the tree */
	private ArrayList<PathTreeElement> checkIntersection(PathTreeElement dest) {
		ArrayList<PathTreeElement> pathList = new ArrayList<PathTreeElement>();
		PathTreeElement top2 = dest;
		while (top2 != null) {
			if (!(top2 instanceof GeneratePathTreeElement)) {
				pathList.add(top2);
			}
			if (top2.equals(this)) return pathList;
			top2 = top2.getParent();
		}
		return null;
	}
	
	/** Creates a list of paths from the source to the destination. A null
	 *  destination means find all of the connections 
	 **/
	public ArrayList<PathTotalElement>  resolvePath(PathTreeElement dest, 
			SystemVar sourcePort) {
		ArrayList<PathTotalElement> outList = new ArrayList<PathTotalElement>();
		// Finds the Top Element and appends the source to top files including top
		PathTreeElement top = this;
		ArrayList<PathTreeElement> check = null;
		
		SystemVar currentVar = sourcePort;
		while (top != null) { 
			check = top.checkIntersection(dest);
			if (check == null) { // Upward Path
				// Check to see if the port already exists for this module
				if (currentVar.getVariableType() == SystemVar.OUTPUT || 
					currentVar.getVariableType() == SystemVar.INOUT ||
					currentVar.getVariableType() == SystemVar.BUFFER) {
					ModInstanceConnect connect = top.getConnection();
					PortConnect port = (PortConnect) connect.findPort(currentVar.getname());
					ModuleObject obj = port.getExternVar().getObject(); 
					if (obj instanceof PortDefault) {
						currentVar = ((PortDefault) obj).getLocalVar();
					}
					else if (obj instanceof SystemVar) {
						currentVar = (SystemVar) obj;
					}
				}
				else {
					outList.add(new PathTotalElement(top,currentVar,PathTotalElement.UP));
				}
			}
			else {
				outList.add(new PathTotalElement(top,currentVar,PathTotalElement.FLAT));
				break;
			}
			top = top.getParent();
		}
		if (check != null) {
			for (int i=check.size()-2;i>=0;i--) {
				outList.add(new PathTotalElement(check.get(i),currentVar,PathTotalElement.DOWN));
			}
		}
		return outList;
	}
	
	
	
	/** Returns a set of paths to this entity */
	public ArrayList<PathTreeElement> getPathsToEntity(InstanceModule module) {
		ArrayList<PathTreeElement> elList = new ArrayList<PathTreeElement>();
		
		if (module.equals(this.module)) {
			elList.add(this);
		}
		for (PathTreeElement el : getPathList()) {
			elList.addAll(el.getPathsToEntity(module));
		}
		return elList;
	}
	
	/** Returns a full list of elements in the tree */
	public ArrayList<PathTreeElement> getFullList() {
		ArrayList<PathTreeElement> treeList = new ArrayList<PathTreeElement>();
		treeList.add(this);
		for (PathTreeElement el : this.getPathList()) {
			treeList.addAll(el.getFullList());
		}
		return treeList;
	}
	/** Gets the full name of this tree */
	public String getPathName() {
		String par = "";
		if (parent != null) par = parent.getPathName() + ".";
		if (connection != null) par += connection.getname();
		else par += module.getname();
		return par;
	}
	
	/** Adds an Element to this node in the tree */
	public void addElement(PathTreeElement el) {
		this.getPathList().add(el);
		el.setParent(this);
	}
	
	public String toString() {
		String ret = "";
		if (this.module != null) ret = this.module.toString(); 
		if (this.connection != null) ret =  this.connection.toString();
		return ret;
	}

	public void setModule(InstanceModule module) {
		this.module = module;
	}

	public InstanceModule getModule() {
		return module;
	}

	public void setConnection(ModInstanceConnect connection) {
		this.connection = connection;
	}

	public ModInstanceConnect getConnection() {
		return connection;
	}

	public void setParent(PathTreeElement parent) {
		this.parent = parent;
	}

	public PathTreeElement getParent() {
		return parent;
	}

	public void setPathList(ArrayList<PathTreeElement> pathList) {
		this.pathList = pathList;
	}

	public ArrayList<PathTreeElement> getPathList() {
		return pathList;
	}

	
	
	


}
