/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.instance;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;

public class CreateInstanceUserInputPage extends UserInputWizardPage{

    private CreateInstanceRefactoring refactoring;
    private CreateInstanceMainComposite main;
    private InstancePortTableComposite table;
    

    
    protected ArrayList<PathTreeElement> pathList;
    private List<ModuleObject> portList;
    
    public CreateInstanceUserInputPage(String name, CreateInstanceRefactoring refactoring) {
        super(name);
        this.setRefactoring(refactoring);
    }

    public void changeInstanceModule(InstanceModule instanceModule) {
    	this.getRefactoring().getProcessor().changeInstanceModule(instanceModule);
    	this.main.updateInstanceModule(this.getRefactoring().getProcessor().getInstanceModule(), 
    			this.getRefactoring().getProcessor().getConnectWrap());
    	this.table.updateInstanceModule(this.getRefactoring().getProcessor().getConnectWrap());
    }
    
    public void createControl(Composite parent) {
    	
    	Composite baseComp = new Composite(parent,SWT.None);
    	baseComp.setLayout(new GridLayout());

    	InstanceModule encMod = getRefactoring().getProcessor().getEnclosingModule();
    	InstanceModule instMod = getRefactoring().getProcessor().getInstanceModule();
    	ModInstanceConnectWrap wrap = getRefactoring().getProcessor().getConnectWrap();
    	
    	
    	GridData mainData = new GridData(SWT.None);
    	mainData.heightHint = 80;
    	main = new CreateInstanceMainComposite(baseComp,SWT.NONE,wrap,instMod,this);
    	main.setLayoutData(mainData);

    	GridData tableData = new GridData(SWT.None);
    	tableData.grabExcessHorizontalSpace = true;
    	tableData.grabExcessVerticalSpace = true;
    	tableData.heightHint = 600;
    	table = new InstancePortTableComposite(baseComp,SWT.NONE,wrap,encMod);
    	table.setLayoutData(tableData);
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

	public void setRefactoring(CreateInstanceRefactoring refactoring) {
		this.refactoring = refactoring;
	}

	public CreateInstanceRefactoring getRefactoring() {
		return refactoring;
	}
    
  
    
    
}
