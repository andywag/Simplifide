package com.simplifide.core.project.structure;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.ConfigurationDirectoryLoader;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSubProject;
import com.simplifide.core.project.EclipseSuite;

/** File which is responsible for loading the directory structures for 
 *  the projects compoennts. 
 * @author andy
 *
 */
public class RootStructureLoader {
	
	private static String WORKSPACELOCATIONNAME = "Structure.xml";
	
	
	private static WorkspaceDirectoryStructure loadStructureFromString(String file) {
		WorkspaceDirectoryStructure structure = new WorkspaceDirectoryStructure();
			structure.loadWorkspaceStructure(file);
			return structure;
	}
	
	private static WorkspaceDirectoryStructure loadStructureFromRealFile(File file) {
		if (file.exists()) {
			WorkspaceDirectoryStructure structure = new WorkspaceDirectoryStructure();
			structure.loadWorkspaceStructure(file);
			return structure;
		}
		return null;
	}
	
	// Load the Workspace Directory Structure from a File
	private static WorkspaceDirectoryStructure loadStructureFromFile(IFile ifile) {
		if (ifile != null && ifile.exists()) { // Check For a Local Structure
			File ufile = ifile.getLocation().toFile();
			return loadStructureFromRealFile(ufile);
		}
		return null;
	}
	
	public static WorkspaceDirectoryStructure loadFromFileOnly(StructureFile structureXmlFile) {
		WorkspaceDirectoryStructure structure = null;
		
		if (structureXmlFile != null) {
			if (structureXmlFile.getLinkType() == StructureFile.LINK_NEW) {
				structure = loadStructureFromString(structureXmlFile.getContents());
				return structure;
			}
			else if (structureXmlFile.getLocation() != null) {
				File ufile = new File(structureXmlFile.getLocation());
				structure = loadStructureFromRealFile(ufile);
				return structure;
			}
		}
		return null;
	}
	
	/** Loads the workspace structure from the initial files 
	 *  1. Looks for the structure file
	 *  2. Looks for the workspace file
	 *  3. Looks for the configuration
	 *  4. Uses Defaults
	 * 
	 * */
	public static WorkspaceDirectoryStructure loadWorkspaceDirectory(StructureFile structureXmlFile) {
		WorkspaceDirectoryStructure structure = null;
		if (structureXmlFile != null && structureXmlFile.getLocation() != null) {
			File ufile = new File(structureXmlFile.getLocation());
			structure = loadStructureFromRealFile(ufile);
		}
		if (structure == null)  structure = loadWorkspaceStructureInternal();
		if (structure == null) structure = loadConfigurationStructureInternal();
		if (structure == null) {
			structure = new WorkspaceDirectoryStructure();
		}
		structure.loadDefaultEmptyDirectories();
		return structure;
		
	}
	
	private static WorkspaceDirectoryStructure loadConfigurationStructureInternal() {
		File suiteFile = ConfigurationDirectoryLoader.getWorkspaceStructure();
		
		if (suiteFile != null && suiteFile.exists()) {
			WorkspaceDirectoryStructure structure = new WorkspaceDirectoryStructure();
			structure.loadWorkspaceStructure(suiteFile);
			return structure;
		} 
		return null;
		
		
	}
	
	private static WorkspaceDirectoryStructure loadWorkspaceStructureInternal() {
		File outDir = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();
		File sourceDir = new File(outDir,WORKSPACELOCATIONNAME);
		WorkspaceDirectoryStructure structure = new WorkspaceDirectoryStructure();
		boolean work = structure.loadWorkspaceStructure(sourceDir);
		if (work) return structure;
		return null;
		
		
	}
	
	private static WorkspaceDirectoryStructure loadSuiteStructureInternal(EclipseSuite suite) {
		if (suite != null && suite.getProject() != null) { // Check at the Suite's Location
			IFile ifile = suite.getProject().getFile(WORKSPACELOCATIONNAME); 
			WorkspaceDirectoryStructure structure = loadStructureFromFile(ifile);
			return structure;
		}
		return null;
	}
	
	private static WorkspaceDirectoryStructure loadProjectStructureInternal(EclipseBaseProject project) {
		if (project != null && project.getBaseFolder() != null) { // Check at the Suite's Location
			IFile ifile = project.getBaseFolder().getFile(WORKSPACELOCATIONNAME); 
			WorkspaceDirectoryStructure structure = loadStructureFromFile(ifile);
			return structure;
		}
		return null;
	}
	
	/** Loads the suite directory structure */
	public static SuiteStructureHolder loadSuiteStructure(EclipseSuite suite) {
		// Check the Suite
		WorkspaceDirectoryStructure structure = null;
		if (HardwareChecker.isProjectEnabled()) {
			structure = loadSuiteStructureInternal(suite);
			if (structure != null && structure.getSuiteStructure() != null) {
				return structure.getSuiteStructure();
			}
			// Check the Workspace
			structure = loadWorkspaceStructureInternal();
			if (structure != null && structure.getSuiteStructure() != null) {
				return structure.getSuiteStructure();
			}
			// Check the Configuration Directory
			structure = loadConfigurationStructureInternal();
			if (structure != null && structure.getSuiteStructure() != null) {
				return structure.getSuiteStructure();
			}
		}
		
		// Return the Default Directory Structure
		structure = new WorkspaceDirectoryStructure();
		structure.loadDefaultEmptyDirectories();
		return structure.getSuiteStructure();
	}
	
	
	/** Load the library directory structure from an existing library*/
	public static LibraryStructureHolder loadLibraryStructure(EclipseBaseProject project) {
		WorkspaceDirectoryStructure structure = null;
		if (HardwareChecker.isProjectEnabled()) {
			// Check Current Project
			structure = loadProjectStructureInternal(project);
			if (structure != null && structure.getLibraryStructure() != null) {
				return structure.getLibraryStructure();
			}
			// Check the Suite
			// Check the Suite
			EclipseSuite suite;
			if (project != null) {
				suite = (EclipseSuite) project.getSuiteReference().getObject();
			}
			else {
				suite = ActiveSuiteHolder.getDefault().getSuite();
			}

			structure = loadSuiteStructureInternal(suite);
			if (structure != null && structure.getLibraryStructure() != null) {
				return structure.getLibraryStructure();
			}
			// Check the Workspace
			structure = loadWorkspaceStructureInternal();
			if (structure != null && structure.getLibraryStructure() != null) {
				return structure.getLibraryStructure();
			}
			// Check the Configuration Directory
			structure = loadConfigurationStructureInternal();
			if (structure != null && structure.getLibraryStructure() != null) {
				return structure.getLibraryStructure();
			}
		}
		
		// Return the Default Directory Structure
		structure = new WorkspaceDirectoryStructure();
		structure.loadDefaultEmptyDirectories();
		return structure.getLibraryStructure();

	}
	
	/** Load the project directory structure from an existing project
	 *  1. Check in the Project Directory
	 *  2. Check in the Suite Directory
	 *  3. Check the Workspace
	 *  4. Check the Configuration
	 * */
	public static ProjectStructureHolder loadProjectStructure(EclipseBaseProject project) {
		WorkspaceDirectoryStructure structure = null;
		if (HardwareChecker.isProjectEnabled()) {
			// Check Current Project
			structure = loadProjectStructureInternal(project);
			if (structure != null && structure.getProjectStructure() != null) {
				return structure.getProjectStructure();
			}
			// Check the Suite
			EclipseSuite suite;
			if (project != null) {
				suite = (EclipseSuite) project.getSuiteReference().getObject();
			}
			else {
				suite = ActiveSuiteHolder.getDefault().getSuite();
			}
			structure = loadSuiteStructureInternal(suite);
			if (structure != null && structure.getSubProjectStructure() != null) {
				return structure.getProjectStructure();
			}
			// Check the Workspace
			structure = loadWorkspaceStructureInternal();
			if (structure != null && structure.getProjectStructure() != null) {
				return structure.getProjectStructure();
			}
			// Check the Configuration Directory
			structure = loadConfigurationStructureInternal();
			if (structure != null && structure.getProjectStructure() != null) {
				return structure.getProjectStructure();
			}
		}
		
		// Return the Default Directory Structure
		structure = new WorkspaceDirectoryStructure();
		structure.loadDefaultEmptyDirectories();
		return structure.getProjectStructure();
		
	}
	
	/** Load the subproject directory structure from an existing subproject
	 *  1. Load from the subproject directory if it exists
	 *  2. Combine with the project directory
	 *  3. Combine with the suite directory
	 *  4. Combine with e workspace directory
	 *  5. Combine with the configuration directory 
	 *  
	 *  
	 **/
	public static SubProjectStructureHolder loadSubProjectStructure(EclipseSubProject project) {
		WorkspaceDirectoryStructure structure = null;
		if (HardwareChecker.isProjectEnabled()) {
			// Check Subproject structure
			structure = loadProjectStructureInternal(project);
			if (structure != null && structure.getSubProjectStructure() != null) {
				return structure.getSubProjectStructure();
			}
			// Check Parent Project
			if (project!= null) structure = loadProjectStructureInternal(project.getParent());
			if (structure != null && structure.getSubProjectStructure() != null) {
				return structure.getSubProjectStructure();
			}
			// Check the Suite
			EclipseSuite suite;
			if (project != null) {
				suite = (EclipseSuite) project.getSuiteReference().getObject();
			}
			else {
				suite = ActiveSuiteHolder.getDefault().getSuite();
			}
			if (suite == null) suite = ActiveSuiteHolder.getDefault().getSuite();

			structure = loadSuiteStructureInternal(suite);
			if (structure != null && structure.getSubProjectStructure() != null) {
				return structure.getSubProjectStructure();
			}
			// Check the Workspace
			structure = loadWorkspaceStructureInternal();
			if (structure != null && structure.getSubProjectStructure() != null) {
				return structure.getSubProjectStructure();
			}
			// Check the Configuration Directory
			structure = loadConfigurationStructureInternal();
			if (structure != null && structure.getSubProjectStructure() != null) {
				return structure.getSubProjectStructure();
			}
		}
		
		// Return the Default Directory Structure
		structure = new WorkspaceDirectoryStructure();
		structure.loadDefaultEmptyDirectories();
		return structure.getSubProjectStructure();
		
	}
	
	
}
