package com.simplifide.base.core.hierarchy;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.SystemVar;

public class PathTotalElement {

	public static int FLAT = 0;
	public static int UP_FIRST = 1;
	public static int UP = 2;
	public static int DOWN = 3;
	public static int DOWN_FIRST = 4;
	
	private PathTreeElement treeElement;
	private SystemVar variable;
	private int direction;

	

	public PathTotalElement(PathTreeElement element, SystemVar variable, int direction) {
		this.setTreeElement(element);
		this.setVariable(variable);
		this.direction    = direction;
	}
	
	/** Searches the path down for matches */
	public void searchPathOutDown(PathTotalTreeElement currentTree) {
		InstanceModule imod = currentTree.getPathElement().getTreeElement().getModule();
		if (imod == null || imod.getArchitectureReference() == null) return;
		HardwareModule umod = (HardwareModule) imod.getArchitectureReference().getObject();
		
		List<ReferenceItem<ModInstanceConnect>> cons = umod.getInstantiations();
		// Search through the instantiations for connections
		for (ReferenceItem<ModInstanceConnect> conRef : cons) { 
			
			ModInstanceConnect con = conRef.getObject();
			if (variable == null) continue;
			PortConnect port = con.findPortWithExternalName(variable.getname());
			if (port != null) {
				SystemVar newVar = port.getLocalVar();
				if (newVar == null) return;
				if (newVar.getVariableType() == SystemVar.INPUT) { // output case
					InstanceModule newInst = (InstanceModule) con.getEntityRef().getObject().getInstanceModRef().getObject();
					PathTreeElement el = new PathTreeElement(newInst,con);
					PathTotalElement newPath = new PathTotalElement(el,newVar,DOWN);
					PathTotalTreeElement newTree = new PathTotalTreeElement(newPath);
					currentTree.addChild(newTree);
					newPath.searchPathOutDown(newTree);
					
				}
			}	
		}


		
	}
	
	/** Searches the path up for matches */
	public void searchPathOutUp(PathTotalTreeElement currentTree) {
		this.setDirection(UP);
		// This only works on outputs
		if (this.variable.getVariableType() == SystemVar.OUTPUT ||
			this.variable.getVariableType() == SystemVar.BUFFER ||
			this.variable.getVariableType() == SystemVar.INOUT) {

			ModInstanceConnect connect = this.getTreeElement().getConnection();
			if (connect != null) {
				PortConnect port = (PortConnect) connect.findPort(this.variable.getname());
				if (port != null) {
					SystemVar newVar = this.resolveSystemVar(port.getExternVar().getObject());
					if (newVar != null) {
						PathTotalElement newTotal = new PathTotalElement(currentTree.getPathElement().getTreeElement().getParent(),
								newVar,
								PathTotalElement.UP);
						
						PathTotalTreeElement newTree = new PathTotalTreeElement(newTotal);
						currentTree.addChild(newTree);
						
						newTotal.searchPathOutDown(newTree);
						newTotal.searchPathOutUp(newTree);
					}
					
				}
				
			}			
		}
		//if (currentTree.getChildren().size() > 0) parent.addChild(currentTree);
	}
	
	public PathTotalTreeElement resolvePathOut() {
		PathTotalTreeElement parentTree = new PathTotalTreeElement(null);
		
		PathTotalTreeElement currentTree = new PathTotalTreeElement(this);
		parentTree.addChild(currentTree);
		
		this.searchPathOutUp(currentTree);
		this.searchPathOutDown(currentTree);
		
		return parentTree;		
	}
	
	
	
	protected SystemVar resolveSystemVar(ModuleObject obj) {
		SystemVar currentVar = null;
		if (obj instanceof PortDefault) {
			currentVar = ((PortDefault) obj).getLocalVar();
		}
		else if (obj instanceof SystemVar) {
			currentVar = (SystemVar) obj;
		}
		return currentVar;
	}
	
	/** Searches the instantiations of this module to see if this variable is an 
	 *  output of one of these instances
	 */
	private PathTotalTreeElement searchDown(PathTotalTreeElement currentTree) {
		PathTotalElement current = currentTree.getPathElement();
		SystemVar currentVar = current.getVariable();
		
		InstanceModule imod = current.getTreeElement().getModule();
		if (imod == null) return null;
		HardwareModule umod = (HardwareModule) imod.getArchitectureReference().getObject();
		if (umod == null) return null;
		// Check to see if this is an output of another block 
		List<ReferenceItem<ModInstanceConnect>> cons = umod.getInstantiations();
		for (ReferenceItem<ModInstanceConnect> conRef : cons) { 
			
			ModInstanceConnect con = conRef.getObject();
			PortConnect port = con.findPortWithExternalName(currentVar.getname());
			if (port != null) {
				SystemVar newVar = port.getLocalVar();
				if (newVar.getVariableType() == SystemVar.OUTPUT) { // output case
					InstanceModule newInst = (InstanceModule) con.getEntityRef().getObject().getInstanceModRef().getObject();
					PathTreeElement el = new PathTreeElement(newInst,con);
					el.setParent(current.getTreeElement());
					PathTotalElement newPath = new PathTotalElement(el,currentVar,UP);
					PathTotalTreeElement newTree = new PathTotalTreeElement(newPath);
					newTree.addChild(currentTree);
					return newTree;
				}
			}	
		}
		return null;
	}
	
	
	

	
	/** Finds the path which leads to this element. Used for finding port paths */
	public PathTotalTreeElement resolvePathIn() {

		// Current Variables for this Function
		//PathTotalElement current = this;
		PathTotalTreeElement currentTree = new PathTotalTreeElement(this);
		PathTotalTreeElement topTree = currentTree;
		//SystemVar currentVar = this.getVariable();
		
		while (currentTree != null) {
			PathTotalElement current = currentTree.getPathElement();
			SystemVar currentVar = current.getVariable();
			if (currentVar == null) return topTree; // Error Condition
			if (currentVar.getVariableType() == SystemVar.INPUT) { // Upward Path
				ModInstanceConnect connect = currentTree.getPathElement().getTreeElement().getConnection();
				if (connect != null) {
					PortConnect port = (PortConnect) connect.findPort(currentVar.getname());
					if (port != null && port.getExternVar() != null) {
						SystemVar newVar = this.resolveSystemVar(port.getExternVar().getObject());
						PathTotalElement newTotal = new PathTotalElement(current.getTreeElement().getParent(),newVar,PathTotalElement.DOWN);
						PathTotalTreeElement newTree = new PathTotalTreeElement(newTotal);
						newTree.addChild(currentTree);
						currentTree = newTree;
						topTree = currentTree; 
					}
					else currentTree = null;
					
				}
				else {
					currentTree = null; // Not Sure why this is the case
				}
			}
			else if (currentVar.getVariableType() == SystemVar.SIGNAL) { // Flat Path
				if (current.getDirection() != PathTotalElement.UP) {
					current.setDirection(PathTotalElement.FLAT); // If the port is a signal it is the top
				}
				topTree = currentTree; // Top Element is the flat condition
				// Search Lower Instances
				currentTree = this.searchDown(currentTree);
			
			}
			else if (currentVar.getVariableType() == SystemVar.OUTPUT ) { // Up Path
				currentTree = this.searchDown(currentTree);
				topTree = currentTree;
			}
			else {
				break;
			}
			
		}
		return topTree;
	}
	
	public PathTotalElement getStartPathElement(InstanceModule imod, SystemVar svar) {
		
		return null;
	}
	
	public ArrayList<PathTotalElement>  resolvePath(PathTotalElement dest) {
		return this.treeElement.resolvePath(dest.getTreeElement(),this.getVariable());
	}
	

	public String toString() {
		String ret = this.treeElement.toString();
		if (this.variable != null) ret +=  " --- " + this.variable.toString();
		return ret;
	}
	
	public void setTreeElement(PathTreeElement treeElement) {
		this.treeElement = treeElement;
	}

	public PathTreeElement getTreeElement() {
		return treeElement;
	}

	

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public void setVariable(SystemVar variable) {
		this.variable = variable;
	}

	public SystemVar getVariable() {
		return variable;
	}




	
	
}
