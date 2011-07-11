package com.simplifide.core.project.test;

import org.eclipse.core.resources.IFolder;

import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.core.project.EclipseProject;

public class EclipseTestProject extends EclipseProject{

	public EclipseTestProject(IFolder baseFolder, CoreProjectSuite suite) {
		super("test",baseFolder,suite.createReferenceItem());
	}
	
	 /** Find a Reference Item based on the find item and type */
    /*public ReferenceItem findBaseReference(ModuleObjectIndexTop item) {
    	ReferenceItem sitem = super.findBaseReference(item);
    	if (sitem != null) return sitem;
    	
    	return this.getSuiteReference().getObject().findProjectReference(item);
       
    }*/
}
 