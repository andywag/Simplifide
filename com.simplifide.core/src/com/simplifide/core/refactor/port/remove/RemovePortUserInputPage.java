/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.refactor.port.remove;

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
import com.simplifide.base.core.reference.ReferenceItem;

public class RemovePortUserInputPage extends UserInputWizardPage{

    private RemovePortRefactoring refactoring;

    
    //private AddPortInputComposite source;
    //private AddPortInputComposite destination;
    private RemovePortTopComposite comp;
    
    protected ArrayList<PathTreeElement> pathList;
    private List<ModuleObject> portList;
    
    public RemovePortUserInputPage(String name, RemovePortRefactoring refactoring) {
        super(name);
        this.refactoring = refactoring;
    }

    public void createControl(Composite parent) {
    	ReferenceItem<Entity> entityRef = this.refactoring.getProcessor().getEntityRef();
    	if (entityRef != null)
    		this.setTitle("Remove Port from " + entityRef.getDisplayName());
        
    	
        Composite result = new Composite(parent,SWT.None);
        GridLayout gridLayout = new GridLayout( 1, false );
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 10;
        result.setLayout( gridLayout );

        this.setControl(result);

        Entity ent = refactoring.getProcessor().getEntityRef().getObject();
        
        this.comp = new RemovePortTopComposite(result,SWT.None,(ModInstanceDefault)ent.getConnectRef().getObject(),this.refactoring.getProcessor().isVhdlFile());          
        this.setControl(result);
        
    }
    
    
    private void onFinish() {
    	this.refactoring.getProcessor().setExistingInstanceWrap(comp.getWrapper());
    	this.refactoring.getProcessor().setInstanceWrapDelta(comp.getDelta());
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
