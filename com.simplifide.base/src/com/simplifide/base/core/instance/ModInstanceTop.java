/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.instance;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.ReferenceItemComparator;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;

public class ModInstanceTop<T extends PortTop> extends ModuleObjectWithList<T> implements java.io.Serializable {
    
   
	private static final long serialVersionUID = 1L;
	private String moduleName;
	
	 /** Pointer to the Port List */
    private ReferenceItem<PortList> portListRef;
    /** Pointer to the Generic List */
    private ReferenceItem<PortList> genericListRef;
  
    public ModInstanceTop(){super();}
    public ModInstanceTop(String moduleName) {
        super(moduleName);
        this.setModuleName(moduleName);
    }
    
    
    
    public int getSearchType() {return ReferenceUtilities.REF_MODINSTANCE_TOP;}
    
    public boolean hasNavigatorChildren() {
    	return true;
    }
    
    public List<ReferenceItem> getNavigatorList() {
        ArrayList<ReferenceItem> outList = new ArrayList();
        if (this.getGenericListRef() != null) outList.add(this.getGenericListRef());
        if (this.getPortListRef() != null) outList.add(this.getPortListRef());
        return outList;
    }
    
    public ReferenceItem<ModuleObjectWithList> getDependants() {
        ModuleObjectWithList outList = new ModuleObjectWithList();
        for (T port : this.getRealSelfList()) {
            int type = port.getSearchType();
            if (type == ReferenceUtilities.REF_PORT_INPUT || type == ReferenceUtilities.REF_PORT_INOUT || type == ReferenceUtilities.REF_PORT_TOP) {
                outList.addAll((ModuleObjectWithList)port.getDependants().getObject());
            }
            
        }
        return outList.createReferenceItem();
    }
    
    public ReferenceItem<ModuleObjectWithList> getOutputs() {
        ModuleObjectWithList outList = new ModuleObjectWithList();
        for (T port : this.getRealSelfList()) {
            int type = port.getSearchType();
            if (type == ReferenceUtilities.REF_PORT_OUTPUT || type == ReferenceUtilities.REF_PORT_TOP) {
                outList.addAll((ModuleObjectWithList)port.getDependants().getObject());
            }
        }
        return outList.createReferenceItem();
    }
    
    public PortTop findPort(String name) {
    	ModuleObjectBaseItem item  = new ModuleObjectBaseItem(name);
    	ReferenceItem ref = new ReferenceItem(name,ReferenceUtilitiesInterface.REF_MODULEOBJECT);
    	ReferenceItem uitem = this.findBaseReference(new ModuleObjectIndexTop(ref,item));
    	if (uitem != null) return (PortTop) uitem.getObject();
    	return null;
    }
    
    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        ReferenceItem outItem = null;
        if (this.getGenericListRef() != null) {
            outItem = this.getGenericListRef().findBaseReference(item);
            if (outItem != null) return outItem;
        }
        if (this.getPortListRef() != null) {
            outItem = this.getPortListRef().findBaseReference(item);
        }
        return outItem;
    }

    public ReferenceItem findSliceReference(ModuleObjectIndexTop item) {
        ReferenceItem outItem = null;
        if (this.getGenericListRef() != null) {
            outItem = this.getGenericListRef().findSliceReference(item);
            if (outItem != null) return outItem;
        }
        if (this.getPortListRef() != null) {
            outItem = this.getPortListRef().findSliceReference(item);
        }
        return outItem;
    }

  

    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        List outList = new ArrayList();
        if (this.getGenericListRef() != null) {
            
            List<? extends ReferenceItem> genList = this.getGenericListRef().findPrefixItemList(item,type);
            outList.addAll(genList);
        }
        if (this.getPortListRef() != null) {
            List<? extends ReferenceItem> genList = this.getPortListRef().findPrefixItemList(item,type);
            outList.addAll(genList);
        }
        Collections.sort(outList,this.getComparator());
              
        return outList;
    }
    
    public static class PortListComparator<T extends PortTop> extends ReferenceItemComparator<T> {
       
        private int checkGeneric(T o1, T o2) {
            if (o1.isGeneric()) {
                if (o2.isGeneric()) return 0;
                else return 1;
            }
            else {
                if (o2.isGeneric()) return -1;
                else return 0;
            }
        }
        
        private int typeCheck(T o1, T o2) {
            return 0;
        }
        
        public int compare(ReferenceItem<T> o1, ReferenceItem<T> o2) {
            T in1 = o1.getObject();
            T in2 = o2.getObject();
            
            int genCheck = checkGeneric(in1,in2);
            if (genCheck != 0) return genCheck;
                    return 0;
        }

    }
    
    public String getModuleName() {
        return moduleName;
    }
    
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
	public void setPortListRef(ReferenceItem<PortList> portListRef) {
		this.portListRef = portListRef;
	}
	public ReferenceItem<PortList> getPortListRef() {
		return portListRef;
	}
	public void setGenericListRef(ReferenceItem<PortList> genericListRef) {
		this.genericListRef = genericListRef;
	}
	public ReferenceItem<PortList> getGenericListRef() {
		return genericListRef;
	}
    
    
    
    
    
  
    
 
    
    
    
    
}
