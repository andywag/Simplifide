package com.simplifide.core.project;

import org.eclipse.core.resources.IFolder;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.library.EclipseLibraryProject;
import com.simplifide.core.project.structure.ProjectStructureHolderTop;
import com.simplifide.core.project.structure.RootStructureLoader;

public class EclipseSubProject extends EclipseLibraryProject{

	private EclipseBaseProject parent;
	
	public EclipseSubProject(String name, IFolder folder,
			ReferenceItem<EclipseSuite> suiteRef) {
		super(name, folder, suiteRef);

	}

	@Override
	public ProjectStructureHolderTop loadProjectStructure() {
		return RootStructureLoader.loadSubProjectStructure(this);
	}

	public void setParent(EclipseBaseProject parent) {
		this.parent = parent;
	}

	public EclipseBaseProject getParent() {
		return parent;
	}

	


	


}
