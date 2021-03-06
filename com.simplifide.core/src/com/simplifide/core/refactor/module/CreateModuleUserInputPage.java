/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.module;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;

public class CreateModuleUserInputPage extends UserInputWizardPage{

    private CreateModuleRefactoring refactoring;
    private CreateModuleComposite composite;

    
    protected ArrayList<PathTreeElement> pathList;
    private List<ModuleObject> portList;
    
    public CreateModuleUserInputPage(String name, CreateModuleRefactoring refactoring) {
        super(name);
        this.refactoring = refactoring;
    }

    
    public void createControl(Composite parent) {
    	
    
    	InstanceModule instMod = refactoring.getProcessor().getEnclosingModule();
    	composite = new CreateModuleComposite(parent,SWT.None,instMod);  
    	this.setControl(composite);
    }
    
    
    private void onFinish() {
    	boolean vhdl = this.refactoring.getProcessor().isVhdlFile();
    	this.refactoring.getProcessor().setWrap(composite.getInstanceWrap(vhdl));
    }
    
    
    public IWizardPage getNextPage() {
    	this.onFinish();
        return super.getNextPage();
    }

    protected boolean performFinish() {
    	this.onFinish();
    	return super.performFinish();
    }
    
  
    
    
}
