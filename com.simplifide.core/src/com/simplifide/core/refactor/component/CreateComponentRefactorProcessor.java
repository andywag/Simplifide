/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.component;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;

import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.refactor.port.PortRefactorProcessor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;

public class CreateComponentRefactorProcessor extends PortRefactorProcessor{

    private static final String ID = CoreActivator.PLUGIN_ID + "editors.refactor.create.instance";
    
    private ReferenceItem<ModInstanceConnect> connectRef;
    
    private InstanceModule enclosingModule;
    private InstanceModule instanceModule;
    private ModInstanceConnectWrap connectWrap;    
    private ReferenceLocation location;
    
    public CreateComponentRefactorProcessor(InstanceModule enclosingModule,
    		SourceEditor editor,
    		ReferenceLocation location) {
    	
    	super(null,editor);
    	this.setConnectRef(connectRef);
    	this.enclosingModule = enclosingModule;
    	this.location = location;
      	this.createModInstanceConnectWrap();
    }
    
    
    private void createModInstanceConnectWrap() {
    	if (this.connectRef != null) {
    		ModInstanceConnect connect = connectRef.getObject();
    		this.connectWrap = new ModInstanceConnectWrap(connect,this.isVhdlFile());
        	this.instanceModule = (InstanceModule) connect.getEntityRef().getObject().getInstanceModRef().getObject();
    	}
    	else {
    		EclipseBaseProject proj = (EclipseBaseProject) this.enclosingModule.getProjectRef().getObject();
    		EclipseSuite suite = (EclipseSuite) proj.getSuiteReference().getObject();
    		List<InstanceModule> mods = suite.getAllInstanceModules();
    		if (mods.size() > 0) {
        		this.instanceModule = mods.get(0);
        		ModInstanceConnect connect = this.instanceModule.createDefaultConnection();
            	this.connectWrap = new ModInstanceConnectWrap(connect,this.isVhdlFile());
    		}
    	}
    }
    
    public void changeInstanceModule(InstanceModule instanceModule2) {
    	this.instanceModule = instanceModule2;
    	ModInstanceConnect connect = this.instanceModule.createDefaultConnection();
    	this.connectWrap = new ModInstanceConnectWrap(connect,this.isVhdlFile());
    }

    
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
       	ReferenceItem<EntityHolder> entRef = this.instanceModule.getEntityReference();
       	ModInstanceDefault def = (ModInstanceDefault) entRef.getObject().getConnectRef().getObject();
    	ModInstanceWrap wrap = new ModInstanceWrap(def,this.isVhdlFile());
       	
    	List<SourceMatch> match = this.createComponentChange(this.location,wrap);

    	RefactorTreeHolder.Root root = new RefactorTreeHolder.Root();
    	root.addElements(match);    	

    	return this.createRootChange(root);
    }
    
  


    @Override
    public String getIdentifier() {
        return ID;
    }

    @Override
    public String getProcessorName() {
        return "Create Instance";
    }

	public void setConnectRef(ReferenceItem<ModInstanceConnect> connectRef) {
		this.connectRef = connectRef;
	}

	public ReferenceItem<ModInstanceConnect> getConnectRef() {
		return connectRef;
	}

	public void setConnectWrap(ModInstanceConnectWrap connectWrap) {
		this.connectWrap = connectWrap;
	}

	public ModInstanceConnectWrap getConnectWrap() {
		return connectWrap;
	}

	public void setInstanceModule(InstanceModule instanceModule) {
		this.instanceModule = instanceModule;
	}

	public InstanceModule getInstanceModule() {
		return instanceModule;
	}

	public void setEnclosingModule(InstanceModule enclosingModule) {
		this.enclosingModule = enclosingModule;
	}

	public InstanceModule getEnclosingModule() {
		return enclosingModule;
	}

	

    
    
	
	
	
	
}
