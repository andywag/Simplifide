package com.simplifide.core.project.suitecontents;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.LibraryGenerator;
import com.simplifide.core.project.generator.ProjectGenerator;
import com.simplifide.core.project.generator.ProjectGeneratorOptions;
import com.simplifide.core.project.library.EclipseLibraryProject;
import com.simplifide.core.util.FileUtility;

public class SuiteContentObject {

	public static final int LIBRARY = 0;
	public static final int PROJECT = 1;
	
	private int type;
	
	private String name;
	private String location;
	private boolean sourceOnly = false;
	private boolean link = true;
	
	private List<FileContentObject> files;
	public SuiteContentObject(String name) {
		this(name,null);
	}
	public SuiteContentObject(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	public SuiteContentObject(EclipseBaseProject project) {
		this.name = project.getname();
		this.location = project.getLocation();
		this.link = project.isLink();
		this.sourceOnly = project.isSourceOnly();
		IFolder baseLocation = project.getBaseFolder();
		if (baseLocation != null && baseLocation.isLinked()) {
			this.link = true;
			this.location = baseLocation.getLocation().toOSString();
		}
		
		if (project instanceof EclipseLibraryProject) this.type = LIBRARY;
		else this.type = PROJECT;
	}
	
	private IFolder getLinkedLocation(EclipseSuite suite) {
		if (this.getLocation() != null && (!(this.getLocation().equalsIgnoreCase("")))) { // Non Default Location Case
    		File ufile = new File(this.getLocation());
    		if (ufile.isAbsolute()) {
    			IFileStore store = EFS.getLocalFileSystem().getStore(new Path(this.getLocation()));
    			URI uri = store.toURI();
    			return (IFolder) FileUtility.convertURItoResourceFolder(uri);    			
    		}
    		else {
    			return suite.getProject().getFolder(new Path(this.getLocation()));
    		}
		}
		return null;
	}
	
	/** Returns the location where the project is supposed to exist at */
	private IFolder getBaseLocation(EclipseSuite suite) {
		
		if (!this.isLink() && this.getLocation() != null && (!(this.getLocation().equalsIgnoreCase("")))) { // Non Default Location Case
    		File ufile = new File(this.getLocation());
    		if (ufile.isAbsolute()) {
    			IFileStore store = EFS.getLocalFileSystem().getStore(new Path(this.getLocation()));
    			URI uri = store.toURI();
    			return (IFolder) FileUtility.convertURItoResourceFolder(uri);    			
    		}
    		else {
    			return suite.getProject().getFolder(new Path(this.getLocation()));
    		}
		}
		if (this.type == LIBRARY) {
			for (IFolder folder : suite.getLibraryFolders()) {
				IFolder ufolder = folder.getFolder(this.name);
				if (ufolder.exists()) return ufolder;
			}
			IFolder fold = suite.getMainLibraryFolder();
			if (fold == null) return null;
			return fold.getFolder(this.name);
		}
		else {
			for (IFolder folder : suite.getProjectFolders()) {
				IFolder ufolder = folder.getFolder(this.name);
				if (ufolder.exists()) return ufolder;
			}
			IFolder fold = (IFolder) suite.getMainProjectFolder();
			if (fold == null) return null;
			return fold.getFolder(new Path(this.name));
		}
		
	}
	
	private IFolder createLink(EclipseSuite suite, IContainer folder) {
		
		IFolder res = null;
		if (this.type == LIBRARY) {
			IFolder main = suite.getMainLibraryFolder();
			res  = main.getFolder(this.getName()); // Folder to link to
		}
		else {
			IContainer main = suite.getMainProjectFolder();
			res  = main.getFolder(new Path(this.getName())); // Folder to link to
		}
		
		
		FileUtility.createLink(new Path(this.location), res);
		//folder = res; // For the linked case use the linked result folder
		if (!res.exists()) { // If this fails return an error
			HardwareLog.logInfo("Couldn't create link to " + this.location);
			return null;
		}
		return res;
	}
	
	private void createProject(EclipseSuite suite, IContainer folder) {
		ProjectGeneratorOptions opt = new ProjectGeneratorOptions(this.getName());
		opt.setNewproject(true);
		IFolder parent = (IFolder) folder.getParent();
		if (this.getType() == LIBRARY) LibraryGenerator.getDefault().createProjectfromWizard(parent, opt);
		else 						   ProjectGenerator.getDefault().createProjectfromWizard(parent, opt);
	}
	
	public void loadProject(EclipseSuite suite) {
		IFolder folder = this.getBaseLocation(suite);
		if (folder == null) { // If the folder doesn't exist stop working
			HardwareLog.logInfo("Location " + this.location + " doesn't exist");
			return;
		}
		if (!folder.exists()) { // If the folder doesn't exist the project needs to be generated
			if (this.isLink()) {
				folder = this.createLink(suite, folder);
				if (folder == null) return;
			}
			else {
				this.createProject(suite, folder);
			}
		}
		if (!folder.exists()) {
			HardwareLog.logInfo("Issue creating project " + this.name);
		}
		// Actually load the projects after they have been created 
		EclipseBaseProject project;
		if (this.type == LIBRARY) project = new EclipseLibraryProject(this.getName(),folder,suite.createReferenceItem());
		else                      project = new EclipseProject(this.getName(),folder,suite.createReferenceItem());
		project.setSourceOnly(sourceOnly);
		project.setLink(this.isLink());
		project.setLocation(this.location);
		project.loadFiles();
		if (this.type == LIBRARY) suite.getLibraryReference().addReferenceItem(project.createReferenceItem());
		else suite.createReferenceItem().addReferenceItem(project.createReferenceItem());
	}
	
	

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setSourceOnly(boolean sourceOnly) {
		this.sourceOnly = sourceOnly;
	}

	public boolean isSourceOnly() {
		return sourceOnly;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public boolean isLink() {
		return link;
	}

	public void setFiles(List<FileContentObject> files) {
		this.files = files;
	}

	public List<FileContentObject> getFiles() {
		return files;
	}

}
