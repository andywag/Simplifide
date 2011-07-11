/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.hier;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.hierarchy.HierarchyList;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.ui.views.ItemView;

public class ProjectContentManager {
	
	private static ProjectContentManager instance;
	
	private  ReferenceItem<HierarchyList> hierList = new NoSortList().createReferenceItem();
	
	public ProjectContentManager() {}
	
	public static ProjectContentManager getInstance() {
		if (instance == null) instance = new ProjectContentManager();
		return instance;
	}
	
	public void updateDisplay() {
		this.fireChange();
	}
      
	public ReferenceItem getContents() {
		EclipseSuite suite = ActiveSuiteHolder.getDefault().getSuite();
		ModuleObjectWithList suite1 = new ModuleObjectWithList("Suite");
		List<CoreProjectBasic> projects = suite.getAllRealProject();
		for (CoreProjectBasic project : projects) {			
			suite1.addModuleObject(project, null);
		}
		return suite1.createReferenceItem();
	}
	
	private void fireChange() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display != null) {
			display.asyncExec(new Runnable() {
				public void run () {
					IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ItemView.ID);
					ItemView itemView = (ItemView) view;
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
