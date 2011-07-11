/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project;

import com.simplifide.base.core.reference.ReferenceItem;

public class EclipseDummyProject extends EclipseProject{

	private static EclipseDummyProject instance;
	
    private EclipseDummyProject( ReferenceItem<EclipseSuite> suiteRef) {
        super("", null, suiteRef);
    }
    
    public static EclipseDummyProject getInstance(ReferenceItem<EclipseSuite> suiteR) {
    	if (instance == null) instance = new EclipseDummyProject(suiteR);
    	return instance;
    }

}
