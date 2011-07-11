package com.simplifide.core.project;

import org.eclipse.core.resources.IFolder;

import com.simplifide.base.core.reference.ReferenceItem;

/** Project which only contains a single directory for design files */
public class EclipseSingleProject extends EclipseProject{

	public EclipseSingleProject(String name, IFolder folder,
			ReferenceItem<EclipseSuite> suiteRef) {
		super(name, folder, suiteRef);
	}
	
	public IFolder getDesignIFolder() {
		return this.getBaseFolder();
	}
	
	public IFolder getBasicDesignFolder() {
		return this.getBaseFolder();
	}

}
