/*
 * ParseContextInterface.java
 *
 * Created on September 27, 2006, 8:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.pythonext.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceHandler;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;

/**
 *
 * @author Andy Wagner
 */
public final class PythonSearch implements ReferenceUtilitiesInterface{
    
    private static PythonSearch instance;
    private ReferenceHandler reference;
    /** Creates a new instance of ParseContextInterface */
    private PythonSearch() {}
   
    
    public static PythonSearch getInstance()
    {
        if (instance == null) instance = new PythonSearch();
        return instance;
    }
    

    public ReferenceItem findGlobalReference(String ustring, int type) {
        return reference.findGlobalObject(this.decodeString(ustring),type);
    }
    
    public ReferenceItem findProjectReference(String ustring, int type) {
        return reference.findProjectObject(this.decodeString(ustring),type);
    }
    
    
    public ReferenceItem findContextReference(String ustring, int type)
    {
        return reference.findContextObject(this.decodeString(ustring),type);
    }
    public ReferenceItem findLocalReference(String ustring, int type)
    {
        return reference.findLocalObject(this.decodeString(ustring),type);
    }
      
    public ModuleObject findVar(String name) {
    	ReferenceItem ref = this.findContextReference(name, ReferenceUtilities.REF_MODULEOBJECT);
    	if (ref == null) return null;
    	if (ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_PORT_TOP) == 0 ||
    		ReferenceUtilities.checkType(ref.getType(), ReferenceUtilities.REF_SYSTEMVAR) == 0) {
    		return ref.getObject();
   
    	}
    	return null;
    }
    
    private ModInstanceDefault checkEntity(ReferenceItem ref) {
    	if (ref == null) return null;
    	if (ref.getObject() instanceof InstanceModule) {
    		InstanceModule inst = (InstanceModule) ref.getObject();
        	EntityHolder holder = (EntityHolder) inst.getEntityReference().getObject();
        	return (ModInstanceDefault) holder.getConnectRef().getObject();
    	}
    	else if (ref.getObject() instanceof EntityHolder) {
    		EntityHolder holder = (EntityHolder) ref.getObject();
        	return (ModInstanceDefault) holder.getConnectRef().getObject();
    	}
    	return null;
    }
    
    public ModInstanceDefault findEntity(String name) {
    	
    	ReferenceItem ref = this.findGlobalReference(name, ReferenceUtilitiesInterface.REF_MODULEOBJECT);
    	ModInstanceDefault def = this.checkEntity(ref);
    	if (def != null) return def;
    	
    	ref = this.findProjectReference(name, ReferenceUtilitiesInterface.REF_INSTANCE_MODULE);
    	def = this.checkEntity(ref);
    	return def;
    	
    	
    }
    
    public List<ModInstanceDefault> getProjectEntities() {
    	
    	ArrayList<ModInstanceDefault> outList = new ArrayList<ModInstanceDefault>();
    	List<ReferenceItem<InstanceModule>> entities = this.reference.getProjectReference().findPrefixItemList("", ReferenceUtilities.REF_INSTANCE_MODULE);
    	for (ReferenceItem<InstanceModule> entityRef : entities) {
    		InstanceModule inst = entityRef.getObject();
    		ModInstanceDefault def = (ModInstanceDefault) inst.getEntity().getConnectRef().getObject();
    		outList.add(def);
    	}
    	return outList;
    	
    }
    
    
    public ModuleObject findGlobalObject(String ustring, int type)
    {
    	ReferenceItem ref = this.findGlobalReference(ustring, type);
    	if (ref != null) return ref.getObject();
    	return null;
        
    }
    public ModuleObject findContextObject(String ustring, int type)
    {
        return this.findContextReference(ustring,type);
    }
    public ModuleObject findLocalObject(String ustring, int type)
    {
        return this.findLocalReference(ustring,type);
    }
    
    private ModuleObjectFindItem decodeString(String instring)
    {
        StringTokenizer tok = new StringTokenizer(instring,".");
        ModuleObjectBaseItem baseItem = new ModuleObjectBaseItem(tok.nextToken());
        ModuleObjectBaseItem currentItem = baseItem;
        while (tok.hasMoreTokens())
        {
            ModuleObjectBaseItem nitem = new ModuleObjectBaseItem(tok.nextToken());
            currentItem.setNext(nitem);
            currentItem = nitem;
        }
        return baseItem;
    }
    
    
    public ReferenceHandler getReference() {
        return reference;
    }

    public void setReference(ReferenceHandler reference) {
        this.reference = reference;
    }

    
    
}
