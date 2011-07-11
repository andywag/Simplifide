/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.instance.Component;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.freemarker.TemplateGenerator;
import com.simplifide.core.refactor.InstanceWriter;
import com.simplifide.core.refactor.MainRefactorProcessor;
import com.simplifide.core.refactor.changes.PortChangeMethods;
import com.simplifide.core.search.SourceMatch;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class PortRefactorProcessor extends MainRefactorProcessor{

    
    private ReferenceItem<Entity> entityRef;
    private ModInstanceWrap existingInstanceWrap;
    private ModInstanceWrap instanceWrapDelta;
    
      
    public PortRefactorProcessor(ReferenceItem<Entity> entityRef , SourceEditor editor) {
    	super(editor);
    	this.setEntityRef(entityRef);
    }
   
    
    
    
    
  
    
    
    /** Creates a list (singleton) of changes for updating a component */
    protected List<SourceMatch> createComponentChange(ReferenceLocation loc,
    		ModInstanceWrap wrap) {
    	/*
    	String cfile = "refactor/verilog/component";
    	if (this.isVhdlFile()) cfile = "refactor/vhdl/component";
    	String temp = TemplateGenerator.generateTemplate(cfile, wrap);
    	*/
    	String temp = InstanceWriter.createComponent(isVhdlFile(), wrap);
    	if (loc != null) {
    		return this.singleMatch(temp, loc);
    	}
    	return new ArrayList<SourceMatch>();
    }
    
    /** Creates a list (singleton) of changes for updating a entity */
    protected List<SourceMatch> createEntityChange(ReferenceItem<ModInstanceDefault> entModRef,
    		ModInstanceWrap wrap) {
    	String cfile = "refactor/verilog/module";
    	if (this.isVhdlFile()) cfile = "refactor/vhdl/entity";
    	
    	if (entModRef != null) {
    		String temp = TemplateGenerator.generateTemplate(cfile, wrap);
    		return this.singleMatch(temp, entModRef.getLocation());
    	}
    	return null;
    }
    
    /** Creates a list (singleton) of changes for deleting input/output declarations and parameters */
    protected List<SourceMatch> createDeleteIoDecs(HardwareModule umod) {
		ArrayList<ReferenceLocation> locs = umod.getIoDeclarationList();
		if (locs.size() == 0) return new ArrayList<SourceMatch>();
		ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
		for (ReferenceLocation loc : locs) {
			ReferenceUsage use = new ReferenceUsage(null,"",loc);
			matchList.add(new SourceMatch(use));
		}
		return matchList;
    }
    
    /** Creates a list (singleton) of changes instantiations with changed connections */
    protected List<SourceMatch> createConnectionChange(ReferenceLocation location,ModInstanceConnectWrap wrap) {
    	
    	String text = InstanceWriter.createInstance(this.isVhdlFile(), wrap);
    	/*String cfile = "refactor/verilog/instance";
    	if (this.isVhdlFile()) cfile = "refactor/vhdl/instance";
    	String src = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_EXPR);
    	String dest = CoreActivator.getDefault().getPreferenceStore().getString(PreferenceConstants.REFACTOR_REPLACE_DEST);
    	wrap.convertPorts(src, dest);*/
    	if (location != null) {
    		//String temp = TemplateGenerator.generateTemplate(cfile, wrap);
    		return this.singleMatch(text, location);
    	}
    	return new ArrayList<SourceMatch>();
    }
    
    protected List<SourceMatch> createPortChange(IProgressMonitor pm) {
    	ArrayList<SourceMatch> matchList = new ArrayList<SourceMatch>();
    	boolean vhdl = this.isVhdlFile();
    	
        InstanceModule imod = (InstanceModule) this.entityRef.getObject().getInstanceModRef().getObject();
        ReferenceItem<Component> compRef =  imod.getComponentReference();
        ReferenceItem<ModInstanceDefault> entModRef = this.entityRef.getObject().getConnectRef();
        
        // Entity Change
        if (entModRef != null) {
            matchList.addAll(PortChangeMethods.createEntityChange(entModRef.getLocation(),this.existingInstanceWrap,vhdl));
        }
        // Io Conversion for Verilog
        matchList.addAll(PortChangeMethods.createDeleteIoDecs(imod.getArchitecture()));
        
        
        if (compRef != null) {
        	ReferenceItem<ModInstanceDefault> compModRef = compRef.getObject().getConnectRef();
        	matchList.addAll(PortChangeMethods.createComponentChange(compModRef.getLocation(),this.existingInstanceWrap,vhdl));
        }
        return matchList;
    }
    
    /** Creates an indivual connection change when a port is added/subtracted */
    protected List<SourceMatch> createInstanceConnectionChange(ConnectorHolder holder) {
    	boolean vhdl = this.isVhdlFile();
    	ModInstanceConnect connect = holder.getConnectRef().getObject();
    	ModInstanceConnectWrap wrap = (ModInstanceConnectWrap) new ModInstanceConnectWrap(connect, false);
    	return PortChangeMethods.createConnectionChange(connect.getFullLocation(), wrap,vhdl);
    }
    
    protected List<SourceMatch> createConnectionChanges(IProgressMonitor pm) {
        List<SourceMatch> matchList = new ArrayList<SourceMatch>();

    	InstanceModule imod = (InstanceModule) this.entityRef.getObject().getInstanceModRef().getObject();
        ConnectorModule cmod = (ConnectorModule) imod.getConnectReference().getObject();
        ModuleObjectWithList<ConnectorHolder> holderList = cmod.getConnectionList();
        for (ReferenceItem<? extends ConnectorHolder> holderRef : holderList.getGenericSelfList()) {
        	ConnectorHolder holder = holderRef.getObject();
        	matchList.addAll(this.createInstanceConnectionChange(holder));
        }
               
       
        return matchList;
    }
       
   
	public void setEntityRef(ReferenceItem<Entity> entityRef) {
		this.entityRef = entityRef;
	}
	public ReferenceItem<Entity> getEntityRef() {
		return entityRef;
	}
	public void setExistingInstanceWrap(ModInstanceWrap instanceWrap) {
		this.existingInstanceWrap = instanceWrap;
	}
	public ModInstanceWrap getExistingInstanceWrap() {
		return existingInstanceWrap;
	}





	public void setInstanceWrapDelta(ModInstanceWrap instanceWrapDelta) {
		this.instanceWrapDelta = instanceWrapDelta;
	}





	public ModInstanceWrap getInstanceWrapDelta() {
		return instanceWrapDelta;
	}
	
	
	
	
}
