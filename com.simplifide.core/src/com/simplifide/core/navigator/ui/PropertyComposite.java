package com.simplifide.core.navigator.ui;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.LibraryGenerator;
import com.simplifide.core.project.generator.ProjectGenerator;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.generator.SuiteGeneratorOptions;
import com.simplifide.core.project.generator.SuiteStructureGenerator;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.ui.wizard.suite_import.FixedLibraryComposite;
import com.simplifide.core.ui.wizard.suite_import.SuiteStructureMainComposite;
import com.simplifide.core.ui.wizard.suite_import.project.ProjectListComposite;

public class PropertyComposite extends Composite {

	
	//private SuiteStructureMainComposite suiteStructComp;
	private FixedLibraryComposite       fixedComposite;
	private ProjectListComposite        projectComposite;
	
	private EclipseSuite suite;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PropertyComposite(Composite parent, int style, EclipseSuite suite) {
		super(parent, style);
		this.suite = suite;
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 0, 600, 400);
		
		TabItem tbtmContent = new TabItem(tabFolder, SWT.NONE);
		tbtmContent.setText("Content");
		this.projectComposite = new ProjectListComposite(tabFolder,SWT.None,suite);
		tbtmContent.setControl(this.projectComposite);
		
		/*
		TabItem tbtmLibraryStructure = new TabItem(tabFolder, SWT.NONE);
		tbtmLibraryStructure.setText("Structure");
		this.suiteStructComp      = new SuiteStructureMainComposite(tabFolder,SWT.NONE,suite);
		tbtmLibraryStructure.setControl(suiteStructComp);
		*/
		
		TabItem tbtmFixedLibrary = new TabItem(tabFolder, SWT.NONE);
		tbtmFixedLibrary.setText("Fixed Library");
		this.fixedComposite      = new FixedLibraryComposite(tabFolder ,SWT.NONE, suite);
		tbtmFixedLibrary.setControl(fixedComposite);
	}
	
	private void updateContent() {
		ArrayList<ProjectGeneratorOptions> projects  = this.projectComposite.getProjectOptions();
		ArrayList<ProjectGeneratorOptions> libraries = this.projectComposite.getLibraryOptions();
		SuiteGeneratorOptions sopt = SuiteGeneratorOptions.getDefaultOptions(false);
		sopt.setNewProjects(projects);
		sopt.setNewLibraries(libraries);
		StructureFile sfile = SuiteStructureGenerator.getDefault().createContentFile(sopt);
		IFile content = suite.getContentXmlFile();
		
		String con = sfile.getContents();
		ByteArrayInputStream bs = new ByteArrayInputStream(con.getBytes());
		try {
			if (content.exists()) {
				content.delete(true, null);
			}
			content.create(bs, true, null);
		} catch (CoreException e) {
				HardwareLog.logError(e);
		}		
	}
	
	private void createProjects() {
		ArrayList<ProjectGeneratorOptions> projects  = this.projectComposite.getProjectOptions();
		ArrayList<ProjectGeneratorOptions> libraries = this.projectComposite.getLibraryOptions();
		IFolder folder = suite.getProjectFolders().get(0);
		for (ProjectGeneratorOptions project : projects) {
			if (project.isNewproject()) {
				ProjectGenerator.getDefault().createProjectfromWizard(folder, project);
			}
		}
		folder = suite.getLibraryFolders().get(0);
		for (ProjectGeneratorOptions project : libraries) {
			if (project.isNewproject()) {
				LibraryGenerator.getDefault().createProjectfromWizard(folder, project);
			}
		}
		
	}
	
	public void okPressed() {
		this.fixedComposite.recreateLibraries(suite);
		this.createProjects();
		this.updateContent();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
