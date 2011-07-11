/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.function.change;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;

public class AddPortUserInputPage extends UserInputWizardPage{

    private AddPortRefactoring refactoring;

    
    //private AddPortInputComposite source;
    //private AddPortInputComposite destination;
    private AddPortTopComposite comp;
    
    protected ArrayList<PathTreeElement> pathList;
    private List<ModuleObject> portList;
    
    public AddPortUserInputPage(String name, AddPortRefactoring refactoring) {
        super(name);
        this.refactoring = refactoring;
    }

    public void createControl(Composite parent) {
    	ReferenceItem<Entity> entityRef = this.refactoring.getProcessor().getEntityRef();
    	
    	if (entityRef != null)
    		this.setTitle("Add Port to " + this.refactoring.getProcessor().getEntityRef().getDisplayName());
        
    	
        Composite result = new Composite(parent,SWT.None);
        GridLayout gridLayout = new GridLayout( 1, false );
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 10;
        result.setLayout( gridLayout );

        //this.setControl(result);

        Entity ent = refactoring.getProcessor().getEntityRef().getObject();
        InstanceModule imod = (InstanceModule) ent.getInstanceModRef().getObject();
        
        this.comp = new AddPortTopComposite(result,SWT.None,
        		(ModInstanceDefault)ent.getConnectRef().getObject(),
        		imod,
        		this.refactoring.getProcessor().isVhdlFile());          
        this.setControl(result);
        
    }
    
    
    private void onFinish() {
    	this.refactoring.getProcessor().setExistingInstanceWrap(comp.getTotalInstanceWrap());
    	this.refactoring.getProcessor().setInstanceWrapDelta(comp.getDeltaInstanceWrap());
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
