/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.source;


import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.SourceFolder;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.util.FileUtility;

public class EclipseSourceFolder extends SourceFolder {

	
	/** This use of a list may not be good as good as just calculating the new source list
	 *  each time the list is required
	 */
	
	private ReferenceItem<? extends CoreProjectSuite> suiteReference;
	
	public EclipseSourceFolder(IFolder folder, ReferenceItem<? extends CoreProjectSuite> suiteReference) {
		super(folder.getLocationURI());
		this.suiteReference = suiteReference;
	}
	
	/*
	private boolean addDesignFileURI(DesignFile dfile) {
		URI folderURI = this.getResource().getLocationURI();
		URI fileURI = dfile.getUri();
		URI relative = folderURI.relativize(fileURI);

		if (!relative.equals(fileURI)) {
			this.sourceList.addObject(dfile.createReferenceItem());
			return true;
		}
		return false;
	}
	
	private void addDesignFilePath(DesignFile dfile) {
		IPath folderRel = this.getResource().getProjectRelativePath();
		IPath fileRel = dfile.getResource().getProjectRelativePath();

		if (folderRel.isPrefixOf(fileRel)) {
			this.sourceList.addObject(dfile.createReferenceItem());
		}
	}
	
	public void addDesignFile(DesignFile dfile) {
		if (!this.addDesignFileURI(dfile)) {
			this.addDesignFilePath(dfile);
		}
		
	}
	
	
	public void deleteObject() {
		super.deleteObject();

		if (this.sourceList != null) {
			this.sourceList.deleteObject();
			this.sourceList = null;
		}
	}

	
	
	public void removeDesignFile(DesignFile designFile) {
		if (this.sourceList != null)
			this.sourceList.removeObject(designFile.createReferenceItem());
	}
	*/
	
	
	public UniqueList<DesignFile> createCompileList() {
		UniqueList<DesignFile> dlist = new UniqueList<DesignFile>();
		for (DesignFile dfile : this.getSourceList().getRealSelfList()) {
			if (!dfile.getCompileInfo().isFinished()) {
				dlist.addAll(dfile.getCompileInfo().createCompileList(this.suiteReference));
			}
		}
		return dlist;
	}
	
	

	private void realBuild(int kind,  IProgressMonitor monitor) {
		
		for (ReferenceItem<? extends DesignFile> sfile : this.getSourceList()
				.getGenericSelfList()) {
			
			if (sfile.getObject() != null) {
			
				sfile.getObject().getBuilder().build(kind, monitor);
				monitor.worked(1);
			}
		}
	}

	public void convertContextList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		for (ReferenceItem<? extends DesignFile> sfile : this.getSourceList().getGenericSelfList()) {
			if (sfile.getObject() != null) {
				sfile.getObject().getCompileInfo().convertContextList(suiteRef);
			}
		}
	}
	
	public void build(int kind, IProgressMonitor monitor) {
		this.realBuild(kind, monitor);
	}

	

	private void decodeFileStore(UniqueList<DesignFile> designs, URI uri) {
		try {
			DesignFile design = null;
			if (SourceObject.isIgnored(uri)) return;
			
			design = LocationOperations.getDesignFile(uri);
			
			if (design != null) {
				designs.addObject(design.createReferenceItem());
			}
			else {
				IFolder folder = FileUtility.convertURItoResourceFolder(uri);
				if (folder != null && folder.exists()) {
					IResource[] children = folder.members();
					for (IResource child : children) {
						this.decodeFileStore(designs, child.getLocationURI());
					}
				}
				else {
					IFileStore store = EFS.getStore(uri);
					IFileStore[] children = store.childStores(EFS.NONE, null);
					for (IFileStore child : children) {
						this.decodeFileStore(designs, child.toURI());
					}
				}
				
			}
			
			
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	public UniqueList<DesignFile> getSourceList() {
		UniqueList<DesignFile> designs = new UniqueList<DesignFile>();
		this.decodeFileStore(designs, this.getUri());
		return designs;
	}
	
	public void updateSourceList(UniqueList<DesignFile> designs) {
		this.decodeFileStore(designs, this.getUri());
	}



}
