/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.hier;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.hierarchy.HierarchyList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.ui.views.ClassView;

public class ClassHierarchyManager {
	
	private static ClassHierarchyManager instance;
	
	private  ReferenceItem<HierarchyList> hierList = new NoSortList().createReferenceItem();
	
	public ClassHierarchyManager() {}
	
	public static ClassHierarchyManager getInstance() {
		if (instance == null) instance = new ClassHierarchyManager();
		return instance;
	}
	
	public void updateDisplay() {
		this.fireChange();
	}
      
	public ReferenceItem getContents() {
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		return suite.getClassListReference();
	
	}
	
	private void fireChange() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null) {
			display.asyncExec(new Runnable() {
				public void run () {
					IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ClassView.ID);
					ClassView itemView = (ClassView) view;
					if (itemView != null) itemView.updateDisplay();
				}
			});
		}
	}
	

	public void setHierList(ReferenceItem<HierarchyList> hierList) {
		this.hierList = hierList;
	}

	public  ReferenceItem<HierarchyList> getHierList() {
		return hierList;
	}
}
