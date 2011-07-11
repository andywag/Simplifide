package com.simplifide.core.project.source;

import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.source.design.DesignFile;

public abstract class ProjectSourceFolderTop extends ModuleObjectNew{

	private EclipseBaseProject project;
	private ReferenceItem<? extends CoreProjectSuite> suiteReference; 
	
	public ProjectSourceFolderTop(EclipseBaseProject project,
			ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		this.project = project;
		this.suiteReference = suiteRef;	
	}
	
	public void deleteObject() {
		this.project = null;
		this.suiteReference = null;
	}

	/** Load up the files at Project Creation */
	public abstract void loadFiles();
	/** Return a list of source files contained in the design */
	public abstract UniqueList<DesignFile> getSourceList();
	/** Add the Design File */
	//public abstract void addDesignFile(DesignFile dfile);
	/** Remove a Design File from the Project */
	//public abstract void removeDesignFile(DesignFile designFile);
	/** Builds the Source Files in the Design */
	public abstract void build(int kind, IProgressMonitor monitor);
	/** Converts the context list to actual design files */
	public abstract void convertContextList(ReferenceItem<? extends CoreProjectSuite> suiteRef);
	/** Creates a compile list for the design */
	public abstract UniqueList<DesignFile> createCompileList();
	

	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#setSuiteReference(com.simplifide.base.core.reference.ReferenceItem)
	 */
	public void setSuiteReference(ReferenceItem<? extends CoreProjectSuite> suiteReference) {
		this.suiteReference = suiteReference;
	}

	/* (non-Javadoc)
	 * @see com.simplifide.core.project.SourceFolderInterface#getSuiteReference()
	 */
	public ReferenceItem<? extends CoreProjectSuite> getSuiteReference() {
		return suiteReference;
	}

	
	protected void setProject(EclipseBaseProject project) {
		this.project = project;
	}

	protected EclipseBaseProject getProject() {
		return project;
	}
	
	
	
}
