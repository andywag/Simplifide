/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.base.core.reference.ReferenceItem;

public class DesignElement extends TopElement{

	public DesignElement(ReferenceItem ref) {
		super(ref);
	}
	
	@Override
	public void addActions(IMenuManager manager, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	
	public static class Verilog extends DesignElement {
		public Verilog(ReferenceItem ref) {
			super(ref);
		}
	}
	
	public static class Vhdl extends DesignElement {
		public Vhdl(ReferenceItem ref) {
			super(ref);
		}
	}

	
	
}
