/*
 * PortConnectTopNew.java
 *
 * Created on August 16, 2006, 1:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.port;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author awagner
 */
public class PortConnect<T extends PortTop> extends PortTop<T>
{

	// Location of the second var in the arrow connection 
	private ReferenceLocation connectionLocation;
	
    private ReferenceItem<? extends ModuleObject> externVar;
    /** Creates a new instance of PortConnectTopNew */
    public PortConnect() {}
    
    /** Used in cases where the connection is not found */
    public PortConnect(String name,  ReferenceItem<? extends ModuleObject> externVar) {
        super(name);
        this.externVar = externVar;
    }
    public PortConnect(ReferenceItem<? extends SystemVar> localVar, ReferenceItem<? extends ModuleObject> externVar)
    {
        super(localVar);
        this.externVar = externVar;
    }

    public ModuleObject getRealExternalVar() {
    	if (externVar != null) 
    		return externVar.getObject();
    	else 
    		return super.getRealExternalVar();
    }
    
    public String getDisplayName()
    {
    	
    	String ext = "";
    	if (this.getExternVar() != null) ext = this.getExternVar().getDisplayName();
    	String outval = this.getname() + "(" + ext + ")";
        return outval;
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        if (this.getExternVar() != null) {
            return this.getExternVar().getDependants();
        }
        return new ModuleObjectWithList("").createReferenceItem();
    }
    
    public ReferenceItem<? extends TypeVar> getTypeReference() {
        if (this.externVar != null) {
            return this.getExternVar().getObject().getTypeReference();
        }
        return null;
    }

    
    public ReferenceItem<? extends ModuleObject> getExternVar() {
        return externVar;
    }

    public void setExternVar(ReferenceItem<? extends ModuleObject> externVar) {
        this.externVar = externVar;
    }

	public void setConnectionLocation(ReferenceLocation connectionLocation) {
		this.connectionLocation = connectionLocation;
	}

	public ReferenceLocation getConnectionLocation() {
		return connectionLocation;
	}

   
    
   
    
}
