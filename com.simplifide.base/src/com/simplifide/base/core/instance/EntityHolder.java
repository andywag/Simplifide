/*
 * InstanceHolder.java
 *
 * Created on March 29, 2007, 9:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.instance;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.api.template.TemplateCompletionInterface;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.SystemVar;

/**
 *
 * @author Andy
 */
public class EntityHolder<T extends ModuleObjectNew> extends ModuleObjectWithList<T> implements TemplateCompletionInterface{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Pointer to the instance module parent */
    private ReferenceItem<InstanceModule> instanceModRef;
    /** Pointer to the module instance */
    private ReferenceItem<ModInstanceDefault> connectRef;
    
    private ReferenceLocation lastGenericLocation;
    private ReferenceLocation lastPortLocation;
    
    /** Creates a new instance of InstanceHolder */
    public EntityHolder() {}
    public EntityHolder(String moduleName) {super(moduleName);}
   
    
    
    /** Create an initial instance for this module */
    public ModInstanceConnect createDefaultConnection() {
    	ReferenceItem<ModInstanceDefault> con = this.connectRef;
    	
    	if (this.connectRef != null) {
    		ModInstanceDefault def = this.getConnectRef().getObject();
        	return def.createDefaultConnection(this);
    	}
    	return null;
    	
    	
    }
    
    public ModuleObject getBaseSearchClass() {
    	return this.getInstanceModRef().getObject();
    }
    
    /** Returns a list of variables defined in this entity */
    public List<SystemVar> getVars() {
    	List<PortTop> portList = this.getPortList();
    	if (portList == null) return new ArrayList<SystemVar>();
    	ArrayList<SystemVar> varList = new ArrayList<SystemVar>();
    	for (PortTop port : portList) {
    		varList.add(port.getLocalVar());
    	}
    	return varList;
    }
    
    /** Returns a port with the name portName */
    public PortTop findPort(String portName) {
    	ModInstanceDefault def = this.connectRef.getObject();
    	return def.findPort(portName);
    }
    
    public List<PortTop> getPortList() {
    	if (this.connectRef != null) {
    		ModInstanceDefault def = connectRef.getObject();
    		if (def.getPortListRef() != null) {
    			PortList<PortTop> plist = (PortList<PortTop>) def.getPortListRef().getObject();
    			return plist.getRealSelfList();
    		}
    	}
    	return null;
    }
    
    public List<PortTop> getGenericList() {
    	if (this.connectRef != null) {
    		ModInstanceDefault def = connectRef.getObject();
    		if (def.getGenericListRef() != null) {
    			PortList<PortTop> plist = (PortList<PortTop>) def.getGenericListRef().getObject();
    			return plist.getRealSelfList();
    		}
    	}
    	return null;
    }
    
    
    
    public void updateHdlDoc(ModuleObject parent) {
    	if (this.connectRef != null) {
    		this.connectRef.getObject().updateHdlDoc(this);
    	}
    }
    
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        if (this.connectRef != null) {
            return this.connectRef.findBaseReference(item);
        }
        return null;
    }
    
     public ReferenceItem findSliceReference(ModuleObjectIndexTop item) {
          if (this.connectRef != null) {
            return this.connectRef.findSliceReference(item);
        }
        return null;
     }
    
      public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        if (this.connectRef != null) {
            return this.connectRef.findPrefixItemList(item,type);
        }
        return new ArrayList<ReferenceItem>();
    }
      
    public boolean hasNavigatorChildren() {
    	if (this.connectRef != null && this.connectRef.hasNavigatorChildren()) return true;
    	return false;
    }
    public List<ReferenceItem> getNavigatorList() {
        if (this.connectRef != null) {
            return this.connectRef.getNavigatorList();
        }
        return new ArrayList<ReferenceItem>();
    }

    
    
    
    public String getHtmlDocName() {
        String outType;
        outType = "<b> " + this.getname() + "</b>\n";
        outType += this.getConnectRef().getObject().getHtmlDocName();
        
        return "<pre>" + outType + "</pre>";
     }

    public ReferenceItem<InstanceModule> getInstanceModRef() {
        return instanceModRef;
    }

    public void setInstanceModRef(ReferenceItem<InstanceModule> instanceModRef) {
        this.instanceModRef = instanceModRef;
    }

    public ReferenceItem<ModInstanceDefault> getConnectRef() {
        return connectRef;
    }

    public void setConnectRef(ReferenceItem<ModInstanceDefault> connectRef) {
        this.connectRef = connectRef;
    }
    
	public String getTemplateDescription() {
		return "";
	}
	public String getTemplateDisplayName() {
		return this.getDisplayName();
	}
	public String getTemplatePattern() {
		String outPattern = "";
		if (this.getConnectRef() != null) {
			outPattern += this.getConnectRef().getObject().getTemplatePattern(); 
		}
		return outPattern;
	}
	public boolean isAutoComplete() {
		return false;
	}

	public void setLastGenericLocation(ReferenceLocation lastGenericLocation) {
		this.lastGenericLocation = lastGenericLocation;
	}
	public ReferenceLocation getLastGenericLocation() {
		return lastGenericLocation;
	}

	public void setLastPortLocation(ReferenceLocation lastPortLocation) {
		this.lastPortLocation = lastPortLocation;
	}
	public ReferenceLocation getLastPortLocation() {
		return lastPortLocation;
	}
    
   
    
}
