package com.simplifide.core.project.generator;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.contents.ProjectTopContents;
import com.simplifide.core.project.source.ProjectSourceList;
import com.simplifide.core.project.structure.ProjectStructureHolderTop;
import com.simplifide.core.project.structure.RootStructureDirectory;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;
import com.simplifide.core.project.suitecontents.ProjectContentLoader;
import com.simplifide.core.util.FileUtility;

public abstract class ProjectTopGenerator extends TopGenerator{

	public abstract StructureDirectory createStructureDirectory(WorkspaceDirectoryStructure wstr, ProjectTopContents contents);
	public abstract ProjectStructureHolderTop loadStructure();

	
	protected StructureDirectory loadStructureDirectory(ProjectGeneratorOptions options) {
		WorkspaceDirectoryStructure wstr = RootStructureLoader.loadFromFileOnly(options.getStructureXmlFile());
		if (wstr == null) {
			ProjectStructureHolderTop holder = loadStructure();
			return holder.getStructureDirectory();
		}
		else {
			StructureDirectory struct =  this.createStructureDirectory(wstr,null);
			return struct;
		}
	}
	
	public static void handleAllFiles(StructureFile ur, StructureDirectory dir) {
		// Handle the Fix Source Files
		if (ur != null) {
			if (ur.getName().equalsIgnoreCase(ProjectSourceList.SOURCE_LOCATION)) {
				dir.addChild(ur);
			}
			else {
				File ufile = new File(ur.getLocation());
				String text = ProjectContentLoader.convertText(ur);
				StructureFile nfile = new StructureFile("Source.xml");
				nfile.setLocation("Source.xml");
				nfile.setLinkType(StructureBase.LINK_NEW);
				nfile.setContents(text);
				dir.addChild(nfile);
			}
		}
		
	}
	
	public void createProjectfromWizard(IContainer folder, ProjectGeneratorOptions options) {
		StructureDirectory dir = this.loadStructureDirectory(options);
		
		if (options.getStructureXmlFile() != null) dir.addChild(options.getStructureXmlFile());
		
		handleAllFiles(options.getAllFiles(), dir);
		
		dir.setName(options.getName());
		RootStructureDirectory top = new RootStructureDirectory();
		top.addChild(dir);
		if (options.isNewproject()) {
			GeneralPurposeGenerator.getInstance().create(folder, top);
		}
		else if (options.isLink()) {
			FileUtility.createLink(new Path(options.getStructureLocation().getLocation()),
					folder.getFolder(new Path(options.getName())));
		}
		else if (options.isCopy()) {
			FileUtility.copyLocalResource(options.getStructureLocation().getLocation(), 
					folder.getFolder(new Path(options.getName())));
		}
	}
	
	/** Creates a new Project */
	private void createBaseProject(ProjectStructureHolderTop structure,IFolder folder) {
		this.createDirectoryStructure(folder, structure.getStructureDirectory());
	}
	
	/** Creates a new project with a local directory  */
	public void createProjectWithLocalDesign(ProjectStructureHolderTop structure,IFolder folder, String copyPath) {
		this.createBaseProject(structure, folder);
		IFolder nfolder = folder.getFolder(structure.getDesignsStructureName());
		FileUtility.copyLocalResource(copyPath, nfolder);
		try {
			nfolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	/** Creates a new project with a local directory  */
	public void createProjectWithLinkedFolder(ProjectStructureHolderTop structure,IFolder folder, String copyPath) {
		this.createBaseProject(structure, folder);
		IFolder nfolder = folder.getFolder(structure.getDesignsStructureName());
		//String[] s = copyPath.split("//.");
		//IFolder ufolder = nfolder.getFolder(s[s.length-1]);
		
		FileUtility.createLink(new Path(copyPath), nfolder);
		try {
			nfolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
}
