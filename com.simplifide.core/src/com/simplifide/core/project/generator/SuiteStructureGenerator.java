/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.generator;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.contents.LibraryContents;
import com.simplifide.core.project.contents.ProjectContents;
import com.simplifide.core.project.contents.ProjectTopContents;
import com.simplifide.core.project.contents.SuiteContents;
import com.simplifide.core.project.prj.ProjectFileLoader;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.suitecontents.SuiteContentLoader;
import com.simplifide.core.project.suitecontents.SuiteContentObject;
import com.simplifide.core.util.FileUtility;

public class SuiteStructureGenerator extends TopGenerator{


	private static SuiteStructureGenerator instance;

	public SuiteStructureGenerator() {}

	public static SuiteStructureGenerator getDefault() {
		if (instance == null) instance = new SuiteStructureGenerator();
		return instance;
	}

	/* Method to create the IEEE Link */
	public void createIeeeLink(IFolder lib) {
		if (lib != null && lib.exists()) {
			FileUtility.createLinktoInstall("resources/vhdl_libs/ieee",lib.getFolder("ieee"));
		}
	}
	
	/* Method to create the IEEE Proposed Link */
	public void createIeeeProposedLink(IFolder lib) {
		if (lib != null && lib.exists()) {
			FileUtility.createLinktoInstall("resources/vhdl_libs/ieee_proposed", lib.getFolder("ieee_proposed"));
		}
	}

	/* Method to create the STD Link */
	public void createStdLink(IFolder lib) {
		if (lib != null && lib.exists()) {
			FileUtility.createLinktoInstall("resources/vhdl_libs/std", lib.getFolder("std"));
		}
	}
	
	public void createLibraryLink(IFolder folder, String name) {
		if (folder != null && folder.exists()) {
			FileUtility.createFileLinktoInstall("resources/libraries/"+name, folder.getFile(name));
		}
	}
	
	
	private void addLibrary(SuiteContents contents, String name) {
		LibraryContents lib = new LibraryContents(name + ".har");
		lib.setLink(StructureFile.LINK_COPY_DISTRIBUTION, "resources/libraries/" + name + ".har");
		lib.setFileLink(true);
        contents.getLibraries().add(lib);
	}
	
	
	private void convertSuiteContentObject(SuiteContents suite, SuiteContentObject object) {
		ProjectTopContents cont;
		if (object.getType() == SuiteContentObject.PROJECT) {
			cont = new ProjectContents(object.getName());
			suite.getProjects().add((ProjectContents)cont);
		}
		else {
			cont = new LibraryContents(object.getName());
			suite.getLibraries().add((LibraryContents)cont);
		}
		cont.setLinkLocation(object.getLocation());
		if (object.isLink()) cont.setLinkType(StructureFile.LINK_ECLIPSE);
		
	}
	
	private void loadContentFile(SuiteContents contents, SuiteGeneratorOptions options) {
		StructureFile struct = options.getContentXmlFile();
		if (struct != null && struct.getLocation() != null) {
			File xmlFile = new File(struct.getLocation());
			ArrayList<SuiteContentObject> content = SuiteContentLoader.loadContents(xmlFile);
			for (SuiteContentObject object : content) {
				convertSuiteContentObject(contents,object);
			}
		}
	}
	
	private void loadAllFiles(SuiteContents contents, SuiteGeneratorOptions options) {
		StructureFile struct = options.getAllFiles();
		if (struct != null && struct.getLocation() != null) {
			
		}
	}
	
	/** Creates the default suite contents based on the options */
	private SuiteContents createDefaultSuiteContents(SuiteGeneratorOptions options) {
        SuiteContents suiteContents = new SuiteContents();
        this.loadContentFile(suiteContents, options);
        
     
        if (options.getLibraries().ieee) addLibrary(suiteContents, "ieee");
        if (options.getLibraries().ieee_proposed) addLibrary(suiteContents, "ieee_proposed");
        if (options.getLibraries().std) addLibrary(suiteContents, "std");
        if (options.getLibraries().ovm) addLibrary(suiteContents, "ovm");
        if (options.getLibraries().uvm) addLibrary(suiteContents, "uvm");
        if (options.getLibraries().vmm) addLibrary(suiteContents, "vmm");
        if (options.getLibraries().unisimVHDL) addLibrary(suiteContents, "unisim_vhdl");        
        if (options.getLibraries().unisimVerilog) addLibrary(suiteContents, "unisim_verilog");

    	StructureFile sfile = new StructureFile("Start.py");
    	StructureFile pfile = new StructureFile("Paths.py");
    	
    	sfile.setLinkType(StructureDirectory.LINK_COPY_DISTRIBUTION);
    	pfile.setLinkType(StructureDirectory.LINK_COPY_DISTRIBUTION);

    	sfile.setLocation("resources/script/Start.py");
    	pfile.setLocation("resources/script/Paths.py");
    	
        suiteContents.getScriptHolder().addChild(sfile);
        suiteContents.getScriptHolder().addChild(pfile);

        
        return suiteContents;
	}
	
	
	
	private void createLibraryContents(SuiteGeneratorOptions options, 
			SuiteContents contents,
			IProject project) {
		if (options.getLibraryDirs() == null) return;
		
		for (String lib : options.getLibraryDirs()) {
			//String[] p = lib.split("//.");
			//String name = p[p.length-1];
			File loc = new File(lib);
			String name = loc.getName();
			
			IFolder ref = options.getDirStructure().getMainLibraryFolder(project);
			IFolder lib1 = ref.getFolder(name);
			FileUtility.createLink(new Path(lib), lib1);
			//LibraryGenerator.getDefault().createProjectWithLinkedFolder(options.getLibraryStructure(), 
			//		lib1, lib);
		}
		
	}

	private void createProjects(SuiteGeneratorOptions options, IProject project) {
		IFolder ref = options.getDirStructure().getMainProjectFolder(project);
		ArrayList<ProjectGeneratorOptions> projects =  options.getNewProjects();
		for (ProjectGeneratorOptions project1 : projects) {
			ProjectGenerator.getDefault().createProjectfromWizard(ref, project1);
		}
	}
	
	private void createLibraries(SuiteGeneratorOptions options, IProject project) {
		IFolder ref = options.getDirStructure().getMainLibraryFolder(project);
		ArrayList<ProjectGeneratorOptions> projects =  options.getNewLibraries();
		for (ProjectGeneratorOptions project1 : projects) {
			ProjectGenerator.getDefault().createProjectfromWizard(ref, project1);
		}
	}
	
	public StructureFile createContentFile(SuiteGeneratorOptions options) {
		 ArrayList<SuiteContentObject> objects = new ArrayList<SuiteContentObject>();
		 for (ProjectGeneratorOptions option : options.getNewProjects()) {
			 
			 SuiteContentObject obj = new SuiteContentObject(option.getName());
			 if (option.getStructureLocation() != null) obj.setLocation(option.getStructureLocation().getLocation());
			 obj.setLink(option.isLink());
			 obj.setType(SuiteContentObject.PROJECT);
			 obj.setSourceOnly(option.isSourceOnly());
			 objects.add(obj);
		 }
		 for (ProjectGeneratorOptions option : options.getNewLibraries()) {
			 SuiteContentObject obj = new SuiteContentObject(option.getName());
			 if (option.getStructureLocation() != null) {
				 obj.setName(option.getStructureLocation().getName());
				 obj.setLocation(option.getStructureLocation().getLocation());
			 }
			 obj.setLink(option.isLink());
			 obj.setType(SuiteContentObject.LIBRARY);
			 obj.setSourceOnly(option.isSourceOnly());
			 objects.add(obj);
		 }
		 String content = SuiteContentLoader.createContents(objects);
		 StructureFile content1 = new StructureFile("Content.xml");
		 content1.setContents(content);
		 content1.setLinkType(StructureFile.LINK_NEW);
		 return content1;
		 
	}
	
	private void createProjectsFromPrjFile(SuiteGeneratorOptions options,
			IProject iproject) {
		String loc = options.getProjectFileLocation();
		if (loc == null) return;
		ProjectFileLoader loader = new ProjectFileLoader();
		loader.loadProjectFile(new File(loc));
		
		IFolder ref = options.getDirStructure().getMainProjectFolder(iproject);
		
		for (ProjectFileLoader.Project project : loader.getProjects()) {
			ProjectGeneratorOptions proj = new ProjectGeneratorOptions(project.name);
			StringBuilder builder = new StringBuilder();
			for (ProjectFileLoader.File fil : project.files) {
				builder.append(fil.name + "\n");
			}
			StructureFile ufile = new StructureFile("structure.xml");
			ufile.setLocation("structure.xml");
			ufile.setLinkType(StructureFile.LINK_NEW);
			ufile.setContents(builder.toString());
			proj.setSourceOnly(true);
			proj.setAllFiles(ufile);
			proj.setNewproject(true);
			ProjectGenerator.getDefault().createProjectfromWizard(ref, proj);
		}
		
	}
	
	public void createProject(IProjectDescription description,IProject projectHandle, 
			SuiteGeneratorOptions options) {		
		
		SuiteContents contents = this.createDefaultSuiteContents(options);
		contents.setStructureXmlFile(options.getStructureXmlFile());
		contents.setContentXmlFile(options.getContentXmlFile());
		contents.setAllFile(options.getAllFiles());
		
		if (options.getContentXmlFile() == null) {
			contents.setContentXmlFile(this.createContentFile(options));
		}
		
		options.setWorkspaceStructure(RootStructureLoader.loadWorkspaceDirectory(options.getStructureXmlFile()));
		StructureDirectory dir =  contents.createStructureDirectory();
		GeneralPurposeGenerator.getInstance().create(projectHandle, dir);

		this.createLibraryContents(options, contents, projectHandle);
		this.createProjects(options, projectHandle);
		this.createLibraries(options, projectHandle);
		
		this.createProjectsFromPrjFile(options, projectHandle);

		if (description.getLocationURI() != null) {
			try {
				projectHandle.move(description, true, null);
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
		}


	}



	
}
