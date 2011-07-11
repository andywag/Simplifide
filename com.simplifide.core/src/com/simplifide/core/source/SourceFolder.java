/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.source;


import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.source.EclipseSourceFolder;

public class SourceFolder extends SourceObject<SourceObject>{

	
	private static final long serialVersionUID = 1L;

	public SourceFolder(URI folder) {
		super(folder);
	}
	
	

	public IFolder getFolder() {
		return (IFolder) this.getResource();
	}
	
	
	
	public void resolveDesignItems(EclipseBaseProject basic, EclipseSourceFolder folder) {
		IResource res = this.getResource();
		
		if (res != null && res.exists()) {
			if (res instanceof IFolder) {
				IFolder fold = (IFolder) res;
				try {
					IResource[] files = fold.members();
					for (IResource file : files) {
						if (!file.getName().startsWith(".") && !file.getName().startsWith("~")) {
							SourceObject object = SourceObject.resolveObject(file.getLocationURI());
							if (object != null && !object.isIgnoredFile()) {
								object.resolveDesignItems(basic, folder);	
							}
						}
					}
				} catch (CoreException e) {
					HardwareLog.logError(e);
				}
				return;
			}
		}
		
		URI uri = this.getUri();
		if (uri == null) return;
		File ufile = new File(uri);
		boolean exists = ufile.exists();
		if (ufile != null && 
			ufile.exists() && 
			ufile.isDirectory()) {
			File[] files = ufile.listFiles();
			for (int i =0;i<files.length;i++) {
				if (!files[i].getName().startsWith(".") && !files[i].getName().startsWith("~")) {
					SourceObject object = SourceObject.resolveObject(files[i].toURI());
					if (object != null && !object.isIgnoredFile()) {
						object.resolveDesignItems(basic, folder);	
					}
				}
			}
		}
	}
	
	
	
}
