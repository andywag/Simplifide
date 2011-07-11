/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;


public class FileUtility {

	
	public static String readFileContents(String filename) {
		StringBuilder contents = new StringBuilder();
	    File afile = new File(filename);
	    if (!afile.exists()) return   null;
	    try {
	     
	      BufferedReader input =  new BufferedReader(new FileReader(afile));
	      try {
	        String line = null; //not declared within while loop
	    
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    return contents.toString();

	}
	
	public static long getLength(IFile ifile) {
		return convertIFile2File(ifile).length();
	}
	
	/** Return all IFiles which correspond to this URI */
	public static IFile[] convertURItoFileArray(URI uri) {
		return ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri);
	}
	
	public static IFolder convertURItoResourceFolder(URI uri) {
		URI baseURI = ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
		URI rel = baseURI.relativize(uri);
		IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(rel.toString()));
		return folder;
	}
	
	private static boolean isDirectory(URI resource) {
		if (resource.getScheme().equalsIgnoreCase("file")) {
			File ufile = new File(resource);
			if (ufile.isDirectory()) return true;
		}
		return false;
	}
	
	public static IResource convertURItoResource(URI uri) {
		
		if (isDirectory(uri)) {
			IContainer[] cont = ResourcesPlugin.getWorkspace().getRoot().findContainersForLocationURI(uri);
			if (cont.length > 0) {
				return cont[0];
			}
		}
		
		IResource[] farr = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri);
		if (farr.length <= 0) {
			farr = ResourcesPlugin.getWorkspace().getRoot().findContainersForLocationURI(uri);
		}
		if (farr.length == 1) return farr[0];
		if (farr.length > 1) {
			EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
			if (suite == null) return null;
			IProject project = suite.getProject();
			for (IResource file : farr) {
				IContainer ufile = file.getParent();
				while (ufile != null) {
					if (ufile == project) return file;
					ufile = ufile.getParent();
				}
			}
		}
		return null;
	}
	
	public static IFile convertURItoFile(URI uri) {
		IResource res = convertURItoResource(uri);
		if (res instanceof IFile) return (IFile) res;
		return null;
	}
	
	public static File convertIFolder2File(IContainer ifile) {
		URI locURI    = ifile.getLocationURI();
		File nfile = new File(locURI.getPath());
		return nfile;
	}
	
	public static File convertIFile2File(IFile ifile) {
		URI locURI    = ifile.getLocationURI();
		File nfile = new File(locURI.getPath());
		return nfile;
	}
	
	public static IFile getResourceforPath(String path) {
		
		File ufile = new File(path);
		
		if (ufile.exists()) {
			URI uri = ufile.toURI();
			IFile[] farr = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(uri);
			return farr[0];
		}
		return null;
	}

	
	public static IFile getLinktoFile(IContainer base, String name, IPath path) {
		IFile file = base.getFile(base.getFullPath().append(name));
		
		try {
			
			file.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
			return file;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	public static File getFileFromInstall(String location) {
		IPath path = getPathFromInstall(location);
		if (path == null) return null;
		return path.toFile();
	}

	public static URL getURLFromInstall(Bundle bundle, String location) {
		URL dpath = bundle.getEntry(location);	
		
		if (dpath == null) return null;
		
		URL url;
		try {
			url = FileLocator.toFileURL(dpath);
			return url;
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	public static URL getURLFromInstall(String location) {
		return getURLFromInstall(CoreActivator.getDefault().getBundle(), location);
	}
	
	public static IPath getPathFromInstall(String location) {
		try {
			URL dpath = CoreActivator.getDefault().getBundle().getEntry(location);	
			
			if (dpath == null) return null;
			
			URL url = FileLocator.toFileURL(dpath);
			IPath path = new Path(url.getPath());
			return path;
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	public static void createFileLink(IPath path,IFile destination) { 
		try {
			destination.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public static void createFileLink(URI path,IFile destination) { 
		try {
			destination.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public static void createLink(IPath path,IFolder destination) {

		try {
			destination.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public static void createLink(URI path,IFolder destination) {

		try {
			destination.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	
	public static void createFileLinktoInstall(String location,IFile destination) {
		try {
			IPath path = getPathFromInstall(location);
			if (path == null) return;
			destination.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public static void createLinktoInstall(String location,IFolder destination) {
		try {
			IPath path = getPathFromInstall(location);
			if (path == null) return;
			destination.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public static void copyLocalResource(String location, IResource destination) {
		IFileStore source = EFS.getLocalFileSystem().getStore(new Path(location));
		IFileStore dest = EFS.getLocalFileSystem().getStore(destination.getLocation());
		try {
			source.copy(dest, EFS.NONE, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	
	
	/** This method copies the folder in the location string from the install directory to the 
	 * destination directory
	 * @param location
	 * @param resName
	 * @param destination
	 */
	public static IFolder copyInstallResource(String location, String resName, IContainer destination) {
		IPath path = getPathFromInstall(location);
		if (path == null) return null;
		
		IFolder temp = destination.getFolder(new Path("temp"));
		IFolder nfolder = destination.getFolder(new Path(resName));
		try {
			temp.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
			nfolder.delete(true, null);
			temp.copy(nfolder.getFullPath(), true, null);
			temp.delete(true, null);
			return nfolder;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	
	}
	/*
	public static IFolder copyInstallResource(String location, String resName, IFolder destination) {
		IPath path = getPathFromInstall(location);
		if (path == null) return null;
		IFolder temp = destination.getFolder("temp");
		IFolder nfolder = destination.getFolder(resName);
		try {
			temp.createLink(path, IFolder.ALLOW_MISSING_LOCAL, null);
			nfolder.delete(true, null);
			temp.copy(nfolder.getFullPath(), true, null);
			temp.delete(true, null);
			return nfolder;
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
*/
	public static IFolder copyInstallResourceFile(String location, IFile outFile)  {
		File ufile = getFileFromInstall(location);
		
		try {
			FileInputStream fin = new FileInputStream(ufile);
			outFile.create(fin, true, null);
			fin.close();
			
		} catch (FileNotFoundException e1) {
			HardwareLog.logError(e1);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		
		return null;
	
	}
	
	public static IFolder copyInstallResourceFile(String location, String resName, IContainer destination)  {
		File ufile = getFileFromInstall(location);
		
		//IFolder nfolder = destination.getFolder(resName);
		IPath path = new Path(resName);
		IFile outFile = destination.getFile(path);
		try {
			FileInputStream fin = new FileInputStream(ufile);
			outFile.create(fin, true, null);
			fin.close();
			
		} catch (FileNotFoundException e1) {
			HardwareLog.logError(e1);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		
		return null;
	
	}

	
	
	
}
