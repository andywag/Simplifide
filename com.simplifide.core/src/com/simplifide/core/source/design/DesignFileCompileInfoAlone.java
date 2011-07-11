/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.source.design;



import java.util.List;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;

/** 
 * Dummy CompileInfo 
 * @author andy
 *
 */
public class DesignFileCompileInfoAlone extends DesignFileCompileInfo{

	public DesignFileCompileInfoAlone(DesignFile designFile) {
		super(designFile);
	}
		
	public void handleParentDeleted(List<DesignFile> dfileList) {}
	
	/** Method which creates the compile list for the project */
	
	public UniqueList<DesignFile> createCompileList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		UniqueList<DesignFile> designList = new UniqueList<DesignFile>();
		return designList;
	}
	
	

	public void convertParentList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {}



	

	

	
}
