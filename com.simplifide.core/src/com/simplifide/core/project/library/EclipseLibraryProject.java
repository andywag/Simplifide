/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.library;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.generator.SuiteStructureGenerator;
import com.simplifide.core.project.library.storage.EclipseLibraryStore;
import com.simplifide.core.project.structure.LibraryStructureHolder;
import com.simplifide.core.project.structure.ProjectStructureHolderTop;
import com.simplifide.core.project.structure.RootStructureLoader;


public class EclipseLibraryProject extends EclipseBaseProject{

	
	
	private static final long serialVersionUID = 6356059397846935539L;
	private EclipseLibraryStore storage;
	
	 
	public EclipseLibraryProject(String name, IFolder folder, ReferenceItem<EclipseSuite> suiteRef) {
		super(name,folder,suiteRef);	
	}
	
	@Override
	public ProjectStructureHolderTop loadProjectStructure() {
		return RootStructureLoader.loadLibraryStructure(this);
	}

	
	public int getSearchType() {
		return ReferenceUtilities.REF_PROJECT_LIBRARY;
	}
	

	private static IFolder createNewLink(IFolder folder) {
		String nam = folder.getName();
		IFolder parent = (IFolder) folder.getParent();
		try {
			folder.delete(true, null);
			if (nam.equalsIgnoreCase("ieee")) 
				SuiteStructureGenerator.getDefault().createIeeeLink(parent);
			else if (nam.equalsIgnoreCase("ieee_proposed")) 
				SuiteStructureGenerator.getDefault().createIeeeProposedLink(parent);
			else if (nam.equalsIgnoreCase("std")) 
				SuiteStructureGenerator.getDefault().createStdLink(parent);
			return parent.getFolder(nam);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return folder;
	}
	
	private static IFolder updateLink(IFolder folder) {
		if (folder.getName().equalsIgnoreCase("ieee") |
			folder.getName().equalsIgnoreCase("ieee_proposed") |
			folder.getName().equalsIgnoreCase("std")) {

			try {
			if (folder.isSynchronized(IResource.DEPTH_ONE)) {
				folder.refreshLocal(IResource.DEPTH_ONE, null);
			}
			if (folder.isLinked()) {
				if (!folder.isAccessible()) {
					return createNewLink(folder);					
				}
				else {
					IResource[] members;
					
						members = folder.members();
						if (members.length == 0) {
							return createNewLink(folder);
						}
					}
					
				}
			}
			 catch (CoreException e) {
					HardwareLog.logError(e);
				}
		}
		return folder;
	}
	
	public static EclipseLibraryProject createProject(String name, IFolder folder1, ReferenceItem<EclipseSuite> suiteRef) {

		IFolder folder = updateLink(folder1);
		
		IFile ifile = folder.getFile(LibraryStructureHolder.FILE_CONFIG);
		if (ifile.exists()) {
			try {
				EclipseLibraryStoreProject lib = new EclipseLibraryStoreProject(name,folder,suiteRef);
				return lib;
			}
			catch (Exception e) {
				HardwareLog.logInfo("Couldn't Load Library from Storage");
			}
		}
		
		return new EclipseLibraryProject(name,folder,suiteRef);
		
	}
	
	
	
	public void build(int kind, IProgressMonitor monitor)  {
		// Check to see if library build configurations are enabled
		super.build(kind, monitor);
		/*if (!CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.BUILD_LIBRARY_STORE)) {
			super.build(kind, monitor);
			return;
		}
		// Should only be run in this mode, but ...
		if (kind == BuildInterface.BUILD_FILE_CONTEXT) {
			try {
				this.storage = EclipseLibraryStore.readObject(this);
				if (storage != null) {
					this.storage.resolveLibraryList(this);
					this.storage.resolveDependenceList();
				}
				else {
					super.build(kind, monitor);
					this.createStorage();
				}
			}
			catch (Exception e) {
				HardwareLog.logError("Building Library (Storage Problem)", e);
				super.build(kind,monitor);
				this.createStorage();
				
			}
		}*/
		
		//super.build(kind,args,monitor);
		
	}
	
	public void deleteObject() {
		this.storage = null;
		super.deleteObject();
	}
	
	public void createStorage() {
		this.storage = new EclipseLibraryStore();
		this.storage.createStorage(this);
	}



	
	
	
	
	
	
	
	
	
	
	
	

}
