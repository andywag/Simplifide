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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.internal.navigator.wizards.WizardShortcutAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.ConfigurationDirectoryLoader;
import com.simplifide.core.ConfigurationDirectoryLoader2;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.errorparser.CommandDefinition;
import com.simplifide.core.errorparser.CommandExtensionManager;
import com.simplifide.core.navigator.action.CleanAction;
import com.simplifide.core.navigator.action.CommandAction;
import com.simplifide.core.navigator.action.CreateLibraryAction;
import com.simplifide.core.navigator.action.CreateMakeAction;
import com.simplifide.core.navigator.action.ExpandTemplateNavigatorAction;
import com.simplifide.core.navigator.action.GenerateDocAction;
import com.simplifide.core.navigator.action.MainProjectAction;
import com.simplifide.core.navigator.action.CleanAction.CleanActor;
import com.simplifide.core.navigator.action.CleanHistoryAction.CleanHistoryActor;
import com.simplifide.core.navigator.action.CreateLibraryAction.CreateLibraryActor;
import com.simplifide.core.navigator.action.DeleteAction.DeleteActor;
import com.simplifide.core.navigator.action.ExpandTemplateNavigatorAction.ExpandTemplateActor;
import com.simplifide.core.navigator.action.ExternalBuildAction.ExternalBuildActor;
import com.simplifide.core.navigator.action.GenerateDocAction.DocActor;
import com.simplifide.core.navigator.action.MainProjectAction.MainProjectActor;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.library.storage.EclipseLibraryStore;
import com.simplifide.core.python.MenuLoader;
import com.simplifide.core.python.context.ProjectContext;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileAlone;
import com.simplifide.core.ui.wizard.WizardIds;

public class BasicProjectElement<T extends EclipseBaseProject> extends TopElement<T> implements CleanActor, 
CleanHistoryActor,  DocActor, DeleteActor, ExpandTemplateActor, ExternalBuildActor, CreateLibraryActor,
MainProjectActor{

	public BasicProjectElement(ReferenceItem ref) {
		super(ref);
	}

	
	private void createMenu(IMenuManager manager) {
		IMenuManager newMenu = new MenuManager("New");
		WizardShortcutAction proj = this.getAction(WizardIds.SUBPROJECT);
		IMenuManager vhdlMenu = new MenuManager("VHDL");
		vhdlMenu.add(this.getAction(WizardIds.VHDLENTITY));
		vhdlMenu.add(this.getAction(WizardIds.VHDLARCHITECTURE));
		vhdlMenu.add(this.getAction(WizardIds.VHDLENTITYARCHITECTURE));
		vhdlMenu.add(this.getAction(WizardIds.VHDLPACKAGE));
		vhdlMenu.add(this.getAction(WizardIds.VHDLTESTBENCH));
		vhdlMenu.add(this.getAction(WizardIds.VHDLTESTER));
		IMenuManager verilogMenu = new MenuManager("Verilog");
		verilogMenu.add(this.getAction(WizardIds.VERILOGMODULE));
		verilogMenu.add(this.getAction(WizardIds.VERILOGPROGRAM));
		verilogMenu.add(this.getAction(WizardIds.VERILOGINTERFACE));
		verilogMenu.add(this.getAction(WizardIds.VERILOGCLASS));
		verilogMenu.add(this.getAction(WizardIds.VERILOGCLASS));
		newMenu.add(proj);
		newMenu.add(vhdlMenu);
		newMenu.add(verilogMenu);
		
		manager.appendToGroup(ICommonMenuConstants.GROUP_NEW, newMenu);
		
	}
	
	private void createNewFileMenu(IMenuManager manager) {
		
		IMenuManager newMenu = new MenuManager("Build");
		manager.appendToGroup(ICommonMenuConstants.GROUP_NEW, newMenu);
		File makeDir = ConfigurationDirectoryLoader2.getProjectMakeFileTemplatesDir();
		//if (makeDir != null) {
			File makeF = new File(makeDir,"Makefile"); 
			newMenu.add(new CreateMakeAction.Project(makeF,this.getItem().getObject()));
			newMenu.add(new CreateMakeAction.EditProject(makeF,this.getItem().getObject()));
		//}
		List<CommandDefinition> commands = CommandExtensionManager.getProjectCommands();
		for (CommandDefinition command : commands) {
			newMenu.add(new CommandAction.Project(this.getItem().getObject(),command));
		}
		
	}

	public void addActions(IMenuManager manager, IStructuredSelection selection) {
		String[] ustr = new String[] {"Hardware Project","Hardware Library"};

		this.createMenu(manager);
		
		manager.appendToGroup(ICommonMenuConstants.GROUP_PORT, new CreateLibraryAction("Create Library",this));
		manager.appendToGroup(ICommonMenuConstants.GROUP_PORT, new MainProjectAction("Make Main Project", this));

		manager.appendToGroup(ICommonMenuConstants.GROUP_BUILD, new CleanAction("Clean", this));
		manager.appendToGroup(ICommonMenuConstants.GROUP_BUILD, new ExpandTemplateNavigatorAction("Expand Templates", this,true));
		//manager.appendToGroup(ICommonMenuConstants.GROUP_BUILD, new CreateMakeAction.Project("Clean All",this));
		this.createNewFileMenu(manager);
		MenuLoader.createProjectActions2(manager,new ProjectContext(this.getItem().getObject()));
		manager.appendToGroup(ICommonMenuConstants.GROUP_PROPERTIES, new GenerateDocAction("Generate Hdl Doc",this,false));

	}

	public void clean(IProgressMonitor monitor) {
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		try {
			suite.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD,monitor);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}

	public void cleanHistory(IProgressMonitor monitor) {
		T project = this.getItem().getObject();
		project.deleteConfigFile();
		this.clean(monitor);
	}


	/** Used for new Wizard of navigator */
	public IFolder getDesignFolder() {
		return this.getItem().getObject().getDesignIFolder();
	}

	private ArrayList<Object> uniquifyList(ArrayList<Object> list) {
		ArrayList<Object> newList = new ArrayList<Object>();
		boolean add = true;
		for (int i=0;i<list.size();i++) {
			add = true;
			Object addObject = list.get(i);
			for (int j=0;j<newList.size();j++) {
				Object curObject = newList.get(j);
				if (curObject.toString().equalsIgnoreCase(addObject.toString())) {
					add = false;
					break;
				}
			}
			if (add) newList.add(addObject);
		}
		return newList;
	}

	public Object[] getChildren() {
		ArrayList<Object> outlist = new ArrayList<Object>();
		UniqueList<DesignFile> dlist = this.getItem().getObject().getSourceList();
		for (ReferenceItem<? extends DesignFile> utime : dlist.getGenericSelfList()) {
			DesignFile dfile = utime.getObject();
			if (dfile.getParent() != null) {

				outlist.add(ElementFactory.createElement(dfile.getParent().createReferenceItem()));
			}
			else {
				if (dfile instanceof DesignFileAlone) {
					DesignFileAlone alone = (DesignFileAlone) dfile;
					outlist.add(new DesignFileAloneElement(alone.createReferenceItem()));
				}
				else {
					IResource res = utime.getObject().getResource();
					if (res != null) outlist.add(res);
					
					else outlist.add(ElementFactory.createElement(dfile.createReferenceItem()));
				}
				
			}
		}

		return uniquifyList(outlist).toArray();
	}


	public boolean hasChildren() {
		//int size = this.getItem().getObject().getDesignFolderNew().getSourceList().getnumber();
		EclipseBaseProject project = this.getItem().getObject();
		int size = project.getSourceList().getnumber();
		if (size >0) return true;
		return false;

	}

	public static class Library extends BasicProjectElement {
		public Library(ReferenceItem ref) {
			super(ref);
		}
	}

	public static class Project extends BasicProjectElement {
		public Project(ReferenceItem ref) {
			super(ref);
		}
	}




	public void generateDoc(IProgressMonitor monitor) {
		this.getItem().getObject().getDocHolder().generateDoc();

	}


	public void delete(IProgressMonitor monitor) {
		try {
			IFolder folder = this.getItem().getObject().getBaseFolder();

			folder.delete(true, monitor);
			IFolder parent = (IFolder) folder.getParent();
			parent.refreshLocal(IProject.DEPTH_INFINITE, null);
			this.clean(monitor);

		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}


	public void expandTemplate(IProgressMonitor monitor) {
		EclipseSuite suite = (EclipseSuite) this.getItem().getObject().getSuiteReference().getObject();

		for (ReferenceItem<? extends DesignFile> dfile : suite.getCompileList().getGenericSelfList()) {
			dfile.getObject().getBuilder().expandTemplates();
		}


	}


	public void externalBuild(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}


	

	public void createLibrary(IProgressMonitor monitor) {
		EclipseBaseProject proj = this.getItem().getObject();
		EclipseLibraryStore lib = new EclipseLibraryStore();
		lib.createStorage(proj);
	}


	public void makeMain(IProgressMonitor monitor) {
		EclipseBaseProject proj = this.getItem().getObject();
		EclipseSuite suite = (EclipseSuite) proj.getSuiteReference().getObject();
		suite.setMainProject(proj);
		ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
		suite.createHierarchy();
	}



}
