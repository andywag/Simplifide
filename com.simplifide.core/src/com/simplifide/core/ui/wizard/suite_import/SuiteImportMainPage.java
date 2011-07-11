/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_import;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.simplifide.base.license.info.HardwareChecker;

public class SuiteImportMainPage extends WizardNewProjectCreationPage{

	private Button defaultStructureButton;
	
	
	public SuiteImportMainPage() {
		super("New Hardware Suite");
		this.setTitle("New Hardware Suite");
		if (!HardwareChecker.isWizardEnabled()) {
			this.setDescription("This wizard is not availog in the free version");
		}
		else {
			this.setDescription("New Hardware Suite");
		}
	}
	
	public void createControl(Composite parent)  {
		super.createControl(parent);
		Composite comp = (Composite) this.getControl();
		
		defaultStructureButton = new Button(comp,SWT.CHECK);
		defaultStructureButton.setText("Default Suite Directory Structure");
		defaultStructureButton.setSelection(true);
	}
	
	public boolean isDefaultStructure() {
		return defaultStructureButton.getSelection();
	}
	
	
	

	
	
}
