/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor.source;


import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.core.CoreActivator;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseProject;
import com.simplifide.core.project.source.EclipseSourceFolder;
import com.simplifide.core.source.SourceLocationHandler;
import com.simplifide.core.source.design.DesignFileBuilder;
import com.simplifide.core.ui.dialog.FileTooLargeDialog;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.util.FileUtility;

public class SourceFile extends SourceObject<SourceObject>{ 
	
	private int sourceLocation;
	private int projectLocation;
	
	private SourceFile parent;
	
	public SourceFile(URI uri) {
		super(uri);
	}
	
	public boolean isAloneFile() {
		return false;
	}

	
	
	public String getFullPathName() {
		if (this.getResource() != null) {
			return this.getResource().getFullPath().toString();
		}
		else return "";
	}
	
	public void deleteObject() {
		SourceLocationHandler.getInstance().removeLocation(this.sourceLocation);
		super.deleteObject();
	}
	
	
	public boolean tooLarge() {
		if (DesignFileBuilder.OVERRIDESIZE) return false;
		IFile ifile = this.getIFile();
		long len = FileUtility.getLength(ifile);
		int mlen = CoreActivator.getDefault().getPreferenceStore().getInt(PreferenceConstants.MAXIMUM_FILE_LENGTH);
		if (len > mlen) return true;
		return false;
	}
	 

	protected void handleTooLarge() {
		// Return true if the file has been checked to continue
		this.isIgnoredFile();
		if (!this.hasCheckedIgnored()) {
			final IWorkbench workbench = PlatformUI.getWorkbench();
			final SourceFile sfile = this;
			workbench.getDisplay().syncExec(new Runnable() {
	        public void run() {
	        	FileTooLargeDialog dialog = new FileTooLargeDialog(workbench,sfile);	
	            dialog.open();
	        }
	            });
	        
		}
		
		
	}
	
	public void resolveDesignItems(EclipseBaseProject basic, EclipseSourceFolder folder) {
		if (basic != null) {
			this.setProjectLocation(basic.getSuiteLocation());
		}
		super.resolveDesignItems(basic, folder);
	}
	
        
	/** Returns the ifile associated with this resource. Only works for workspace elements */
	public IFile getIFile() {
		return (IFile) this.getResource();
	}     
        
        
	public void build(int kind, Map args, IProgressMonitor monitor, EclipseProject project) {
		
	}
	
	public ParserTop getParser() {return null;}

	public void setSourceLocation(int sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public int getSourceLocation() {
		return sourceLocation;
	}

	public void setProjectLocation(int projectIndex) {
		this.projectLocation = projectIndex;
	}

	public int getProjectLocation() {
		return projectLocation;
	}

	public void setParent(SourceFile parent) {
		this.parent = parent;
	}

	public SourceFile getParent() {
		return parent;
	}


	
	
	
	
	
}
