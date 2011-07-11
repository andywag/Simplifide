/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.generator;


import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.structure.RootStructureLoader;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureLink;
import com.simplifide.core.project.structure.WorkspaceDirectoryStructure;
import com.simplifide.core.util.FileUtility;

/** Top level class used to create suites and projects */
public class TopGenerator {

	
	protected WorkspaceDirectoryStructure loadStructure(ProjectGeneratorOptions options) {
		WorkspaceDirectoryStructure wstr = RootStructureLoader.loadWorkspaceDirectory(options.getStructureXmlFile());
		return wstr;
	}
	
	 /** Creates the directory structure existing under this child */
	 protected void createDirectoryStructure(IProject baseFolder, StructureDirectory dir) {
		 for (StructureBase child1 : dir.getChildren()) {
			 StructureDirectory child = (StructureDirectory) child1;
			 IFolder ndir = baseFolder.getFolder(child.getName());
			 this.createFolder(ndir);
			 this.createDirectoryStructure(ndir, child);
		 }
	 }
	 
	 private IFolder createSingle(IFolder baseFolder, StructureDirectory child) {
		 if (baseFolder != null && !baseFolder.exists())
			try {
				baseFolder.create(true, true, null);
			} catch (CoreException e1) {
				HardwareLog.logError(e1);
			}
		 if (child.getType() == StructureDirectory.DIR) {
			 IFolder ndir = baseFolder.getFolder(child.getName());
			 this.createFolder(ndir);
			 return ndir;
		 }
		 else if (child.getType() == StructureDirectory.LINK) {
			 StructureLink link = (StructureLink) child;
			 String location = link.getLocation();
			 IPath path = new Path(location);
			 FileUtility.createLink(path, baseFolder);
		 }
		 else if (child.getType() == StructureDirectory.COPY) {
			 StructureLink link = (StructureLink) child;
			 String location = link.getLocation();
			 IPath path = new Path(location);
			 IFileStore store = EFS.getLocalFileSystem().getStore(path);
			 IFileStore dest = EFS.getLocalFileSystem().getStore(baseFolder.getLocation());
			 try {
				store.copy(dest, EFS.NONE, null);
			} catch (CoreException e) {
				HardwareLog.logError(e);
			} 
		 }
		 
		 return null;
		 
	 }
	 
	 /** Creates the directory structure existing under this child */
	 protected void createDirectoryStructure(IFolder baseFolder, StructureDirectory dir) {
		 for (StructureBase child1 : dir.getChildren()) {
			 StructureDirectory child = (StructureDirectory) child1;
			 IFolder ndir = this.createSingle(baseFolder, child);
			 if (ndir != null) this.createDirectoryStructure(ndir, child);
		 }
	 }
	
	/** Convenience Class used to generate a folder and catch the exception */
	protected boolean createFolder(IFolder folder) {
		try {
			folder.create(false, true, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
			return false;
		}
		return true;
	}
	
}
