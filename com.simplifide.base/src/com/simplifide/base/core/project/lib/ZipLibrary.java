package com.simplifide.base.core.project.lib;

import java.io.File;

import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;

/** Compressed project which is stored inside of a zip file and 
 *  java objects to save space and compile time
 *  
 * @author andy
 *
 */
public class ZipLibrary extends CoreProjectBasic{

	public ZipLibrary(File location, ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		super(location.getName());
		this.setSuiteReference(suiteRef);
	}
	
}
