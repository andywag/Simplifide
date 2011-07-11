/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.dialog;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.WorkbenchException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.ui.perspective.HardwarePerspectiveFactory;

public class OpenHardwareDialog extends Dialog
{
	
	private IWorkbench workbench;

	public OpenHardwareDialog(IWorkbench workbench) {
		super(workbench.getActiveWorkbenchWindow().getShell());
		this.workbench = workbench;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite container = (Composite) super.createDialogArea(parent);
		Label nameLabel = new Label(container,SWT.LEFT);
		nameLabel.setText("Open the Perpective Related to this Project");
		return container;
	}

	@Override
	protected void okPressed() {
		try {
			workbench.showPerspective(HardwarePerspectiveFactory.ID, workbench.getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
                    HardwareLog.logError(e);
		}
		super.okPressed();
	}
	
	
	
	

	

}
