/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;

public class ElementFactory {

	public static TopElement createElement(ReferenceItem ref) {
		switch (ref.getType()) {
			
		//case ReferenceUtilities.REF_PROJECT_SUITE : return new SuiteElement(ref);
		case ReferenceUtilities.REF_SPLIT_FILE : return new SplitElement(ref);
		case ReferenceUtilities.REF_VERILOG_FILE : return new DesignElement.Verilog(ref);
		case ReferenceUtilities.REF_VHDL_FILE : return new DesignElement.Vhdl(ref);
		
		case ReferenceUtilities.REF_PROJECT_BASIC : return new BasicProjectElement.Project(ref);
		case ReferenceUtilities.REF_PROJECT_LIBRARY : return new BasicProjectElement.Library(ref);
		default : return new BaseElement(ref);
		}
	}
	
}
