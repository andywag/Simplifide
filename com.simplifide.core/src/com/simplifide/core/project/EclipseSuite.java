/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.swt.widgets.Display;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.project.LibraryHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.doc.EclipseSuiteDocHandler;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.project.hier.ClassHierarchyManager;
import com.simplifide.core.project.hier.HierarchyManager;
import com.simplifide.core.project.hier.ProjectContentManager;
import com.simplifide.core.project.library.EclipseLibraryProject;
import com.simplifide.core.project.library.har.EclipseHarProject;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.SuiteStructureHolder;
import com.simplifide.core.project.test.EclipseTestProject;
import com.simplifide.core.python.SaveActionInterpreter;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;



public class EclipseSuite extends CoreProjectSuite<EclipseBaseProject>{

	private static QualifiedName MAIN_PROJECT_PROPERTY = new QualifiedName(CoreActivator.PLUGIN_ID,"Base_Project");
	
	private IProject project;
    private EclipseSuiteDocHandler docHandler;
	private SuiteExternalErrorHandler errorHandler;
    private UniqueList<DesignFile> compileList;
    private UniqueList<CoreProjectBasic> projectCompileList;
    private SuiteStructureHolder dirStructure;
    
    private EclipseBaseProject mainProject;
   
    private static String contentFileName = "Content.xml";
    
	public EclipseSuite() {super();}
	
	public EclipseSuite(String name, IProject project) {
		super(name);
		this.setProject(project);
		init();
	}
	
	public EclipseSuite(String name, IProject project, boolean load) {
		super(name);
		this.setProject(project);
		if (load) init();
	}
	
	
	public IFile getContentXmlFile() {
		return this.project.getFile(contentFileName);
		
	}
	
	
	public List<InstanceModule> getAllInstanceModules() {
		List<CoreProjectBasic> projects = this.getAllRealProject();
		ArrayList<InstanceModule> modules = new ArrayList<InstanceModule>();
		for (CoreProjectBasic project : projects) {
			modules.addAll(project.getAllInstanceModules());
		}
		return modules;
	}
	
	public void saveAllFiles() {
		Display display = Display.getDefault();
		if (display == null) return;
		display.syncExec(new Runnable() {
			public void run() {
				for (DesignFile dfile : getCompileList().getRealSelfList()) {
					SourceEditor editor = dfile.getEditor();
					if (editor != null) editor.doSave(null);
				}
			};
		});

		
	}
	
	public void refreshAllFiles() {

		Display display = Display.getDefault();
		if (display == null) return;
		display.syncExec(new Runnable() {
				public void run() {
					for (DesignFile dfile : getCompileList().getRealSelfList()) {
						try {
							dfile.getResource().refreshLocal(IResource.DEPTH_ZERO, null);
						} catch (CoreException e) {
							HardwareLog.logError(e);
						}
					}
				};
			});

	
	}
	
	
	public boolean isMainProject() {
		String pers;
		try {
			pers = project.getPersistentProperty(BuildHandler.MAIN_PROJECT);
			if (pers != null) {
				if (pers.equalsIgnoreCase(BuildHandler.MAIN_PROJECT_TRUE)) return true;
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return false;
		
	}
	
	/** Cleans the project and reloads the data files 
	 * 
	 */
	public void clean() {
		this.deleteObject();
		this.publicInit();
		if (ActiveSuiteHolder.getDefault().getTreeContent() != null) {
			ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
		}
	}
	
	public void deleteObject() {
		this.dirStructure = null;
        this.docHandler = null;
        this.errorHandler = null;   
        this.mainProject = null;
        super.deleteObject();
	}
	
	public void publicInit() {
		super.publicInit();
		this.init();
		EclipseContentHandler.serializeSuite(this);
		
	}
	
	private void init() {
		//ScalaInterface.test();
		// Create the Directory Structure
		this.dirStructure = RootStructureLoader.loadSuiteStructure(this);
		// Loads the Projects defined in contents.xml. This should contain all projects
		this.loadContentProjects(); 
		// Loads the Projects in the project directory. If not already handled by the contents ?
		this.loadProjects();        
		// Loads the Libraries in the libraries directory
		this.loadLibraries();       
        
		this.docHandler = new EclipseSuiteDocHandler(this);	
        this.errorHandler = new SuiteExternalErrorHandler(this);
        
        
	}
        
	public String getMainProjectName() {
		NoSortList list = this.getHierListReference().getObject();
		ConnectorModule mod = (ConnectorModule) ((ReferenceItem)list.getObject(0)).getObject();
		DesignFile dfile = LocationOperations.getDesignFile(mod.getEntityLocation());
		return dfile.getProjectRef().getname();
		
	}
	
	
	
    public int getSearchType() {
    	return ReferenceUtilities.REF_PROJECT_SUITE;
    }
	
	
        /** Convenience Method to retrive the library list */
        public List<EclipseBaseProject> getProjectList() {
            return this.getRealSelfList();
        }
        /** Convenience Method to retrive the library list */
        public List<CoreProjectBasic> getLibraryList() {
            //return this.getLibraryReference().getObject().getRealSelfList();
        	if (this.getLibraryReference() == null || 
        		this.getLibraryReference().getObject() == null) return new ArrayList();
        	return this.getLibraryReference().getObject().getRealSelfList();
        }
        
        public List<CoreProjectBasic> getAllRealProject() {
        	ArrayList<CoreProjectBasic> projects = new ArrayList<CoreProjectBasic>();
        	projects.addAll(this.getLibraryList());
        	projects.addAll(this.getProjectList());
        	return projects;
        }
        
        public List<CoreProjectBasic> getAllProjects() {
        	ArrayList<CoreProjectBasic> projects = new ArrayList<CoreProjectBasic>();
        	//lis.addAll(this.getLibraryList());
        	//lis.addAll(this.getProjectList());
        	if (this.projectCompileList == null) return projects;
        	
        	for (ReferenceItem<? extends CoreProjectBasic> ref: this.projectCompileList.getGenericSelfList()) {
        		CoreProjectBasic base = ref.getObject();
        		if (base != null) projects.add(base);
        	}
        	
        	for (int i=projects.size()-1;i>0-1;i--) {
        		CoreProjectBasic base = projects.get(i);
        		if (base instanceof EclipseHarProject ||
        			base.getname().equalsIgnoreCase("ieee") ||
        			base.getname().contains("ieee") ||
        			base.getname().equalsIgnoreCase("std")) {
        			projects.remove(i);
        		}
        	}
        	
        	return projects;
        }
  
        public void createProjectCompileList() {
        	UniqueList<CoreProjectBasic> projects = new UniqueList<CoreProjectBasic>();
        	for (ReferenceItem<? extends DesignFile> dref : this.getCompileList().getGenericSelfList()) {
        		projects.addObject(dref.getObject().getProjectRef());
        	}
        	this.projectCompileList = projects;
        }
        
	
	/** Generates the sections of hdl doc */
	public void generateDoc() {
		this.docHandler.generateDoc();
	}
        
	
    
	
	private void loadSingleProject() {
		IFolder proj = this.getDirStructure().getDesignStructureFolder(this.getProject());
		if (proj != null && proj.exists()) {
			EclipseSingleProject single = new EclipseSingleProject("work2",proj,this.createReferenceItem());
			single.loadFiles();
			this.createReferenceItem().addReferenceItem(single.createReferenceItem());

		}
	}
	
	private void loadContentProjects() {
		if (HardwareChecker.isProjectEnabled()) {
			EclipseContentHandler.handleEclipseContents(this);
		}
	}
	
	private boolean alreadyExists(IResource project) {
		for (CoreProjectBasic proj : this.getAllRealProject()) {
			if (proj != null && project != null && proj.getname().equalsIgnoreCase(project.getName())) return true;
		}
		return false;
	}
	
	/** Load the projects for this suite */
	private void loadProjects() {
		this.loadSingleProject();
		List<IFolder> folders = this.getProjectFolders();
		for (IFolder folder : folders) {
			if (folder != null && folder.exists()) {
				try {
					IResource[] projects = folder.members();
					for (IResource project : projects) {
							
						if (project instanceof IFolder && !project.getName().startsWith(".") && !alreadyExists(project)) {
							IFolder projFolder = (IFolder) project;
							String fname = projFolder.getName();
							ReferenceItem<EclipseSuite> eclipseRef = this.createReferenceItem();
						
							EclipseProject eproject = new EclipseProject(fname,projFolder,eclipseRef);
							eproject.loadFiles();
						
							this.createReferenceItem().addReferenceItem(eproject.createReferenceItem());
						}
					}
				} catch (CoreException e) {
					HardwareLog.logError(e);
				}
			}
		}
	}
	
	/** Load the libraries for this suite */
	private void loadLibraries() {
		List<IFolder> folders = this.getLibraryFolders();
		for (IFolder libFolder : folders) {
			if (libFolder != null && libFolder.exists() && this.isMainProject()) {
				try {
					IResource[] cfolder = libFolder.members();
					for (IResource resource : cfolder) {
						if (resource instanceof IFolder && !resource.getName().startsWith(".")) {
							EclipseLibraryProject library = EclipseLibraryProject.createProject(resource.getName(),(IFolder)resource,this.getEclipseSuiteRef());
							this.getLibraryReference().addReferenceItem(library.createReferenceItem());
							library.loadFiles();
						}
						else if (resource.getName().endsWith(".har")) { // Har File Support
							EclipseHarProject library = EclipseHarProject.createHarProject(resource, this.getEclipseSuiteRef());
							this.getLibraryReference().addReferenceItem(library.createReferenceItem());
							library.loadFiles();
						}
					}
				} catch (CoreException e) {
					HardwareLog.logError(e);
				}
			}
		}
		
		
	}
	
	/** Returns a project with a given name */
	public EclipseBaseProject getProject(String name) {
		for (EclipseBaseProject project : this.getProjectList()) {
			if (project.getname().equalsIgnoreCase(name)) return project;
		}
		return null;
	}
	
	/** Returns a library with the given name */
	
	public CoreProjectBasic getLibrary(String name) {
		for (CoreProjectBasic project : this.getLibraryList()) {
			if (project.getname().equalsIgnoreCase(name)) return project;
		}
		return null;
	}
	
	/** Creates the compile list for the suite. 
	 *  Only searches through the projects because the libraries are optional
	 */
	 protected void createCompileList() {
		 UniqueList<DesignFile> dlist = new UniqueList<DesignFile>();
		 for (EclipseBaseProject proj : this.getRealSelfList()) {
			 dlist.addAll(proj.createCompileList());
		 } 
		 /*if (this.getTest() != null) {
			 dlist.addAll(this.getTest().createCompileList());
		 }*/
		 this.setCompileList(dlist);
		 this.createProjectCompileList();
	 }
	
	
	public ReferenceItem<EclipseSuite> getEclipseSuiteRef() {
		return (ReferenceItem<EclipseSuite>) this.createReferenceItem();
	}
	
	/** Adds a project to the suite Called from Builder Change State*/
	public void addProject(IFolder folder) {
		EclipseProject project = new EclipseProject(folder.getName(),folder,this.createReferenceItem());
		project.loadFiles();
		this.getReference().addReferenceItem(project.createReferenceItem());
		if (ActiveSuiteHolder.getDefault().getTreeContent() != null) ActiveSuiteHolder.getDefault().getTreeContent().fireChange();

	}
	/** Adds a library to the Suite Called from Builder Change State*/
	public void addLibrary(IFolder folder) {
		EclipseLibraryProject lib = new EclipseLibraryProject(folder.getName(),folder,this.createReferenceItem());
		lib.loadFiles();
		this.getLibraryReference().getObject().addObject(lib.createReferenceItem());
		if (ActiveSuiteHolder.getDefault().getTreeContent() != null)
			ActiveSuiteHolder.getDefault().getTreeContent().fireChange();

	}
	
	
	/** Removes a Project from the suite Called from Builder Change State*/
	public void removeProject(IFolder folder) {
		for (ReferenceItem<? extends EclipseBaseProject> item : this.getGenericSelfList()) {
			IFolder ufolder = item.getObject().getBaseFolder();
			if (folder.getProjectRelativePath().toString().equalsIgnoreCase(ufolder.getProjectRelativePath().toString())) {
				this.removeObject(item);
				item.deleteObject();
				if (ActiveSuiteHolder.getDefault().getTreeContent() != null) {
					ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
				}
				EclipseContentHandler.serializeSuite(this);
				return;
			}
		}
	}
	/** Removes a Library from the suite (Occurs on Deletion) */
	public void removeLibrary(IFolder folder) {
		LibraryHolder holder = this.getLibraryReference().getObject();
		for (ReferenceItem<? extends CoreProjectBasic> item : holder.getGenericSelfList()) {
			EclipseBaseProject proj = (EclipseBaseProject) item.getObject();
			IFolder ufolder = proj.getBaseFolder();
			if (folder.getProjectRelativePath().toString().equalsIgnoreCase(ufolder.getProjectRelativePath().toString())) {
				holder.removeObject(item);
				item.deleteObject();
				ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
				EclipseContentHandler.serializeSuite(this);
				return;
			}
		}
	} 
	
	/** @deprecated : Method for old memory handling */
	public void deleteConfigFile() {
		for (EclipseBaseProject proj : this.getProjectList()) {
			proj.deleteConfigFile();
		}
		for (CoreProjectBasic proj : this.getLibraryList()) {
			((EclipseBaseProject)proj).deleteConfigFile();
		}
		
	}
		
	/** Get the Library Build Times 
	 * @deprecated*/
	private int getLibraryBuildTimes() {
		/*
		int time = 0;
		if (this.getLibraryReference() == null || this.getLibraryReference().getObject() == null) return time;
		ModuleObjectWithList list = this.getLibraryReference().getObject();
		for (int i=0;i<list.getnumber();i++) {
			ReferenceItem<EclipseBaseProject> proj = (ReferenceItem<EclipseBaseProject>) list.getObject(i);
			time += proj.getObject().getBuildTime();
		}
		return time; */
		return 0;
	}
	
	/** Get the project Build Times to show as status for the project build 
	 * @deprecated*/
	private int getProjectBuildTimes() {
		int time = 0;
		/*for (ReferenceItem<? extends EclipseBaseProject> proj : this.getGenericSelfList()) {
			if (proj != null && proj.getObject() != null)
				time += proj.getObject().getBuildTime();
		}*/
		return time;
	}
	
	
	
	
	/** Creates a list of components and objects in the Design Only (2nd pass)_*/

	private void buildNormal(IProgressMonitor monitor, int totaltime) {
		try {
			monitor.beginTask("Building Project", totaltime);
			List ulist = this.getCompileList().getRealSelfList();
			for (DesignFile dfile : this.getCompileList().getRealSelfList()) {
				dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED,  monitor);
			}
		}
		finally {
			monitor.done();
		}
	}
	/** Creates a list of components and objects in the Design Only (1st pass)_*/
	private void buildContext(IProgressMonitor monitor, int total) {
		try {
			monitor.beginTask("Building Project Context", total);
			
			for (ReferenceItem<? extends EclipseBaseProject> projectRef : this.getGenericSelfList()) {
				if (projectRef != null && projectRef.getObject() != null) {
					projectRef.getObject().build(BuildInterface.BUILD_FILE_CONTEXT, monitor);
				}
			}
			
			if (this.getLibraryReference() != null && this.getLibraryReference().getObject() != null) {
				for (ReferenceItem<? extends CoreProjectBasic> libraryRef : this.getLibraryReference().getObject().getGenericSelfList()) {
					EclipseLibraryProject lib = (EclipseLibraryProject) libraryRef.getObject();
					lib.build(BuildInterface.BUILD_FILE_CONTEXT, monitor);
				}
			}
			
			
			
			
			
			if (this.getTest() != null) {
				this.getTest().build(BuildInterface.BUILD_FILE_CONTEXT, monitor);
			}
		}
		finally {
			monitor.done();
		}
	}
	
	
	private EclipseTestProject getTest() {
		if (this.getTestReference() != null) {
			return (EclipseTestProject) this.getTestReference().getObject();
		}
		return null;
	}

	
	public void recreateCompileList() {
		this.convertContextList();
		this.createCompileList();
	}
	
	/** Goes through all of the projects in the design and converts the list
	 * of dependent libraries into actual files 
	 */
	private void convertContextList() {
		// Not sure the library should be included in this list
		
		if (this.getLibraryReference() != null && this.getLibraryReference().getObject() != null) {
			for (ReferenceItem<? extends CoreProjectBasic> libraryRef : this.getLibraryReference().getObject().getGenericSelfList()) {
				EclipseBaseProject lib = (EclipseBaseProject) libraryRef.getObject();
				lib.convertContextList();
			}
		}
		
		for (ReferenceItem<? extends EclipseBaseProject> projectRef : this.getGenericSelfList()) {
			if (projectRef != null && projectRef.getObject() != null)
				projectRef.getObject().convertContextList();
		}
		
	}
	

 	/** This function builds the suite project. It only works for the initial
	 * build 
	 * @param kind
	 * @param args
	 * @param monitor
	 */
	
public void buildWithoutHierarchy(int kind, IProgressMonitor monitor)  {
		//int totaltime = this.getProjectBuildTimes() + this.getLibraryBuildTimes();
		int totaltime = 0;	
	
		try {
			monitor.beginTask("Building Project " + this.name, 2*totaltime);
			SubProgressMonitor submon = new SubProgressMonitor(monitor,totaltime);
			
			// Creates the basic structure of the files to determiner ordering for compile
			this.buildContext(submon,totaltime);
			// Updates the files dependencies
			this.convertContextList();
			// Creates the order of compilation for the design
			
			this.createCompileList();
			
			submon = new SubProgressMonitor(monitor,totaltime);
			this.buildNormal(submon,totaltime);
			
			
		}
		finally {
			monitor.done();
		}
		
	}
	
	public void build(int kind, IProgressMonitor monitor)  {		
		try {
			this.buildWithoutHierarchy(kind, monitor);
			this.createHierarchy();
			SaveActionInterpreter.getDefault().clean();
			
		}
		finally {
			monitor.done();
		}
	}
	
	public void createHierarchy() {
		try {
			super.createHierarchy();
			HierarchyManager.getInstance().changeHierList(this.getHierListReference());
			ProjectContentManager.getInstance().updateDisplay();
			ClassHierarchyManager.getInstance().updateDisplay();
		}
		catch (Exception e) {
			HardwareLog.logError(e);
		}
		
	}
	

	
	
	public void setProject(IProject project) {
		this.project = project;
	}
	public IProject getProject() {
		return project;
	}
	public void setErrorHandler(SuiteExternalErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}
	public SuiteExternalErrorHandler getErrorHandler() {
		return errorHandler;
	}
	public void setCompileList(UniqueList<DesignFile> compileList) {
		this.compileList = compileList;
	}
	public UniqueList<DesignFile> getCompileList() {
		return compileList;
	}
	
	/** Convert the UniqueList command to a simple Array List */
	public ArrayList<DesignFile> getCompileArrayList() {
		ArrayList<DesignFile> slist = new ArrayList();
		for (DesignFile dfile : getCompileList().getRealSelfList()) {
			slist.add(dfile);
		}
		return slist;
	}

	public void setDirStructure(SuiteStructureHolder dirStructure) {
		this.dirStructure = dirStructure;
	}

	public SuiteStructureHolder getDirStructure() {
		return dirStructure;
	}

	public String getBaseLocation() {
		return this.project.getLocation().toOSString();
	}
	
	public IFolder getBuildFolder() {
		return this.getDirStructure().getBuildStructureFolder(this.project);
    }
	
	public IFolder getDocFolder() {
		return this.getDirStructure().getDocStructureFolder(this.project);
    }
    
	
	
	public List<IFolder> getProjectFolders() {
		return this.getDirStructure().getProjectStructureFolders(this.project);
	}
	
	public List<IFolder> getLibraryFolders() {
		return this.getDirStructure().getLibraryStructureFolders(project);  
	}
	
	
	public IContainer getMainProjectFolder() {
		IContainer folder = this.getDirStructure().getMainProjectFolder(this.project);
		/*if (folder == null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			folder = root.getFolder(new Path(this.getname()));
		}*/
		return folder;
	}
	
	public IFolder getMainLibraryFolder() {
		return this.getDirStructure().getMainLibraryFolder(this.project);
	}
	
	public IFolder getScriptFolder() {
		return this.getDirStructure().getScriptStructureFolder(this.project);
	}
	
	public IFolder getSynthesisFolder() {
		return this.getDirStructure().getSynthesisStructureFolder(this.project);
	}
	
	public IFolder getRouteFolder() {
		return this.getDirStructure().getRouteStructureFolder(this.project);
	}
	
	public IFolder getTestFolder() {
		return this.getDirStructure().getTestStructureFolder(this.project);
	}

	 protected CoreProjectBasic getMainProjectForHierarchy() {
	    	return this.mainProject;
	    }
	
	public void setMainProject(EclipseBaseProject mainProject) {
		this.mainProject = mainProject;
	}

	public EclipseBaseProject getMainProject() {
		return mainProject;
	}

	public IFile getMakefile() {
		return this.getProject().getFile("Makefile");
	}

	


	
}
