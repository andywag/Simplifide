/*
 * FunctionHolder.java
 *
 * Created on March 6, 2007, 5:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.newfunction;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectComparator;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.finder.index.ModuleObjectIndexTop;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;

/**
 *
 * @author awagner
 *
 * This class holds a set of functions which numerous functions with different type
 * parameters
 */
public class FunctionHolder<T extends InstanceFunction> extends ModuleObjectWithList<T> {
    
	private String classPrefix;
	
    /** Creates a new instance of FunctionHolder */
    public FunctionHolder(String name) {super(name);}
    
    
    public String getDisplayName() {
    	if (classPrefix != null) {
    		return this.classPrefix + "::" + super.getDisplayName();
    	}
    	return super.getDisplayName();
    }
    
    public ModuleObject getBaseSearchClass() {
    	if (this.getnumber() > 0) return this.getObject(0).getObject();
    	return this;
    }
    
    public int getSearchType() {
        return ReferenceUtilities.REF_FUNCTION_HOLDER;
    }

    protected ModuleObjectComparator getComparator() {
        return FunctionComparator.getBaseInstance();
    }

    public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
        return null;
    }
    

    public TypeVar[] getTypeofInput(int index) {
    	ArrayList<TypeVar> types = new ArrayList<TypeVar>();
    	for (ReferenceItem<? extends T> instR : this.getGenericSelfList()) {
    		T inst = instR.getObject();
    		List<PortTop> ports = inst.getPorts();
    		if (ports.size() > index) {
    			TypeVar type = ports.get(index).getType();
    			types.add(type);
    		}
    	}
    	return types.toArray(new TypeVar[types.size()]);
    }
    
    public ReferenceItem findSliceReference(ModuleObjectIndexTop top) {
        
     
        
        FunctionPortList ports = new FunctionPortList("PortList");
        ModuleObject obj1 = top.getBase().getObject();
        if (obj1 instanceof NoSortList) {
        	ReferenceItem<NoSortList<ReferenceItem<? extends ModuleObject>>> list = top.getBase();
            for (ReferenceItem<? extends ModuleObject> obj : list.getObject().getGenericSelfList()) {
                PortConnect connect = new PortConnect(obj.getname(), obj);
                ports.addObject(connect.createReferenceItem());
            }
        }
        
        
        FunctionDeclaration dec = new FunctionDeclaration(this.getname(),null,ports.createReferenceItem());
        BaseFunction base = new BaseFunction(dec.createReferenceItem());
        ReferenceItem<? extends T> inst =  this.findReference(base.createReferenceItem());
        if (inst != null) {
            FunctionCall call = new FunctionCall(inst.getObject(),ports);
            return call.createReferenceItemWithLocation(inst.getLocation());
        }
        return null;
    }

    public List<ReferenceItem> findPrefixItemList(ModuleObjectFindItem item, int type) {
        List ulist = this.getGenericSelfList();
        return (List<ReferenceItem>) ulist;
    }
    
    public List<ReferenceItem> getHyperlinkItemList(ReferenceItem item)
    {
    	ArrayList refList = new ArrayList();
    	for (ModuleObjectNew ref : this.getRealSelfList()) {
    		List<ReferenceItem> nlist = ref.getHyperlinkItemList(item);
    		refList.addAll(nlist);
    	}
       
        return (List<ReferenceItem>) refList;
    }


	public void setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
	}


	public String getClassPrefix() {
		return classPrefix;
	}
    
    
}
