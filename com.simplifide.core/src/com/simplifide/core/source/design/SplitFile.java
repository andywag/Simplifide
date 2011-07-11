/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.source.design;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.split.SourceFileSplitter;
import com.simplifide.base.split.VerilogFileSplitter;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.source.EclipseSourceFolder;

public class SplitFile extends SourceFile{

	private IFolder folder;
	private List<DesignFile> designList = new ArrayList<DesignFile>();
	
	public SplitFile() {
		super(null);
	}
	/*
	public SplitFile(IFile file, IFolder folder) {
		super(file);
		this.setFolder(folder);
		
	}*/
	
	public int getSearchType() {
		return ReferenceUtilities.REF_SPLIT_FILE;
	}

	public void combineFile() {
		
		URI uri = this.getResource().getLocationURI();
		File nfile = new File(uri);
		
		SourceFileSplitter split = new VerilogFileSplitter(nfile);
		split.combine();
		
	}
	
	public void resolveDesignItems(EclipseBaseProject basic, EclipseSourceFolder folder) {
		
		IResource[] resArr;
		try {
			resArr = this.getFolder().members();
			for (IResource res : resArr) {
				SourceObject obj = SourceObject.resolveObject(res.getRawLocationURI());
				if (obj instanceof DesignFile) {
					DesignFile dfile = (DesignFile) obj;
					getDesignList().add(dfile);
					// Commented because this was a stupid way of dealing with things
					//obj.resolveDesignItems(basic, folder);
					dfile.setParent(this);
				}
			}
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		
		
	}

	public void setFolder(IFolder folder) {
		this.folder = folder;
	}

	public IFolder getFolder() {
		return folder;
	}

	public void setDesignList(List<DesignFile> designList) {
		this.designList = designList;
	}

	public List<DesignFile> getDesignList() {
		return designList;
	}

	
	

}
