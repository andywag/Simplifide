/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class BaseNewWizard extends Wizard implements INewWizard{
	
	private IWorkbench workbench;
	private IStructuredSelection selection;
	
	public BaseNewWizard() {}
	
	
	@Override
	public boolean performFinish() {
		return false;
	}

	protected void createMainPage() {
		
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		this.createMainPage();
	}


	public void setWorkbench(IWorkbench workbench) {
		this.workbench = workbench;
	}


	public IWorkbench getWorkbench() {
		return workbench;
	}


	public void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}


	public IStructuredSelection getSelection() {
		return selection;
	}
	
}
