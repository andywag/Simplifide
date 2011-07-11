/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard.suite_classic;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.project.structure.StructureFile;

public class SuiteMainPage extends WizardNewProjectCreationPage{

	private Button existingButton;
	
	private XmlFileComposite structureComposite;
	private XmlFileComposite contentsComposite;
	private XmlFileComposite sourceComposite;
	
	public SuiteMainPage() {
		super("New Hardwware Suite");
		this.setTitle("New Hardware Suite");
		this.setDescription("New Hardware Suite");
	}
	
	public void createControl(Composite parent)  {
		super.createControl(parent);
		Composite comp = (Composite) this.getControl();

		existingButton = new Button(comp,SWT.CHECK);
		existingButton.setText("Existing Directory and Files");
		
		this.setSourceComposite(new XmlFileComposite(comp,SWT.None,"List of Design Files"));		
		this.structureComposite = new XmlFileComposite(comp,SWT.None,"Non Default Directory Structure");
		this.contentsComposite = new XmlFileComposite(comp,SWT.None,"Non Default Suite Contents");
		
		if (!HardwareChecker.isProjectEnabled()) {
			this.getSourceComposite().setEnabled(false);
			this.structureComposite.setEnabled(false);
			this.contentsComposite.setEnabled(false);
		}
		
	}
	
	/** Returns the location of the structure file */
	
	public StructureFile getSourceFile() {
		return this.sourceComposite.getXmlFile();
	}
	
	public StructureFile getStructureFile() {
		return this.structureComposite.getXmlFile();
	}
	public StructureFile getContentsFile() {
		return this.contentsComposite.getXmlFile();
	}
	
	
	public boolean existingDirectory() {
		return existingButton.getSelection();
	}

	public void setSourceComposite(XmlFileComposite sourceComposite) {
		this.sourceComposite = sourceComposite;
	}

	public XmlFileComposite getSourceComposite() {
		return sourceComposite;
	}
	
	
	
	/*public boolean isVhdlSelected() {
		return vhdlButton.getSelection();
	}*/
	
	
}
