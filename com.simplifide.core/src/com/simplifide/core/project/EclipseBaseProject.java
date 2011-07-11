/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.doc.EclipseProjectDocHandler;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.source.ProjectSourceFolder;
import com.simplifide.core.project.source.ProjectSourceFolderTop;
import com.simplifide.core.project.source.ProjectSourceList;
import com.simplifide.core.project.structure.ProjectStructureHolderTop;
import com.simplifide.core.project.suitecontents.FileContentObject;
import com.simplifide.core.source.ProjectLocationHandler;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.util.FileUtility;
import com.simplifide.core.verilog.editor.describer.VerilogFile;
import com.simplifide.core.vhdl.describer.VhdlFile;

public abstract class EclipseBaseProject extends CoreProjectBasic<InstanceModuleTop>{

	private static final long serialVersionUID = 1L;

	private IFolder baseFolder;
	private EclipseProjectDocHandler docHolder;
	private ProjectStructureHolderTop projectStructure;

    private ProjectSourceFolderTop sourceFolder;
    private ProjectXmlHandler projectXMLHandler;
    private EclipseSubProjectHolder subProjects;

    private UniqueList<DesignFile> orderedList;
    
    private boolean sourceOnly = false;
    
    private String location;
    private boolean link;
    
    

	public EclipseBaseProject(String name, IFolder folder, ReferenceItem<EclipseSuite> suiteRef) {
		this(name,folder,suiteRef,null);
	}
	public EclipseBaseProject(String name, 
							  IFolder folder, 
							  ReferenceItem<EclipseSuite> suiteRef,
							  List<FileContentObject> files) {
		super(name);
		this.baseFolder = folder;
		this.setSuiteReference(suiteRef);
		init(files);
	}
	

	private void init(List<FileContentObject> files) {
		
		this.projectXMLHandler = new ProjectXmlHandler(this);
		this.projectStructure = this.loadProjectStructure();
		this.subProjects = new EclipseSubProjectHolder(this);
		if (baseFolder != null) {
			this.setSuiteLocation(ProjectLocationHandler.getInstance().registerLocation(this));
			this.docHolder = new EclipseProjectDocHandler(this);
			// Create the Source Folder for the Design
			// Either Location Base or Source Based if File List Exists
			this.createProjectSourceFolder(files);
			
		}
		
	}

	
	
	public IFile getSourceXmlFile() {
		return this.getBaseFolder().getFile(ProjectSourceList.SOURCE_LOCATION);
	}
	
	public boolean isFileList() {
		List<FileContentObject> sfiles = ProjectSourceList.getFixedFiles(this);  
		if (sfiles.size() > 0) return true;
		return false;
	}
	
	public List<File> initialFiles() {
		List<java.io.File> outputs = new ArrayList<java.io.File>();
		List<FileContentObject> sfiles = ProjectSourceList.getFixedFiles(this);  
		for (FileContentObject sfile : sfiles) {
			outputs.add(new File(sfile.getLocation()));
		}
		return outputs;
	}
	
	private void createProjectSourceFolder(List<FileContentObject> files) {
		// Check with Content.xml
		List<FileContentObject> sfiles = ProjectSourceList.getFixedFiles(this);  
		// Check the input file
		if (sfiles.size() == 0 && files != null) sfiles = files;
		
		if (sfiles.size() > 0) { // Fixed Project File List
			this.sourceFolder = new ProjectSourceList(this, this.getSuiteReference(),sfiles);
		}
		else {
			this.sourceFolder = new ProjectSourceFolder(this,this.getSuiteReference());
		}
		
		
	}
	

	
	
	
	public abstract ProjectStructureHolderTop loadProjectStructure();
	
	/** Returns a folder which is always assumed to contain design files
	 *  This is used by EclipseTopSourceFolder
	 */
	public IFolder getBasicDesignFolder() {
		return this.getBaseFolder().getFolder("design");
	}
	
	/** This method adds a design file to the appropriate source folder. This is called when 
	 *  an addition occurs in BuilderChangeState
	 * @param dfile
	 */
	/*public void addDesignFile(DesignFile dfile) {
		this.getSourceFolder().addDesignFile(dfile);
	}*/
	/** Goes through all of the projects in the design and converts the list
	 * of dependent libraries into actual files 
	 */
	public void convertContextList() {
		if (this.getSourceFolder() != null)
			this.getSourceFolder().convertContextList(this.getSuiteReference());
	}
	
	public ArrayList<CoreProjectBasic> getRequiredProjects() {
		ArrayList<CoreProjectBasic> projects = new ArrayList<CoreProjectBasic>();
		for (ReferenceItem<? extends CoreProjectBasic> project : this.getProjectCompileList().getGenericSelfList()) {
			if (project.getObject() == this) break;
			projects.add(project.getObject());
		}
		
		return projects;
	}
	
	/** Secondary method which creates the true compile list for this project */
	public UniqueList<CoreProjectBasic> getProjectCompileList() {
		UniqueList<CoreProjectBasic> projectList = new UniqueList<CoreProjectBasic>();
		EclipseSuite suite = (EclipseSuite) this.getSuiteReference().getObject();
		for (ReferenceItem<? extends DesignFile> dref : suite.getCompileList().getGenericSelfList()) {
			DesignFile dfile = dref.getObject();
			projectList.addObject(dfile.getProjectRef());
		}
		return projectList;
	}
	
	
	public UniqueList<DesignFile> getSourceList() {
		return this.getSourceFolder().getSourceList();
	}
	
	public IFile getConfigFile() {
		return this.getBaseFolder().getFile(ProjectStructureHolderTop.FILE_CONFIG);	
	}
	
	public void deleteConfigFile() {
		try {
			this.getConfigFile().delete(true, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	

	public IFolder getDocFolder() {
		return this.baseFolder.getFolder(this.projectStructure.getDocStructure().getName());
	}

	public IFolder getDesignIFolder() {
		return this.baseFolder.getFolder(this.projectStructure.getBaseDesignStructure().getName());
	}
	
	public IFolder getBuildFolder() {
		if (this.projectStructure.getBuildStructure() == null || 
			this.projectStructure.getBuildStructure().getName() == null) return null;
		
		String name = this.projectStructure.getBuildStructure().getName();
		return this.baseFolder.getFolder(name);
	}
	
	public IFolder getScriptfolder() {
		return this.baseFolder.getFolder(this.projectStructure.getScriptStructure().getName());
	}
	
	public IFolder getTestFolder() {
		return this.baseFolder.getFolder(this.projectStructure.getTestStructure().getName());
	}
	
	public IFolder getSynthesisFolder() {
		return this.baseFolder.getFolder(this.projectStructure.getSynthesisStructure().getName());
	}
	
	public IFolder getSubProjectFolder() {
			ProjectStructureHolderTop pstruct = (ProjectStructureHolderTop) this.getProjectStructure();
			String folderName = pstruct.getSubProjectStructureName();
			if (folderName == null) return null;
			return baseFolder.getFolder(folderName);
		
	}
	
	/** Convenience method which returns all packags in this project */
	public List<InstancePackage> getPackageList() {
		return this.findRealPrefixItemList(new ModuleObjectBaseItem(""), 
				ReferenceUtilities.REF_INSTANCE_PACKAGE);
	}

	/** Convenience method which returns all the modules in this project */
	public List<InstanceModule> getModuleList() {
		return this.findRealPrefixItemList(new ModuleObjectBaseItem(""), 
				ReferenceUtilities.REF_INSTANCE_MODULE);
	}



	public void deleteObject() {
		this.projectStructure = null;
		this.projectXMLHandler = null;
		this.baseFolder = null;
		this.subProjects = null;
		if (this.sourceFolder != null)
			this.sourceFolder.deleteObject();
		this.sourceFolder = null;
		this.docHolder = null;
		super.deleteObject();
	}



	/** Removes the files from the source list in the source folder */
	/*public void removeDesignFile(DesignFile designFile) {
		this.getSourceFolder().removeDesignFile(designFile);
	}*/

	/** Loads up the files and subprojects when the project is created */
	public void loadFiles() {
		this.subProjects.loadFiles();
		this.getSourceFolder().loadFiles();
	}

	/** Defaults to get number of files in this design */
	public int getBuildTime() {
		//if (this.getSourceFolder() != null) return this.getSourceFolder().getBuildTime();
		return 0;
	}
	/** Creates the compile list for this project */
	public UniqueList<DesignFile> createCompileList() {
		UniqueList<DesignFile> files =  this.getSourceFolder().createCompileList();
		UniqueList<DesignFile> lis = new UniqueList<DesignFile>();
		if (files == null) return new UniqueList<DesignFile>();
		for (ReferenceItem<? extends DesignFile> dfile : files.getGenericSelfList()) {
			if (dfile != null && dfile.getObject() != null && dfile.getObject().getProjectRef() != null) {
				if (dfile.getObject().getProjectRef().getObject() == this) {
					lis.addReferenceItem(dfile);
				}
			}
		}
		this.setOrderedList(lis);
		return files;
	}
	
	public List<DesignFile> getCompileArrayList() {
		ArrayList<DesignFile> files = new ArrayList<DesignFile>();
		for (ReferenceItem<? extends DesignFile> dfile : this.getOrderedList().getGenericSelfList()) {
			if (dfile.getObject().getProjectRef().getObject() == this) {
				files.add(dfile.getObject());
			}
		}
		return files;
	}
	
	/** Builds the Design */
	public void build(int kind,  IProgressMonitor monitor)  {
		if (this.sourceFolder != null) this.sourceFolder.build(kind, monitor);
	}

	

	public File getDetailXmlFileLocation() {
		IFile ifile =  this.getBaseFolder().getFile("details.xml");
		return FileUtility.convertIFile2File(ifile);
	}
	

	/** Gets a List of Projects used by this project
	 *  USED BY PYTHON SCRIPTS
	 * @return
	 */
	public ArrayList<CoreProjectBasic> getProjectArrayList() {
		return this.getProjectCompileList().getRealArrayList();
	}

	public ArrayList<DesignFile> getSourceArrayList() {
		return this.getSourceList().getRealArrayList();
	}

	public ArrayList<DesignFile> getVerilogList() {
		ArrayList<DesignFile> designList = new ArrayList<DesignFile>();
		for (DesignFile dfile : this.getSourceArrayList()) {
			if (dfile instanceof VerilogFile) {
				designList.add(dfile);
			}
		}
		return designList;
	}
	
	public ArrayList<DesignFile> getVhdlList() {
		ArrayList<DesignFile> designList = new ArrayList<DesignFile>();
		for (DesignFile dfile : this.getSourceArrayList()) {
			if (dfile instanceof VhdlFile) {
				designList.add(dfile);
			}
		}
		return designList;
	}
	
	/** This is kind of a kludge. It looks through all of the files in the hierarchy to find the highest
	 *  file in this project 
	 */
	public String getMainModule() {
		EclipseSuite suite = (EclipseSuite) this.getSuiteReference().getObject();
		List<DesignFile> files = suite.getCompileArrayList();
		String last = "";
		for (DesignFile file : files) {
			CoreProjectBasic proj = file.getProjectRef().getObject();
			if (proj == this) {
				String ufile = file.getname();
				String[] p = ufile.split("\\.");
				last = p[0];
			}
		}
		return last;
	}
	
	public IFile getMakefile() {
		return this.getBaseFolder().getFile("Makefile");
	}
	
	
	
	/** End Convenience Methods */

	public void setBaseFolder(IFolder baseFolder) {
		this.baseFolder = baseFolder;
	}

	public IFolder getBaseFolder() {
		return baseFolder;
	}

	public String getCLocation() {
		IFolder folder = this.getBaseFolder().getFolder("src_c");
		return folder.getLocationURI().getPath();
	}
	

	public void setDocHolder(EclipseProjectDocHandler docHolder) {
		this.docHolder = docHolder;
	}

	public EclipseProjectDocHandler getDocHolder() {
		return docHolder;
	}

	public void setProjectStructure(ProjectStructureHolderTop projectStructure) {
		this.projectStructure = projectStructure;
	}

	public ProjectStructureHolderTop getProjectStructure() {
		return projectStructure;
	}


	public void setSourceFolder(ProjectSourceFolderTop designFolderNew) {
		this.sourceFolder = designFolderNew;
	}

	public ProjectSourceFolderTop getSourceFolder() {
		return sourceFolder;
	}

	public void setProjectXMLHandler(ProjectXmlHandler projectXMLHandler) {
		this.projectXMLHandler = projectXMLHandler;
	}

	public ProjectXmlHandler getProjectXMLHandler() {
		return projectXMLHandler;
	}

	public void setSourceOnly(boolean sourceOnly) {
		this.sourceOnly = sourceOnly;
	}

	public boolean isSourceOnly() {
		return sourceOnly;
	}
	public void setOrderedList(UniqueList<DesignFile> orderedList) {
		this.orderedList = orderedList;
	}
	public UniqueList<DesignFile> getOrderedList() {
		return orderedList;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocation() {
		return location;
	}
	public void setLink(boolean link) {
		this.link = link;
	}
	public boolean isLink() {
		return link;
	}


	

	

	


	





}
