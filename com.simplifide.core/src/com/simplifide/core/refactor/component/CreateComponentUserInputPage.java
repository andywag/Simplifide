/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.component;

import java.util.ArrayList;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;

public class CreateComponentUserInputPage extends UserInputWizardPage{

    private CreateComponentRefactoring refactoring;
    private CreateComponentMainComposite main;
    

    
    protected ArrayList<PathTreeElement> pathList;
    
    public CreateComponentUserInputPage(String name, CreateComponentRefactoring refactoring) {
        super(name);
        this.refactoring = refactoring;
    }

    public void changeInstanceModule(InstanceModule instanceModule) {
    	this.refactoring.getProcessor().changeInstanceModule(instanceModule);
    	this.main.updateInstanceModule(this.refactoring.getProcessor().getInstanceModule(), 
    			this.refactoring.getProcessor().getConnectWrap());
    }
    
    public void createControl(Composite parent) {
    	
    	
    	Composite baseComp = new Composite(parent,SWT.None);
    	baseComp.setLayout(new GridLayout());

    	InstanceModule encMod = refactoring.getProcessor().getEnclosingModule();
    	InstanceModule instMod = refactoring.getProcessor().getInstanceModule();
    	ModInstanceConnectWrap wrap = refactoring.getProcessor().getConnectWrap();
    	
    	
    	GridData mainData = new GridData(SWT.None);
    	mainData.heightHint = 100;
    	main = new CreateComponentMainComposite(baseComp,SWT.NONE,wrap,instMod,this);
    	main.setLayoutData(mainData);
    	this.setControl(baseComp);


    	
    }
    
    
    private void onFinish() {
    	//this.refactoring.getProcessor().setExistingInstanceWrap(comp.getTotalInstanceWrap());
    	//this.refactoring.getProcessor().setInstanceWrapDelta(comp.getDeltaInstanceWrap());
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
