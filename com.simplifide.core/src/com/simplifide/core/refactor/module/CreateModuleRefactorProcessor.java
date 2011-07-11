/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.module;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.base.refactor.model.ModInstanceWrap;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.port.PortRefactorProcessor;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;

public class CreateModuleRefactorProcessor extends PortRefactorProcessor{

    private static final String ID = CoreActivator.PLUGIN_ID + "editors.refactor.create.instance";
    
    private ReferenceItem<ModInstanceConnect> connectRef;
    
    private InstanceModule enclosingModule;
    private ReferenceLocation location;
    private ModInstanceWrap wrap;
    
    public CreateModuleRefactorProcessor(InstanceModule enclosingModule,
    		SourceEditor editor,
    		ReferenceLocation location) {
    	
    	super(null,editor);
    	this.setConnectRef(connectRef);
    	this.enclosingModule = enclosingModule;
    	this.location = location;
    }
    
    
   
    
   
    
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
    	if (this.wrap.getName().equalsIgnoreCase("")) {
    		this.wrap.setName("newModule");
    	}
    	ModInstanceConnectWrap wr = wrap.createSimpleWrap();
    	List<SourceMatch> match = this.createConnectionChange(this.location, wr);
    	RefactorTreeHolder.Root root = new RefactorTreeHolder.Root();
    	root.addElements(match);    	
    	Change ch = this.createRootChange(root);
    	Change modch = new CreateModuleChange(this.wrap,this.enclosingModule);
    	CompositeChange comp = new CompositeChange("Create Module");
    	comp.add(ch);
    	comp.add(modch);
    	return comp;
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

	

	public void setEnclosingModule(InstanceModule enclosingModule) {
		this.enclosingModule = enclosingModule;
	}

	public InstanceModule getEnclosingModule() {
		return enclosingModule;
	}

	public void setWrap(ModInstanceWrap wrap) {
		this.wrap = wrap;
	}

	public ModInstanceWrap getWrap() {
		return wrap;
	}
}
