/*
 * ConnectorModule2.java
 *
 * Created on March 29, 2007, 9:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.hierarchy;



import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;

/**
 *
 * @author Andy
 * This class and connector holder are really screwed up and contain very similar information
 * and need to be fixed. connectionList is a new version of parent ref which contains 
 * connection information for refactoring
 */
public class ConnectorModule<T extends ConnectorHolder> extends ModuleObjectWithList<T> implements ConnectionWrapper.Con{
    
    /** Reference to the parent instance module */
    private ReferenceItem<InstanceModule> instModRef;
    /** Reference to a list of parent elements */
    private ReferenceItem<ModuleObjectWithList> parentRef;
    /** Creates a new instance of ConnectorModule */
    private ModuleObjectWithList<ConnectorHolder> connectionList;
    
    public ConnectorModule() {}
    public ConnectorModule(String name, ReferenceItem<InstanceModule> instModRef) {
        super(name);
        this.setInstModRef(instModRef);
        init();
    }
    
 
    private void init() {
        ModuleObjectWithList parent = new ModuleObjectWithList("Parent");
        this.setParentRef(parent.createReferenceItem());
        this.connectionList = new ModuleObjectWithList("ConnectionList");
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_CONNECTOR_MODULE;
    }
 
    public PathTreeElement createTree(ModInstanceConnect connect, int depth) {
    	if (depth > 100) return null;
    	PathTreeElement el = new PathTreeElement(this.instModRef.getObject(),connect);
    	
    	for (ReferenceItem<? extends ConnectorHolder> conRef : this.getGenericSelfList()) {
    		ConnectorModule cmod = null;
    		if (conRef.getObject().getModuleRef() != null)     		
    			cmod = conRef.getObject().getModuleRef().getObject();

    		if (this != cmod) {
    			PathTreeElement pathElement = conRef.getObject().createTree(depth+1);
        		if (pathElement != null) el.addElement(pathElement);
    		}
    		else {
    		
    			BaseLog.logInfoAlways("Circular Reference in " + cmod);
    		}
    	}
    	return el;
    }
    
  
    
   
    
    public void deleteObject() {
        this.setParentRef(null);
        super.deleteObject();
    }
    
     public void addReferenceItem(ReferenceItem<? extends T> inval) {
         super.addReferenceItem(inval);
         //this.fireChange();
    }
    
    
    
  
    public ReferenceLocation getEntityLocation() {
    	if (this.getInstModRef() != null) {
    		if (this.getInstModRef().getObject().getEntityReference() != null) {
    			return this.getInstModRef().getObject().getEntityReference().getLocation();
    		}
    	}
    	return null;
    }
    
   
    
   
    public ReferenceItem<InstanceModule> getInstModRef() {
        return instModRef;
    }
    
    public void setInstModRef(ReferenceItem<InstanceModule> instModRef) {
        this.instModRef = instModRef;
    }
    
    public ReferenceItem<ModuleObjectWithList> getParentRef() {
        return parentRef;
    }
    
    public void setParentRef(ReferenceItem<ModuleObjectWithList> parentRef) {
        this.parentRef = parentRef;
    }

    
    public static class NotFound extends ConnectorModule<ConnectorHolder> {
    	public NotFound(String name) {
    		super(name, null);
    	}
    }

    public List<ModInstanceConnect> getRealConnectionList() {
    	List<ModInstanceConnect> connections = new ArrayList<ModInstanceConnect>();
    	for (ReferenceItem<? extends ConnectorHolder> holder : this.getConnectionList().getGenericSelfList()) {
    		if (holder.getObject().getConnectRef() != null) {
    			connections.add(holder.getObject().getConnectRef().getObject());
    		}
    	}
    	return connections;
    }

	public ConnectionWrapper createWrapper() {
		ConnectionWrapper wrap = new ConnectionWrapper(this.instModRef.getObject());
		for (ReferenceItem item : this.getGenericSelfList()) {
			ConnectionWrapper.Con con = (ConnectionWrapper.Con) item.getObject();
			if (con != null) {
				ConnectionWrapper nwrap = con.createWrapper();
				if (nwrap != null) {
					wrap.addObject(nwrap.createReferenceItem());
				}
			}
		}
		return wrap;
	}
	public void setConnectionList(ModuleObjectWithList<ConnectorHolder> connectionList) {
		this.connectionList = connectionList;
	}
	public ModuleObjectWithList<ConnectorHolder> getConnectionList() {
		return connectionList;
	}
	
   
    
    
}
