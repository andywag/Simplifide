/*
 * InstanceModuleTop.java
 *
 * Created on January 26, 2007, 7:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.module;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author awagner
 */
public class InstanceModuleTop<T extends ModuleObject> extends ModuleObjectNew<T>{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReferenceItem<CoreProjectBasic> projectRef;
    /** Creates a new instance of InstanceModuleTop */
    public InstanceModuleTop() {}
    public InstanceModuleTop(String name) {super(name);}
    public InstanceModuleTop(String name, ReferenceItem<CoreProjectBasic> basicRef) {
    	super(name);
    	this.projectRef = basicRef;
    }
    
    public int getSearchType() {return ReferenceUtilities.REF_INSTANCE_MODULE_TOP;}
    
	public void setProjectRef(ReferenceItem<CoreProjectBasic> projectRef) {
		this.projectRef = projectRef;
	}
	public ReferenceItem<CoreProjectBasic> getProjectRef() {
		return projectRef;
	}
    
}
