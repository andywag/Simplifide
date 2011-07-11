package com.simplifide.core.project.source;

import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.source.design.DesignFile;


/** Class used in a project to contain the sources folders used for the design
 *  This allows the use of multipler source folders in the project
 * @author andy
 *
 */
public class ProjectSourceFolder extends ProjectSourceFolderTop {


	private ArrayList<EclipseSourceFolder> folderList = new ArrayList<EclipseSourceFolder>();
	
	public ProjectSourceFolder(EclipseBaseProject project,ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		super(project,suiteRef);
		init();
	}

	private void init() {
		ArrayList<StructureDirectory> dirList = this.getProject().getProjectStructure().getDesignList();
		if (dirList.size() == 0 || this.getProject().isSourceOnly()) { // When no design folder is specified use the base project folder
			EclipseSourceFolder sFolder = new EclipseSourceFolder(this.getProject().getBaseFolder(),this.getSuiteReference());
			this.folderList.add(sFolder);
		}
		else {
			// Always use a base folder called design if it exists
			IFolder design = this.getProject().getBasicDesignFolder();
			if (design.exists()) {
				EclipseSourceFolder sFolder = new EclipseSourceFolder(design,this.getSuiteReference());
				this.folderList.add(sFolder);
			}
			for (StructureDirectory dir : dirList) {
				IFolder folder = this.getProject().getBaseFolder();
				if (!dir.getName().equalsIgnoreCase("design")) {
					IFolder desFolder = folder.getFolder(dir.getName());
					EclipseSourceFolder sFolder = new EclipseSourceFolder(desFolder,this.getSuiteReference());
					folderList.add(sFolder);
				}
			}
			// If the number of folders is zero than the use the project base for files
			if (folderList.size() == 0) { 
				IFolder baseFolder = this.getProject().getBaseFolder();
				EclipseSourceFolder sFolder = new EclipseSourceFolder(baseFolder,this.getSuiteReference());
				folderList.add(sFolder);
			}
		}
		
	}
	


	
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#addDesignFile(com.simplifide.core.source.design.DesignFile)
	 */
	public void addDesignFile(DesignFile dfile) {
		/*
		for (EclipseSourceFolder folder : this.getFolderList()) {
			folder.addDesignFile(dfile);
		} */
	}
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#deleteObject()
	 */
	public void deleteObject() {
		for (EclipseSourceFolder folder : this.getFolderList()) {
			folder.deleteObject();
		}
		this.folderList.clear();
		
		super.deleteObject();
	}
	
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#getSourceList()
	 */
	public UniqueList<DesignFile> getSourceList() {
		UniqueList<DesignFile> designs = new UniqueList<DesignFile>();
		for (EclipseSourceFolder folder : this.getFolderList()) {
			folder.updateSourceList(designs);
		}
		return designs;
	}
	
	
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#removeDesignFile(com.simplifide.core.source.design.DesignFile)
	 */
	/*public void removeDesignFile(DesignFile designFile) {
		for (EclipseSourceFolder folder : this.getFolderList()) {
			folder.removeDesignFile(designFile);
		}
	}*/
	
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#loadFiles()
	 */
	public void loadFiles() {
		for (EclipseSourceFolder folder : this.getFolderList()) {
			folder.resolveDesignItems(this.getProject(), folder);
		}
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#createCompileList()
	 */
	public UniqueList<DesignFile> createCompileList() {
		UniqueList<DesignFile> dlist = new UniqueList<DesignFile>();
		for (EclipseSourceFolder folder : this.getFolderList()) {
			dlist.addAll(folder.createCompileList());
		}
		return dlist;
	}
	
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#convertContextList(com.simplifide.base.core.reference.ReferenceItem)
	 */
	public void convertContextList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		for (EclipseSourceFolder folder : this.getFolderList()) {
			folder.convertContextList(suiteRef);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#build(int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void build(int kind, IProgressMonitor monitor) {
		for (EclipseSourceFolder folder : this.getFolderList()) {
			folder.build(kind, monitor);
		}
	}

	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#setFolderList(java.util.ArrayList)
	 */
	public void setFolderList(ArrayList<EclipseSourceFolder> folderList) {
		this.folderList = folderList;
	}

	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#getFolderList()
	 */
	public ArrayList<EclipseSourceFolder> getFolderList() {
		return folderList;
	}



	
}
