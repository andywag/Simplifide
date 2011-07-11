/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.builder;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;


public class BuilderChangeState implements IResourceDeltaVisitor{

	private static int STATE_NONE = 0;
	private static int STATE_ADD = 1;
	private static int STATE_REMOVE = 2;
	private static int STATE_CHANGE = 3;
	private static int STATE_RENAME = 4;
	
	
	private static int PROJECT_OP_NONE = 0;
	private static int PROJECT_OP_PROJ = 1;
	private static int PROJECT_OP_LIB = 2;
	
	private int state = STATE_NONE;
	
	private EclipseSuite suite;
	private EclipseBaseProject base;
	
	
	private List<Change> changeList = new ArrayList<Change>();
	private UniqueList<DesignFile> depList = new UniqueList<DesignFile>();
	
	private boolean projectChange = false;
	
	public BuilderChangeState(EclipseSuite suite, HardwareBuilder builder) {
		this.suite = suite;
	}
	

	/** Resolves the addition of a file to this project
	 * 
	 * 1. Compile the added files
	 * 2. Recompute the compile list
	 * 3. Compile the dependencies
	 * 
	 */
	private void resolveAdd() {
		if (changeList.isEmpty()) {
			return;
		}
		for (int i=0;i<changeList.size();i++) {
			Change change = changeList.get(i);
			change.dfile.resolveDesignItems(base, null);
			change.dfile.getBuilder().build(BuildInterface.BUILD_FILE_CONTEXT);
		}
		for (int i=0;i<changeList.size();i++) {
			Change change = changeList.get(i);
			change.dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
		}
		this.suite.recreateCompileList();
		this.createDependentList();
		this.compileDependencies();
		/*if (changeList.isEmpty()) {
			return;
		}
		if (changeList.size() == 1) {
			Change change = changeList.get(0);
			change.dfile.resolveDesignItems(base, null);
			change.dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
		}
		else {
			for (int i=0;i<changeList.size();i++) {
				Change change = changeList.get(i);
				change.dfile.resolveDesignItems(base, null);
				change.dfile.getBuilder().build(BuildInterface.BUILD_FILE_CONTEXT);
			}
			List<DesignFile> clist = Change.getDesignFileList(changeList);
			//CompileDependency cdep = SourceFolder.createCompileGraph(clist, base.getSuiteLocation(), suite.createReferenceItem());
			//for (ReferenceItem<? extends DesignFile> dfile : cdep.compileList.getGenericSelfList()) {
			//	dfile.getObject().getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
			//}
			
			for (int i=0;i<changeList.size();i++) {
				Change change = changeList.get(i);
				change.dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
			}
			for (int i=0;i<changeList.size();i++) {
				Change change = changeList.get(i);
				change.dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
			}
		}*/
	}
	
	private void createDependentList() {
		
		for (Change change : this.changeList) {
			UniqueList<DesignFile> flist = change.dfile.getDependentList();
			depList.addAll(flist);
		}
	}
	
	
	/** Method which compiles the files which are dependent on the changed or removed files. 
	 *  A new Compile tree probably needs to be created before each of these operation
	 */
	private void compileDependencies() {
		IWorkspaceRunnable myRunnable = 
			new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					for (ReferenceItem<? extends DesignFile> dfileRef : depList.getGenericSelfList()) {
						DesignFile dfile = dfileRef.getObject();
						if (dfile != null && dfile.getBuilder() != null) {
							dfile.getBuilder(). build(BuildInterface.BUILD_FILE_CLOSED);					
						}
					}
					
				}
		};
		try {
			ResourcesPlugin.getWorkspace().run(myRunnable, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	
	/**
	 * Method to remove files from the project. This is probably the easiest. 
	 * 
	 * 1. Calculate Dependencies
	 * 2. Delete the Objects
	 * 3. Compile the Dependencies
	 * 4. Needs to update the compile lists as well (****TBD****)
	 * 
	 */
	private void resolveRemove() {
		this.createDependentList();
		for (Change change : this.changeList) {
			change.dfile.deleteObject();
		}
		this.compileDependencies();
		this.suite.recreateCompileList();
	}
	
	/**
	 * Method which handles file changes 
	 * 
	 * 1. Recreate the compile list so the orders are corrected
	 * 2. Create the Dependent List associated with the change
	 * 3. Finalize the references (Delete the files internals of the files which are to be recompiled)
	 *    Still not sure why this needs to occur (Should be part of build)
	 * 4. Re-compile dependencies
	 * 5. Handle Save (Updates the different displays)
	 * 
	 * @todo There may be an issue with ordering for multiple changes but 
	 */
	private void resolveChange() {
		this.suite.recreateCompileList(); // Recreate the compile list before the deps are calculated
		this.createDependentList();
		for (Change change : this.changeList) {
			change.dfile.getModuleRef().getObject().finalizeReference();
		}
		
		this.compileDependencies();
		for (Change change : this.changeList) {
			change.dfile.handleSave();
		}
	}
	
	private void resolveRename() {
		this.createDependentList();
		// Handle Removes First
		for (Change change : this.changeList) {
			if (change.type == STATE_REMOVE) {
				change.dfile.deleteObject();
			}
		}
		for (Change change : this.changeList) {
			if (change.type == STATE_ADD) {
				change.dfile.resolveDesignItems(base, null);
				change.dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
			}
		}
			
		this.compileDependencies();
	}
	
	
	/** Add the changes to the list, I am not sure what the differences
	 *  between resolveObject and resolveObjectWithoutHistry */
	
	private void addChangeList(IResource resource, boolean history) {
		SourceObject obj;
		if (history) 
			obj = SourceObject.resolveObject(resource.getLocationURI());
		else 
			obj = SourceObject.resolveObject(resource.getLocationURI());
		
		if (obj instanceof DesignFile) {
			Change ch = new Change((DesignFile)obj,STATE_ADD);
			this.changeList.add(ch);
		}
	}
	
	/** Checks the addition to see whether it fits in either the project
	 *  folder or library folder. This doesn't handle subprojects
	 */
	private int checkProjectOperation(IResource resource) {	
		String resPath = resource.getParent().getFullPath().toString();
		List<IFolder> folders = this.suite.getProjectFolders();
		for (IFolder folder : folders) {
			if (folder != null) {
				String projPath = folder.getFullPath().toString();
				if (projPath.equalsIgnoreCase(resPath)) return PROJECT_OP_PROJ;
			}
		}
		
		folders = this.suite.getLibraryFolders();
		for (IFolder folder : folders) {
			if (folder != null) {
				String libPath  = folder.getFullPath().toString();
				if (libPath.equalsIgnoreCase(resPath)) return PROJECT_OP_LIB;
			}
		}
		return PROJECT_OP_NONE;
	}
	
	/** Add Project To Suite */
	private void addProject(IResource resource,int op) {
		this.projectChange = true;
		if (resource instanceof IFolder) {
			if (op == PROJECT_OP_PROJ)
				this.suite.addProject((IFolder)resource);
			else
				this.suite.addLibrary((IFolder)resource);
			
		}
	}
	/** Remove the project from the suite */
	private void removeProject(IResource resource,int op) {
		this.projectChange = true;
		if (resource instanceof IFolder) {
			if (op == PROJECT_OP_PROJ)
				this.suite.removeProject((IFolder)resource);
			else
				this.suite.removeLibrary((IFolder)resource);
		}
	}
	
	/** Routine called on the resource visitor when the file is added*/
	private boolean handleAdd(IResource resource) {
		
		int op = this.checkProjectOperation(resource);
		if (op != PROJECT_OP_NONE) { // Check for project additions
			this.addProject(resource,op);
			return false;
		}
		
		if (this.base != null) {
			if (this.state == STATE_REMOVE) {
				this.state = STATE_RENAME;
			}
			else {
				this.state = STATE_ADD;
			}
			this.addChangeList(resource,false);
		}
		return true;
	}
	
	/** Routine called on the resource visitor when the file is removed*/
	private boolean handleRemove(IResource resource) {
		
		int op = this.checkProjectOperation(resource);
		if (op != PROJECT_OP_NONE) { // Check for project removals
			this.removeProject(resource,op);
			return false;
		}
		
		if (this.base != null) {
			if (this.state == STATE_ADD) {
				this.state = STATE_RENAME;
			}
			else {
				this.state = STATE_REMOVE;
			}
			//if (SourceObject.isDesignFile(resource)) {
				DesignFile dfile = LocationOperations.getDeletedDesignFile(resource.getLocationURI());
				if (dfile != null) {
					Change ch = new Change(dfile,STATE_REMOVE);
					this.changeList.add(ch);
				}
				
			//}
		}
		return true;
	}
	/** Routine called on the resource visitor when the file is changed*/

	private boolean handleChange(IResource resource) {
		
		if (base == null) {
			for (ReferenceItem<? extends EclipseBaseProject> basic : suite.getGenericSelfList()) {
				
				EclipseBaseProject project = basic.getObject();
				if (project != null) {
					String projPath = project.getBaseFolder().getFullPath().toString();
					String resPath = resource.getFullPath().toString();
					if (projPath.equalsIgnoreCase(resPath)) {
						this.base = project;
						break;
					}
				}
				
			}
		}
		else {
			this.state = STATE_CHANGE;
			this.addChangeList(resource,true);
		}
		return true;
		
	}
	
	/** Method which is run after the changes are calculated */
	public void resolveChangeList() {
		if (this.state == STATE_ADD) {
			this.resolveAdd();
			if (ActiveSuiteHolder.getDefault().getTreeContent() != null)
				ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
		}
		else if (this.state == STATE_REMOVE) {
			this.resolveRemove();
			if (ActiveSuiteHolder.getDefault().getTreeContent() != null)
				ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
		}
		else if (this.state == STATE_CHANGE) {
			this.resolveChange();
		}
		else if (this.state == STATE_RENAME) {
			this.resolveRename();
			if (ActiveSuiteHolder.getDefault().getTreeContent() != null)
				ActiveSuiteHolder.getDefault().getTreeContent().fireChange();
		}
			
	}
	
	/** Main method which is called when changes are detected */
	public boolean visit(IResourceDelta delta) throws CoreException {
	
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		if (suite == null) return false;
		IResource res = delta.getResource();
		if (res instanceof IProject) { // Don't do anything if change isn't in active suite
			if (!suite.getProject().equals(delta.getResource())) return false;
		}
		
		if (delta.getKind() == IResourceDelta.CHANGED) {
			return this.handleChange(delta.getResource());
		}
		if (delta.getKind() == IResourceDelta.REMOVED) {
			return this.handleRemove(delta.getResource());
		}
		if (delta.getKind() == IResourceDelta.ADDED) {
			return this.handleAdd(delta.getResource());
		}
		
		return true;
	}
	
	public boolean validChange() {
		if (this.changeList.size() > 0) return true;
		return false;
	}
	
	public boolean projectChange() {
		return this.projectChange;
	}
	
	public DesignFile getChangedFile() {
		Change change = this.changeList.get(0);
		if (change != null) {
			return change.dfile;
		}
		return null;
	}

	
	/** Class which identifies the type of change which occured */
	private static class Change {
		private DesignFile dfile;
		private int type;
		
		public Change(DesignFile dfile, int type) {
			this.dfile = dfile;
			this.type = type;
		}
		
		public static List<DesignFile> getDesignFileList(List<Change> changeList) {
			ArrayList<DesignFile> designList = new ArrayList<DesignFile>();
			for (Change uchange : changeList) {
				designList.add(uchange.dfile);
			}
			return designList;
		}
		
		public static List<DesignFile> getRemoveFileList(List<Change> changeList) {
			ArrayList<DesignFile> designList = new ArrayList<DesignFile>();
			for (Change uchange : changeList) {
				if (uchange.type == STATE_REMOVE)
					designList.add(uchange.dfile);
			}
			return designList;
		}
		
	}

}
