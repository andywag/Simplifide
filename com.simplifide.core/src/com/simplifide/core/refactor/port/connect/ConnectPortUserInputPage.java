/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port.connect;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.PathTreeElement;

public class ConnectPortUserInputPage extends UserInputWizardPage{

    private ConnectPortRefactoring refactoring;

    
    private ConnectPortInputComposite source;
    private ConnectPortInputComposite destination;
  
    private ConnectPortTreeComposite treeComposite;
    
    protected ArrayList<PathTreeElement> pathList;
    private List<ModuleObject> portList;
    
    public ConnectPortUserInputPage(String name, ConnectPortRefactoring refactoring) {
        super(name);
        this.refactoring = refactoring;
    }

    public void createControl(Composite parent) {
    	
    	this.setTitle("Connect Ports");


        Composite result = new Composite(parent,SWT.None);
        GridLayout gridLayout = new GridLayout( 1, false );
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 10;
        result.setLayout( gridLayout );

        this.setControl(result);
        this.treeComposite = new ConnectPortTreeComposite(result,SWT.NONE);
          
        this.setControl(result);
        
    }
    
    
    private void onFinish() {
    	this.refactoring.getProcessor().setSourceElement(this.treeComposite.getSource());
    	this.refactoring.getProcessor().setDestinationElement(this.treeComposite.getDestination());
    	
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
