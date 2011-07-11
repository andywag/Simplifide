/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.simplifide.core.navigator.element.TopElement;


public class SuiteActionProvider extends CommonActionProvider{


	
	@Override
	public void init(ICommonActionExtensionSite site) {
		
		super.init(site);
		
	}

	

	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		TreeSelection sel = (TreeSelection) this.getActionSite().getStructuredViewer().getSelection();
		Object el = sel.getFirstElement();
		if (el instanceof TopElement) {
			TopElement top = (TopElement) el;
			top.addActions(menu, sel);
		}		
	}

	
	
}
