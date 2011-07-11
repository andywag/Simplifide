/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.simplifide.base.BaseActivator;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.project.EclipseSuite;

public class GenerateHdlDocAction implements IWorkbenchWindowActionDelegate {

    public GenerateHdlDocAction() {
      
        
    }
    
    public void dispose() {
        // TODO Auto-generated method stub

    }

    public void init(IWorkbenchWindow window) {
        // TODO Auto-generated method stub

    }

    public void run(IAction action) {
        if (!BaseActivator.RELEASE) {
            EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
            if (suite != null) {
                suite.generateDoc();
            }
        }
        
    }

    public void selectionChanged(IAction action, ISelection selection) {
        

    }

}
