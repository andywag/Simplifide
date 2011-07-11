/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.stream;

import java.util.ArrayList;
import java.util.List;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.stream.EntityStore.IOList;

public abstract class ConnectionStore {

	private List<EntityStore> entityList = new ArrayList<EntityStore>();
	
	public ConnectionStore() {}
	public ConnectionStore(SuperModule smod) {
		this.createConnectionStore(smod);
	}
	
	private void createConnectionStore(SuperModule<ModuleObjectNew> smod) {
		
		for (ReferenceItem ref : smod.getGenericSelfList()) {
			if (ref.getSearchType() == ReferenceUtilities.REF_ENTITY) {
				entityList.add(this.createEntityStore((Entity)ref.getObject()));
			}
		}
	}
	
	public abstract EntityStore createEntityStore(Entity entity);
	
	
	public EntityStore getEntityStore(ParseContext context) {
		ModuleObject obj = context.getRefHandler().getActiveReference().getObject();
		HardwareModule mod = null;
		if (obj instanceof Entity) {
			Entity ent = (Entity) obj;
			return getEntityStore(ent);
		}
		else if (obj instanceof HardwareModule) {
			mod = (HardwareModule) obj;
			InstanceModule instMod = (InstanceModule) mod.getInstModRef().getObject();
			Entity ent = (Entity) instMod.getEntityReference().getObject();
			return getEntityStore(ent);
		}
		else {
			return null;
		}
		
	}
	
	private EntityStore getEntityStore(Entity entity) {
		for (EntityStore estore : entityList) {
			if (estore.getEntity().getname().equals(entity.getname())) {
				return estore;
			}
		}
		return null;
	}
	
	public String handleAutoWire(ParseContext context) {
		EntityStore ent = this.getEntityStore(context);
		if (ent != null) return ent.handleAutoWire();
		return "";
	}
	
	public String handleAutoInput(ParseContext context) {
		EntityStore ent = this.getEntityStore(context);
		if (ent != null) return ent.handleAutoInput();
		return "";
	}
	
	public String handleAutoOutput(ParseContext context) {
		EntityStore ent = this.getEntityStore(context);
		if (ent != null) return ent.handleAutoOutput();
		return "";
	}
	
	public EntityStore.IOList handleAutoArgs(TemplateContents cont, ParseContext context) {
		if (!(context.getActiveReference().getObject() instanceof Entity)) {
			BaseLog.logInfo("Verilog Emacs Generator Broken" + context.getRefHandler().getSuperModuleReference());
		}
		Entity ent = (Entity) context.getActiveReference().getObject();
		return this.getEntityStore(ent).createAutoArgs();
		
	}
	
	private static Entity getEntityForInstance(ParseContext context) {
		ReferenceItem ref = context.getRefHandler().getSecondaryReference();
		if (ref == null) return null;
		ModuleObject object = ref.getObject();
		if (object instanceof Entity) return (Entity) object;
		ref = context.getActiveReference();
		if (ref == null) return null;
	    object = ref.getObject();
		if (object instanceof Entity) return (Entity) object;
		return null;
	}
	
	public static EntityStore.IOList handleAutoInstance(TemplateContents cont, ParseContext context) {
			
		//Entity ent = (Entity) context.getRefHandler().getSecondaryReference().getObject();
		Entity ent = getEntityForInstance(context);
		if (ent == null) return null;
		ModInstanceDefault def = (ModInstanceDefault) ent.getConnectRef().getObject();
		PortList plist = (PortList) def.getPortListRef().getObject();
		List<PortTop> portList = plist.getInputOutputOrderedList();
		IOList ioList = new IOList();
		
		for (PortTop port : portList) {
			if (port.getSearchType() == ReferenceUtilities.REF_PORT_INPUT) {
				ioList.inputList.add(port.getname());
			}
			else if (port.getSearchType() == ReferenceUtilities.REF_PORT_OUTPUT) {
				ioList.outputList.add(port.getname());
			}
		}
		
		return ioList;
		
	}
	
	
	
	
	
	public void setEntityList(List<EntityStore> entityList) {
		this.entityList = entityList;
	}

	public List<EntityStore> getEntityList() {
		return entityList;
	}

	
	
}
