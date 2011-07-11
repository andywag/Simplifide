package com.simplifide.core.ui.wizard.suite_prjfile;

import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.builder.HardwareBuilder;
import com.simplifide.core.project.generator.SuiteGenerator;
import com.simplifide.core.project.generator.SuiteGeneratorOptions;
import com.simplifide.core.ui.wizard.BaseNewWizard;
import com.simplifide.core.ui.wizard.suite_classic.SuiteLibraryPage;

public class VerilogSuiteWizard extends BaseNewWizard {

	public static String ID_WIZARD = CoreActivator.PLUGIN_ID + ".ui.wizard.VerilogSuiteWizard";

	private VerilogSuiteMainPage mainPage;
	
	private VerilogFilePage    filePage;
	private VerilogProjectTopPage libraryPage;
	private SuiteLibraryPage   fixedLibraryPage;
	
	public VerilogSuiteWizard() {
		this.init();
	}
	
	public IWizardPage getNextPage(IWizardPage page) {
		if (page instanceof WizardNewProjectCreationPage) {
			if (mainPage.useProjectFile()) {
				return libraryPage;
			}
		}
		return super.getNextPage(page);
	}
	
	private void init() {
		mainPage = new VerilogSuiteMainPage();
		this.addPage(mainPage);
		
		if (HardwareChecker.isWizardEnabled()) {
			filePage = new VerilogFilePage();
			libraryPage = new VerilogProjectTopPage.Library();
			this.fixedLibraryPage = new SuiteLibraryPage();
		
			this.addPage(filePage);
			this.addPage(libraryPage);
			this.addPage(this.fixedLibraryPage);
		}
		
	}
	
	public boolean performFinish() {
		this.createNewProject();
		return true;
	}
	
	private IProject createNewProject() {
		HardwareBuilder.disableBuild();
        // get a project handle
        final IProject newProjectHandle = mainPage.getProjectHandle();
        
        // get a project descriptor
        URI location = null;
        if (!mainPage.useDefaults()) {
			location = mainPage.getLocationURI();
		}
        
        // Set the project location for non default workspace locations
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
        description.setLocationURI(location);
        
        
        SuiteGeneratorOptions.Libraries lib = this.fixedLibraryPage.getLibraries();
        
        final SuiteGeneratorOptions options = SuiteGeneratorOptions.getDefaultOptions(false);
        
        if (mainPage.useProjectFile()) {
        	options.setProjectFileLocation(mainPage.getProjectFileLocation());
        }
        else {
            options.setAllFiles(filePage.getSourceXML());

        }
        
        options.setLibraries(lib);
        options.setLibraryDirs(libraryPage.getLibraries());
        
        SuiteGenerator.getInstance().createSuite(newProjectHandle, description, options);
        
        /*HardwareBuilder.enableBuild();
        try {
        	EclipseSuite suite = BuildHandler.loadProject(newProjectHandle);
        	ActiveSuiteHolder.getDefault().setSuite(suite);
			newProjectHandle.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
			if (options.getAllFiles() != null) {
				newProjectHandle.build(IncrementalProjectBuilder.CLEAN_BUILD, null);
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}*/
		
        return newProjectHandle;
    }
	
}
