/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TestWizardPage extends WizardPage{

	private Text nameField;
	
	protected TestWizardPage() {
		super("New Test");
		this.setTitle("New Test");
	    setPageComplete(true);
	}

	public String getTestName() {
		return nameField.getText();
	}
	
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent,SWT.NULL);
	    composite.setFont(parent.getFont());
	    GridLayout layout = new GridLayout();
	    layout.numColumns = 2;
	    composite.setLayout(layout);
	    composite.setLayoutData(new GridData(GridData.FILL_BOTH));
	    
	    // Test Name Field
	    Label lab = new Label(composite,SWT.NULL);
	    lab.setText("Test Name");
	    nameField  = new Text(composite,SWT.BORDER);
	    nameField.setText("work");
	    nameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    this.setControl(composite);
		
	}

}
