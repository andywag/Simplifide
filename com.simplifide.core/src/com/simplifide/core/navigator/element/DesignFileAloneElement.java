/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.source.design.DesignFileAlone;

public class DesignFileAloneElement extends TopElement{

	public DesignFileAloneElement(ReferenceItem ref) {
		super(ref);
	}
	
	public String getName() {
		ReferenceItem ref = this.getItem();
		DesignFileAlone alone = (DesignFileAlone) ref.getObject();
		return alone.getFileStore().getName();
	}
	
	@Override
	public void addActions(IMenuManager manager, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		
	}
	
	public static class Verilog extends DesignFileAloneElement {
		public Verilog(ReferenceItem ref) {
			super(ref);
		}
	}
	
	public static class Vhdl extends DesignFileAloneElement {
		public Vhdl(ReferenceItem ref) {
			super(ref);
		}
	}

	
	
}
