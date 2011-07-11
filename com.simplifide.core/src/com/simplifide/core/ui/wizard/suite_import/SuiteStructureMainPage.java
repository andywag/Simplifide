/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_import;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.core.project.structure.StructureFile;

public class SuiteStructureMainPage extends WizardPage{

	
	private SuiteStructureMainComposite structComposite;
	
	
	public SuiteStructureMainPage() {
		super("Project Structure");
		this.setTitle("Project Structure");
		this.setDescription("Set the directory structure for the project");
	}
	
	public void createControl(Composite parent)  {
		Composite comp = new Composite(parent,SWT.NULL);
		
		this.structComposite = new SuiteStructureMainComposite(comp, SWT.None);
		
		this.setControl(comp);
		
	}
	
	public StructureFile getStructureFile() {
		return structComposite.getStructureXml();
	}
	
	
	

	
	
}
