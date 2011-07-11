/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.hierarchy;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.util.StringOps;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;


public  class ConnectorHolder extends ModuleObjectNew<ModuleObjectNew> implements ConnectionWrapper.Con{
    
       
        /** Reference to connector module */
        private ReferenceItem<ConnectorModule> moduleRef;
        /** Reference to modinstance connect */
        private ReferenceItem<ModInstanceConnect> connectRef;
        
        public ConnectorHolder(String name) {
        	super(name);
        }
        
        public ConnectorHolder(ReferenceItem<ConnectorModule> moduleRef, ReferenceItem<ModInstanceConnect> connectRef) {
            super(connectRef.getname() + "(" + moduleRef.getname() + ")");
            this.setModuleRef(moduleRef);
            this.setConnectRef(connectRef);
        }
        
        public PathTreeElement createTree(int depth) {
        	if (depth > 20) return null;
        	if (connectRef != null && connectRef.getObject() != null) {
        		ModInstanceConnect connect = connectRef.getObject();
        		ConnectorModule cmod = this.moduleRef.getObject();
        		
            	return cmod.createTree(connect,depth+1);
        	}
        	return null;
        }
        
       
        
        public int getSearchType() {
            return ReferenceUtilities.REF_CONNECTOR_MODULE;
        }
        
        public boolean hasNavigatorChildren() {
        	if (this.moduleRef != null) return this.moduleRef.hasNavigatorChildren();
        	return false;
        }
        @Override
		public List<ReferenceItem> getNavigatorList() {
			if (this.moduleRef != null) return this.moduleRef.getNavigatorList();
			return new ArrayList<ReferenceItem>();
		}
        
        

		

		
        
        
        public void setModuleRef(ReferenceItem<ConnectorModule> moduleRef) {
			this.moduleRef = moduleRef;
		}

		public ReferenceItem<ConnectorModule> getModuleRef() {
			return moduleRef;
		}

		public void setConnectRef(ReferenceItem<ModInstanceConnect> connectRef) {
			this.connectRef = connectRef;
		}

		public ReferenceItem<ModInstanceConnect> getConnectRef() {
			return connectRef;
		}

		public static class NotFound extends ConnectorHolder {
			
			private String moduleName;
			
			public NotFound(String instanceName, String moduleName) {
				super(instanceName);
				this.moduleName = moduleName;
			}
			
			public String getDisplayName() {
				return this.name + StringOps.addParens(this.moduleName);
			}
			
		}

		public ConnectionWrapper createWrapper() {
			if (this.moduleRef == null) return null;
			
			ConnectorModule mod = this.moduleRef.getObject();
			ConnectionWrapper wrap = mod.createWrapper();
			
			return wrap;
		}
		
		
        
    }
    
