/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.dialog;




import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;

import com.simplifide.core.baseeditor.source.SourceFile;

public class FileTooLargeDialog extends Dialog
{

	
	private IWorkbench workbench;
	private SourceFile sfile;
	private Button button;

	
	
	public FileTooLargeDialog(IWorkbench workbench, SourceFile sfile) {
		super(workbench.getActiveWorkbenchWindow().getShell());
		this.workbench = workbench;
		this.sfile = sfile;
		
	}

	private String getDialogText() {
		String di = "The file " + sfile.getname() + " is a large file which may cause issues with performance. If this is a netlist or\n";
		di += " other non required file, this shoud be removed from the design by checking the box below.";
		return di;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		// TODO Auto-generated method stub
		Composite container = (Composite) super.createDialogArea(parent);
		Label nameLabel = new Label(container,SWT.LEFT);
		nameLabel.setText(this.getDialogText());
		
		this.button =  new Button(container,SWT.CHECK);
		this.button.setText("Remove File From Project");
		
		return container;
	}

	@Override
	protected void okPressed() {
		if (this.button.getSelection()) {
			this.sfile.setIgnoredFile(true);
		}
		else {
			this.sfile.setIgnoredFile(false);
		}
		super.okPressed();
	}
	
	
	
	

	

}
