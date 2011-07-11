/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.instance;

import java.util.List;

import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;


/** Connection Instance
 * @todo : Remove the connectionModule and convert to a reference connection
 */

public class ModInstanceConnect extends ModInstanceTop implements java.io.Serializable {
   
	private static final long serialVersionUID = 1L;

	
	private String connectionName;
    /** Pointer the Object which this object connects to */
    private ReferenceItem<EntityHolder> entityRef;
    
    /** Location of the module name -- Used for refactoring */
    private ReferenceLocation modnameLocation;
    /** Location of the whole instantiation -- Used for refactoring */
    private ReferenceLocation fullLocation;
    
    private ReferenceItem<InstanceModule> enclosingModuleReference;
    
    public ModInstanceConnect(){super();}
    
    public ModInstanceConnect(String instanceName, ReferenceItem<EntityHolder> connect) {
        super(instanceName);
        this.setEntityRef(connect);
    }
    


    
    public int getSearchType() {
        return ReferenceUtilities.REF_MODINSTANCE_CONNECT;
    }
    
    public String getDisplayName() {
        if (this.entityRef != null && this.entityRef.getObject() != null) {
            String instName = this.entityRef.getObject().getname();
            if (instName == null) instName = "???";
            return this.getname() + "(" + instName + ")";
        }
        return this.getname();
    }
    
    private PortConnect findExternalPort(PortList<PortConnect> ports,String external) {
    	for (ReferenceItem<? extends PortConnect> port : ports.getGenericSelfList()) {
    		if (port.getObject().getExternVar().getname().equalsIgnoreCase(external)) return port.getObject();
    	}
    	return null;
    }
    
    /** Finds a port connection with the external name (external) */
    public PortConnect findPortWithExternalName(String external) {
    	PortConnect port = null;
    	if (this.getGenericListRef() != null && this.getGenericListRef().getObject() != null) {
    		PortList<PortConnect> generics = (PortList) this.getGenericListRef().getObject();
        	port = this.findExternalPort(generics,external);
    	}
    	
    	if (this.getPortListRef() != null && this.getPortListRef().getObject() != null) {
    		PortList<PortConnect> ports = (PortList) this.getPortListRef().getObject();
    		if (port == null) port = this.findExternalPort(ports,external);
    	}
    	return port;
    }
    
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
    	ReferenceItem ref = super.findBaseReference(item);
    	if (ref != null) return ref;
    	
    	// Handle Cases when connection is not found
    	if (this.getEntityRef() == null || this.getEntityRef().getObject() == null) return null;
    	
    	InstanceModule inst = (InstanceModule) this.entityRef.getObject().getInstanceModRef().getObject();
        List<ReferenceItem> conList = inst.getArchitecture().getConnections();
    	for (ReferenceItem ref2 : conList) {
    		if (ref2.getname().equalsIgnoreCase(item.getBase().getname())) return ref2;
    	}
    	return null;
    }
    
    /** TODO : Load the architecture if it isn't already open */
    private void loadArchitecture(InstanceModule imod) {
    	
    }
   
    
    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        ReferenceItem ref = new ReferenceItem(item.getname(),type,null);
        //List<ReferenceItem> refList  = this.getReferenceList().findPrefixItemList(ref);
        
        List<ReferenceItem> refList = this.entityRef.findPrefixItemList(item, type);
        InstanceModule inst = (InstanceModule) this.entityRef.getObject().getInstanceModRef().getObject();
        this.loadArchitecture(inst);
        List<ReferenceItem> conList = inst.getArchitecture().findPrefixItemList(item, type); 
        refList.addAll(conList);
        
        return refList;
    }
    
    
    public ReferenceItem<ModInstanceDefault> getConnectionReference() {
        if (this.entityRef != null) {
            return this.entityRef.getObject().getConnectRef();
        }
        return null;
    }
    
    
    

    public ReferenceItem<EntityHolder> getEntityRef() {
        return entityRef;
    }

    public void setEntityRef(ReferenceItem<EntityHolder> entityRef) {
        this.entityRef = entityRef;
    }


	public void setFullLocation(ReferenceLocation fullLocation) {
		this.fullLocation = fullLocation;
	}

	public ReferenceLocation getFullLocation() {
		return fullLocation;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getConnectionName() {
		if (connectionName != null) return connectionName;
		return this.getEntityRef().getname();
	}

	public void setModnameLocation(ReferenceLocation modnameLocation) {
		this.modnameLocation = modnameLocation;
	}

	public ReferenceLocation getModnameLocation() {
		return modnameLocation;
	}

	public void setEnclosingModuleReference(ReferenceItem<InstanceModule> enclosingModuleReference) {
		this.enclosingModuleReference = enclosingModuleReference;
	}

	public ReferenceItem<InstanceModule> getEnclosingModuleReference() {
		return enclosingModuleReference;
	}
   

  
    
    
}
