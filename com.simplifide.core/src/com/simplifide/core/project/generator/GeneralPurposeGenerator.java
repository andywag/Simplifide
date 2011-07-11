/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.generator;


import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.structure.StructureBase;
import com.simplifide.core.project.structure.StructureDirectory;
import com.simplifide.core.project.structure.StructureFile;
import com.simplifide.core.util.FileUtility;

/** Top level class used to create suites and projects */
public class GeneralPurposeGenerator {

	 private static GeneralPurposeGenerator instance;
	
	 private GeneralPurposeGenerator() {}
	 
	 public static GeneralPurposeGenerator getInstance() {
		 if (instance == null) instance = new GeneralPurposeGenerator();
		 return instance;
	 }
	
	 /** Create this directory structure in the folder basefolder described by dir */
	 public void create(IContainer baseFolder, StructureDirectory dir) {
		 for (StructureBase base : dir.getChildren()) {
			 if (base.isDirectory()) {
				 this.createDirectory(baseFolder, (StructureDirectory)base);
			 }
			 else {
				 this.createFile(baseFolder, (StructureFile)base);
			 }
		 }
	 }
	
	 public void createDirectory(IContainer baseFolder, StructureDirectory dir) {
		IFolder folder = baseFolder.getFolder(new Path(dir.getName()));
		 if (dir.getLinkType() == StructureBase.LINK_NONE) { // Standard Directory Creation
			this.createFolder(folder);
			for (StructureBase base : dir.getChildren()) {
				if (base.isDirectory()) this.createDirectory(folder, (StructureDirectory)base);
				else this.createFile(folder, (StructureFile)base);
			}
		}
		else if (dir.getLinkType() == StructureBase.LINK_ECLIPSE) {	
			FileUtility.createLink(new Path(dir.getLocation()), folder);
		}
		else if (dir.getLinkType() == StructureBase.LINK_DISTRIBUTION) {
			FileUtility.createLinktoInstall(dir.getLocation(), folder);
		}
		else if (dir.getLinkType() == StructureBase.LINK_COPY) {
			FileUtility.copyLocalResource(dir.getLocation(), folder);
		}
		else if (dir.getLinkType() == StructureBase.LINK_COPY_DISTRIBUTION) {
			this.createFolder(folder);
			FileUtility.copyInstallResource(dir.getLocation(), dir.getName(), (IFolder)baseFolder);
		}
	 }
	 
	 public void createFile(IContainer baseFolder, StructureFile dir) {
		 IFile file = baseFolder.getFile(new Path(dir.getName()));
		 if (dir.getLinkType() == StructureBase.LINK_NONE) { // Standard Directory Creation
				// No real need to create an empty file so ...
		 }
		 else if (dir.getLinkType() == StructureBase.LINK_COPY) {
			 FileUtility.copyLocalResource(dir.getLocation(), file);
		 }
		 else if (dir.getLinkType() == StructureBase.LINK_ECLIPSE) {
			 FileUtility.createFileLink(new Path(dir.getLocation()), file);
		 }
		 else if (dir.getLinkType() == StructureBase.LINK_DISTRIBUTION) {
			 FileUtility.createFileLinktoInstall(dir.getLocation(), file);
		 }
		 else if (dir.getLinkType() == StructureBase.LINK_COPY_DISTRIBUTION) {
			 FileUtility.copyInstallResourceFile(dir.getLocation(), file);
		 }
		 else if (dir.getLinkType() == StructureBase.LINK_NEW) {
			 IFile nfile = baseFolder.getFile(new Path(dir.getName()));
			 ByteArrayInputStream bs = new ByteArrayInputStream(dir.getContents().getBytes());
			 try {
				nfile.create(bs, true, null);
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
			 //nfile.create
			 //FileUtility.copyInstallResourceFile(dir.getLocation(), file);
		 }
	 }
	 
	 /** Convenience Class used to generate a folder and catch the exception */
	protected boolean createFolder(IFolder folder) {
		try {
			if (!folder.exists()) folder.create(false, true, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
			return false;
		}
		return true;
	}

	
	
}
