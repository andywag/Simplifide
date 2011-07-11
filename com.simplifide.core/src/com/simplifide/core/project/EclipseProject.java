/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project;

import org.eclipse.core.resources.IFolder;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.structure.ProjectStructureHolderTop;
import com.simplifide.core.project.structure.RootStructureLoader;

public class EclipseProject extends EclipseBaseProject{
	
	
	private static final long serialVersionUID = 1L;
	
	public EclipseProject(String name, IFolder folder, ReferenceItem<EclipseSuite> suiteRef) {
		super(name,folder,suiteRef);
		init();
	}
	
	private void init() {}
	
	
	/** Returns the default project structure for this type of project */
	public ProjectStructureHolderTop loadProjectStructure() {
		return RootStructureLoader.loadProjectStructure(this);
	}

	
	
	
	
		
	
}
