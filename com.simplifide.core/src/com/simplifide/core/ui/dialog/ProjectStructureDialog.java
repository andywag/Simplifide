/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.dialog;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;

public class ProjectStructureDialog extends Dialog
{
	
	
	private Label nameLabel;
	
	public static boolean ONESHOT = false;

	public ProjectStructureDialog(IWorkbench workbench) {
		super(workbench.getActiveWorkbenchWindow().getShell());

	}

	
	public boolean shouldShow() {
		//return true;
		if (!ONESHOT) return true;
		return false;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite container = (Composite) super.createDialogArea(parent);
		GridData dGrid = new GridData();
		dGrid.horizontalSpan = 180;
		dGrid.horizontalAlignment = GridData.FILL;
		container.setLayoutData(dGrid);
		
		nameLabel = new Label(container,SWT.LEFT);
		nameLabel = new Label(container,SWT.LEFT);
		String labelText = "Most of the features for SimplifIDE require knowledge of the project structure.\r\n";
		labelText += "Currently you are editting a file outside of the project where many features will not work properly.\r\n";
		labelText += "Instructions for setting up your project can be found at http://simplifide.com/html2/project_structure/simplifide_structure.htm, or\r\n";
		labelText += "for a simple project only containing rtl files at http://simplifide.com/html2/getting_started/simple_suite.htm.\r\n";
		nameLabel.setText(labelText);
		
		this.ONESHOT = true;
		
		return container;
	}

	@Override
	protected void okPressed() {
		
		super.okPressed();
	}
		

}
