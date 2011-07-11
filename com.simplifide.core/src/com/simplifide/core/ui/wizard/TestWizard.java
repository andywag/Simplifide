/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.wizard;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.navigator.element.ProjectHolderElement;
import com.simplifide.core.navigator.element.SuiteElement;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.TestGenerator;



public class TestWizard extends BaseNewWizard{

	private TestWizardPage newPage;
	
	
	protected void createMainPage() {
		newPage = new TestWizardPage();
		this.addPage(newPage); 
	}
	
	public EclipseSuite getLocation() {
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		IStructuredSelection sel = (IStructuredSelection) this.getSelection();
		Object obj = sel.getFirstElement();
		if (obj instanceof IProject) {
			IProject proj = (IProject) obj;
			suite = ActiveSuiteHolder.getDefault().getMapSuite(proj.getName());
		}
		else if (obj instanceof SuiteElement) {
			SuiteElement el = (SuiteElement) obj;
			suite = el.getItem().getObject();
		}
		else if (obj instanceof ProjectHolderElement) {
			ProjectHolderElement el = (ProjectHolderElement) obj;
			suite = el.getItem().getObject();
		}
		return suite;
	}
	
	public boolean performFinish() {
		EclipseSuite suite = getLocation();
		IFolder testFolder = suite.getTestFolder();
		TestGenerator.getDefault().createTest(testFolder, newPage.getTestName());
		return true;
	}
	
}
