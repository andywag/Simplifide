package com.simplifide.core.project.structure;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.QualifiedName;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.test.EclipseTest;
import com.simplifide.core.project.test.EclipseTestProject;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class SuiteStructureHolder extends BaseStructureHolder{

	public static QualifiedName SUITE_DIR_STRUCTURE_FILE = new QualifiedName(CoreActivator.PLUGIN_ID , "suite_dir_structure");

	protected static String FOLDER_DESIGN      = "design";
	protected static String FOLDER_DOC         = "doc";
	protected static String FOLDER_SCRIPT      = "script";
	protected static String FOLDER_TEST        = "test";
	protected static String FOLDER_PROJECTS    = "projects";
	protected static String FOLDER_LIB         = "libraries";
	protected static String FOLDER_BUILD       = "build";
	protected static String FOLDER_SYNTHESIS   = "synthesis";
	protected static String FOLDER_ROUTE       = "route";
	// This needs to be fixed but I am not sure what is going on with this
	public static String FOLDER_SCRIPT_TOOL = "Tools";
	
	public static String SUITE_MENU_PATH = "Menu.Suite";
	public static String PROJECT_MENU_PATH = "Menu.Project";
	
	private ArrayList<StructureDirectory> projects = new ArrayList<StructureDirectory>();
	private ArrayList<StructureDirectory> libraries = new ArrayList<StructureDirectory>();
		
		
	public SuiteStructureHolder() {}
	
	public SuiteStructureHolder(StructureDirectory structureDirectory) {
		super(structureDirectory);
		this.parseStructureDirectory(structureDirectory);

	}
	
	protected void parseSingleStructureBase(StructureBase dir) {
		String linkName = dir.getLinkName();
		if (linkName == null) return;
		if (linkName.equalsIgnoreCase(FOLDER_LIB)) {
			this.getLibraries().add((StructureDirectory)dir);
		}
		else if (linkName.equalsIgnoreCase(FOLDER_PROJECTS)) {
			this.getProjects().add((StructureDirectory)dir);
		}
		else {
			super.parseSingleStructureBase(dir);
		}
	}
	

	
	public static RootStructureDirectory createDefaultSuiteStructure() {
		// Required Directories
		RootStructureDirectory root = new RootStructureDirectory();
		root.addDefaultChild(SuiteStructureHolder.FOLDER_LIB,RootStructureDirectory.LINK_LIBRARY);
		root.addDefaultChild(SuiteStructureHolder.FOLDER_PROJECTS,RootStructureDirectory.LINK_PROJECT);
		// Optional Directories
		if (root.getUseDir(PreferenceConstants.SUITE_DIR_BUILD)) root.addDefaultChild(SuiteStructureHolder.FOLDER_BUILD,RootStructureDirectory.LINK_BUILD);
		if (root.getUseDir(PreferenceConstants.SUITE_DIR_DOC)) root.addDefaultChild(SuiteStructureHolder.FOLDER_DOC,RootStructureDirectory.LINK_DESIGN);
		if (root.getUseDir(PreferenceConstants.SUITE_DIR_ROUTE)) root.addDefaultChild(SuiteStructureHolder.FOLDER_ROUTE,RootStructureDirectory.LINK_ROUTE);
		if (root.getUseDir(PreferenceConstants.SUITE_DIR_SCRIPT)) root.addDefaultChild(SuiteStructureHolder.FOLDER_SCRIPT,RootStructureDirectory.LINK_SCRIPT);
		if (root.getUseDir(PreferenceConstants.SUITE_DIR_SYNTHESIS)) root.addDefaultChild(SuiteStructureHolder.FOLDER_SYNTHESIS,RootStructureDirectory.LINK_SYNTHESIS);
		if (root.getUseDir(PreferenceConstants.SUITE_DIR_TEST)) {
			StructureDirectory dir = root.addDefaultChild(SuiteStructureHolder.FOLDER_TEST,RootStructureDirectory.LINK_TEST);
			dir.addChild(new StructureDirectory(EclipseTestProject.DESIGN));
			dir.addChild(new StructureDirectory(EclipseTest.DATA_FOLDER));
		}
		return root;
	}
	
	//public StructureDirectory getProjectsStructure() {return this.getLinkMap().get(SuiteRootStructureDirectory.FOLDER_PROJECTS);}
	//public StructureDirectory getLibrariesStructure() {return this.getLinkMap().get(SuiteRootStructureDirectory.FOLDER_LIB);}
	public StructureDirectory getBuildStructure() {return (StructureDirectory) this.getLinkMap().get(SuiteStructureHolder.FOLDER_BUILD);}
	public StructureDirectory getDocStructure() {return (StructureDirectory)this.getLinkMap().get(SuiteStructureHolder.FOLDER_DOC);}
	public StructureDirectory getRouteStructure() {return (StructureDirectory)this.getLinkMap().get(SuiteStructureHolder.FOLDER_ROUTE);}
	public StructureDirectory getScriptStructure() {return (StructureDirectory)this.getLinkMap().get(SuiteStructureHolder.FOLDER_SCRIPT);}
	public StructureDirectory getSynthesisStructure() {return (StructureDirectory)this.getLinkMap().get(SuiteStructureHolder.FOLDER_SYNTHESIS);}
	public StructureDirectory getTestStructure() {return (StructureDirectory)this.getLinkMap().get(SuiteStructureHolder.FOLDER_TEST);}
	public StructureDirectory getDesignStructure() {return (StructureDirectory)this.getLinkMap().get(SuiteStructureHolder.FOLDER_DESIGN);}

	//public String getProjectsStructureName() {return this.getStructureName(SuiteRootStructureDirectory.FOLDER_PROJECTS);}
	//public String getLibrariesStructureName() {return this.getStructureName(SuiteRootStructureDirectory.FOLDER_LIB);}
	public String getBuildStructureName() {return this.getStructureName(SuiteStructureHolder.FOLDER_BUILD);}
	public String getDocStructureName() {return this.getStructureName(SuiteStructureHolder.FOLDER_DOC);}
	public String getRouteStructureName() {return this.getStructureName(SuiteStructureHolder.FOLDER_ROUTE);}
	public String getScriptStructureName() {return this.getStructureName(SuiteStructureHolder.FOLDER_SCRIPT);}
	public String getSynthesisStructureName() {return this.getStructureName(SuiteStructureHolder.FOLDER_SYNTHESIS);}
	public String getTestStructureName() {return this.getStructureName(SuiteStructureHolder.FOLDER_TEST);}
	public String getDesignStructureName() {return this.getStructureName(SuiteStructureHolder.FOLDER_DESIGN);}

	
	//public IFolder getProjectsStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteRootStructureDirectory.FOLDER_PROJECTS);}
	//public IFolder getLibrariesStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteRootStructureDirectory.FOLDER_LIB);}
	
	/** Converts a list of structure directories to a list of folders */
	private ArrayList<IFolder> convertStructure(IProject project, ArrayList<StructureDirectory> struct) {
		ArrayList<IFolder> folders = new ArrayList<IFolder>();
		for (StructureDirectory dir : struct) {
			IFolder folder = project.getFolder(dir.getName());
			if (folder != null && folder.exists()) folders.add(folder);
		}
		return folders;
	}
	public ArrayList<IFolder> getProjectStructureFolders(IProject project) {
		return this.convertStructure(project, this.getProjects());
	}
	public ArrayList<IFolder> getLibraryStructureFolders(IProject project) {
		return this.convertStructure(project, this.getLibraries());
	}
	/** Return the main project folder for this projects */
	public IFolder getMainProjectFolder(IProject project) {
		for (StructureDirectory struct : this.getProjects()) {
			IFolder folder = project.getFolder(struct.getName());
			if (folder != null & folder.exists()) return folder;
		}
		return null;
	}
	/** Return the main library folder for this projects */
	public IFolder getMainLibraryFolder(IProject project) {
		if (this.getLibraries().size() > 0) {
			StructureDirectory struct = this.getLibraries().get(0);
			return project.getFolder(struct.getName());
		}
		return null;
	}
	
	public IFolder getBuildStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteStructureHolder.FOLDER_BUILD);}
	public IFolder getDocStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteStructureHolder.FOLDER_DOC);}
	public IFolder getRouteStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteStructureHolder.FOLDER_ROUTE);}
	public IFolder getScriptStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteStructureHolder.FOLDER_SCRIPT);}
	public IFolder getSynthesisStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteStructureHolder.FOLDER_SYNTHESIS);}
	public IFolder getTestStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteStructureHolder.FOLDER_TEST);}
	public IFolder getDesignStructureFolder(IProject proj) {return this.getStructureFolder(proj,SuiteStructureHolder.FOLDER_DESIGN);}

	public String getSuiteMenuPath() {return SUITE_MENU_PATH;}
	public String getProjectMenuPath() {return PROJECT_MENU_PATH;}
	
	public void setProjects(ArrayList<StructureDirectory> projects) {
		this.projects = projects;
	}
	public ArrayList<StructureDirectory> getProjects() {
		return projects;
	}
	public void setLibraries(ArrayList<StructureDirectory> libraries) {
		this.libraries = libraries;
	}
	public ArrayList<StructureDirectory> getLibraries() {
		return libraries;
	}


 	
}
