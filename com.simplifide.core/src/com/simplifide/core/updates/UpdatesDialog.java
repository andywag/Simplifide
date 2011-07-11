/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.updates;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;

public class UpdatesDialog extends Dialog
{
	
	private IWorkbench workbench;

	public UpdatesDialog(IWorkbench workbench) {
		super(workbench.getActiveWorkbenchWindow().getShell());
		this.workbench = workbench;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite container = (Composite) super.createDialogArea(parent);
		Label nameLabel = new Label(container,SWT.LEFT);
		nameLabel.setText("A newer version of the SimplifIDE plugin is available.\n.");
		return container;
	}

	
	
	
	
	

	

}
