/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.wizards.WizardShortcutAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.ConfigurationDirectoryLoader;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.builder.HardwareBuilder;
import com.simplifide.core.editors.actions.ExpandEmacsAutoAction;
import com.simplifide.core.errorparser.CommandDefinition;
import com.simplifide.core.errorparser.CommandExtensionManager;
import com.simplifide.core.navigator.action.CleanAction;
import com.simplifide.core.navigator.action.CleanAction.CleanActor;
import com.simplifide.core.navigator.action.CleanHistoryAction.CleanHistoryActor;
import com.simplifide.core.navigator.action.CleanScriptAction;
import com.simplifide.core.navigator.action.CleanScriptAction.CleanScriptActor;
import com.simplifide.core.navigator.action.CommandAction;
import com.simplifide.core.navigator.action.CreateMakeAction;
import com.simplifide.core.navigator.action.ExpandEmacsNavigatorAction;
import com.simplifide.core.navigator.action.ExpandEmacsNavigatorAction.ExpandEmacseActor;
import com.simplifide.core.navigator.action.ExpandTemplateNavigatorAction;
import com.simplifide.core.navigator.action.ExpandTemplateNavigatorAction.ExpandTemplateActor;
import com.simplifide.core.navigator.action.GenerateDocAction;
import com.simplifide.core.navigator.action.MainProjectAction;
import com.simplifide.core.navigator.action.MainProjectAction.MainProjectActor;
import com.simplifide.core.navigator.action.PropertyAction;
import com.simplifide.core.navigator.action.PropertyAction.PropertyActor;
import com.simplifide.core.navigator.ui.PropertyDialog;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.python.MenuLoader;
import com.simplifide.core.python.PythonStartup;
import com.simplifide.core.python.context.SuiteContext;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.perspective.HardwarePerspectiveFactory;
import com.simplifide.core.ui.wizard.WizardIds;

public class SuiteElement extends TopElement<EclipseSuite> implements CleanActor, CleanHistoryActor,
	GenerateDocAction.DocActor, ExpandTemplateActor,MainProjectActor, CleanScriptActor, ExpandEmacseActor,
	PropertyActor{

	
	private IProject project;
	
	public SuiteElement(IProject project, ReferenceItem ref) {
		super(ref);
		this.project = project;
	}
	
	public boolean hasChildren() {
		return true;
	}
	
	public Object[] getChildren() { 
		ArrayList ulist = new ArrayList();
		ulist.add(new ProjectHolderElement.Project(this.getItem()));
		ulist.add(new ProjectHolderElement.Library(this.getItem()));
		
		IFolder script = this.getItem().getObject().getScriptFolder();
		if (script != null && script.exists()) ulist.add(script);
		
		IFolder synthesis = this.getItem().getObject().getSynthesisFolder();
		if (synthesis != null && synthesis.exists()) ulist.add(synthesis);
		
		IFolder route = this.getItem().getObject().getRouteFolder();
		if (route != null && route.exists()) ulist.add(route);
		
		IFolder test  = this.getItem().getObject().getTestFolder();
		if (test != null && test.exists()) ulist.add(test);
		
		IFolder doc = this.getItem().getObject().getDocFolder();
		if (doc != null && doc.exists()) {
			ulist.add(new DocElement(this.getItem()));
		}
		return ulist.toArray();
		/*
		Object[] objArr = new Object[] {
				new ProjectHolderElement.Project(this.getItem()),
				new ProjectHolderElement.Library(this.getItem()),
				this.getItem().getObject().getScriptFolder(),
				this.getItem().getObject().getSynthesisFolder(),
				this.getItem().getObject().getRouteFolder(),
				this.getItem().getObject().getTestFolder(),
				new DocElement(this.getItem())
		};
		return objArr;
		*/
	}
	
	private void createMenu(IMenuManager manager) {
		IMenuManager newMenu = new MenuManager("New");
		WizardShortcutAction proj = this.getAction(WizardIds.PROJECT);
		WizardShortcutAction library = this.getAction(WizardIds.LIBRARY);
		newMenu.add(proj);
		newMenu.add(library);
		manager.appendToGroup(ICommonMenuConstants.GROUP_NEW, newMenu);
	}
	
	private void applyWizardMenu(IMenuManager manager, IStructuredSelection selection) {
		//String[] ustr = new String[] {"Hardware Library","Hardware Project"};
		//this.createWizardMenu(manager, selection, ustr);
		this.createMenu(manager);
	}
	
	private void createNewFileMenu(IMenuManager manager) {
		IMenuManager newMenu = new MenuManager("Build");
		manager.appendToGroup(ICommonMenuConstants.GROUP_NEW, newMenu);
		File makeDir = ConfigurationDirectoryLoader.getMakeFileSuiteTemplatesDir();

		if (makeDir != null) {
			newMenu.add(new CreateMakeAction.Suite(new File(makeDir,"Makefile"),this.getItem().getObject()));
		}
		List<CommandDefinition> commands = CommandExtensionManager.getProjectCommands();
		for (CommandDefinition command : commands) {
			newMenu.add(new CommandAction.Suite(this.getItem().getObject(),command));
		}
		
	}

	@Override
	public void addActions(IMenuManager manager, IStructuredSelection selection) {
		
		manager.appendToGroup(ICommonMenuConstants.GROUP_BUILD, new MainProjectAction("Make Main Project",this));
		
		EclipseSuite suite = this.getItem().getObject();
		EclipseSuite active = ActiveSuiteHolder.getDefault().getSuite();
		
		if (suite == null) return;
		if (active != null && !active.equals(suite)) return;
		
	    this.applyWizardMenu(manager, selection);
		
	    manager.appendToGroup(ICommonMenuConstants.GROUP_BUILD, new CleanAction("Clean", this));
	    manager.appendToGroup(ICommonMenuConstants.GROUP_BUILD, new CleanScriptAction("Reload Script", this));
	 
		manager.appendToGroup(ICommonMenuConstants.GROUP_SEARCH, new ExpandTemplateNavigatorAction("Expand Templates", this,true));
	    manager.appendToGroup(ICommonMenuConstants.GROUP_SEARCH, new ExpandEmacsNavigatorAction("Expand Emacs AUTO Statements", this,true));
	    manager.appendToGroup(ICommonMenuConstants.GROUP_SEARCH, new GenerateDocAction("Generate Hdl Doc",this,false));
	    manager.appendToGroup(ICommonMenuConstants.GROUP_PROPERTIES, new PropertyAction("Properties",this));

	    //this.createNewFileMenu(manager);
	    
		//ToolLoader.createActions(manager, selection,
		//		SuiteRootStructureDirectory.getDefault().getScriptStructureFolder(this.project));
		//manager.appendToGroup(ICommonMenuConstants.GROUP_PORT, new SplitFileAction("Split Files",this));
		//manager.appendToGroup(ICommonMenuConstants.GROUP_PORT, new RemoveSplitAction("Remove Split Files",this));
	
	    

	    //manager.appendToGroup(ICommonMenuConstants.GROUP_SEARCH, new CleanScriptAction("Clean Script Directory", this));

	
		
	
		
		MenuLoader.createSuiteActions2(manager,new SuiteContext(this.getItem().getObject()));
		/*manager.appendToGroup(ICommonMenuConstants.GROUP_PROPERTIES, act);
		SuiteToolLoader loader = new SuiteToolLoader(this.getItem().getObject());
		loader.createActions(manager);
		*/
		
		
	}
	
	
	
	public void clean(IProgressMonitor monitor) {
		try {
			this.project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	
	}

	public void cleanHistory(IProgressMonitor monitor) {
		this.getItem().getObject().deleteConfigFile(); 
		this.clean(monitor);
		
	}

	public void generateDoc(IProgressMonitor monitor) {
		EclipseSuite suite = this.getItem().getObject();
		suite.generateDoc();
		
	}


	public void expandTemplate(IProgressMonitor monitor) {
		// Force a Save of All Objects
		EclipseSuite suite = this.getItem().getObject(); 
		SuiteExpandTemplateAction ac = new SuiteExpandTemplateAction(suite);
		try {
			suite.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		ac.expand();
	}


	public void makeMain(IProgressMonitor monitor) {
		
		EclipseSuite thisSuite = this.getItem().getObject(); 
		EclipseSuite currentSuite = ActiveSuiteHolder.getDefault().getSuite();
		
		if (thisSuite.equals(currentSuite)) return;
		
		BuildHandler.changeMainProject(currentSuite, thisSuite,true);
		ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
		
	}



	public void cleanScript(IProgressMonitor monitor) {
		PythonStartup.getDefault().cleanUp();
	}

	public void expandEmacs(IProgressMonitor monitor) {
		HardwareBuilder.disableBuild();
		EclipseSuite suite = this.getItem().getObject();
		suite.saveAllFiles();
		for (DesignFile dfile : suite.getCompileArrayList()) {
			ExpandEmacsAutoAction.runExpandCommand(dfile);
		}
		suite.refreshAllFiles();
		HardwareBuilder.enableBuild();
		
	} 

	public void properties(IProgressMonitor monitor) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
        
        	workbench.getDisplay().syncExec(new Runnable() {
            	public void run() {
            		EclipseSuite suite = getItem().getObject();
            		PropertyDialog dialog = new PropertyDialog(workbench,suite);
                    dialog.open();
            		
            	}
            });
       
		
	}


	
	
	
	
}
