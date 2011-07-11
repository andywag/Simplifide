/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.navigator.wizards.WizardShortcutAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.resources.IconManager;
import com.simplifide.core.ui.wizard.WizardIds;

public abstract class ProjectHolderElement extends TopElement<EclipseSuite>  {

	
	
	public ProjectHolderElement(ReferenceItem holder) {
		super(holder);
	}

	
	public Image getImageDescriptor() {
		return IconManager.getImage(IconManager.REF_CORE_PROJECT_FOLDER);
	}
	

	public static class Library extends ProjectHolderElement {
		
		public Library(ReferenceItem item) {
			super(item);
		}
		
		public String getName() {
			return "Libraries";
		}
		
		protected List<ReferenceItem> getSelfList() {
			ArrayList refList = (ArrayList) this.getItem().getObject().getLibraryReference().getObject().getGenericSelfList();
			return refList;
		}
		
		private void createMenu(IMenuManager manager) {
			IMenuManager newMenu = new MenuManager("New");
			WizardShortcutAction proj = this.getAction(WizardIds.LIBRARY);
			newMenu.add(proj);
			manager.appendToGroup(ICommonMenuConstants.GROUP_NEW, newMenu);
		}
		
		
		public void addActions(IMenuManager manager, IStructuredSelection selection) {
			this.createMenu(manager);
			IFolder libFolder = this.getItem().getObject().getMainLibraryFolder();
			String[] ustr = new String[] {};
			this.createWizardMenu(manager, new StructuredSelection(libFolder),ustr);
		}
		
		
		
	}
	
	public static class Project extends ProjectHolderElement {
		
		public Project(ReferenceItem item) {
			super(item);
		}
		
		public String getName() {
			return "Projects";
		}
		
		private void createMenu(IMenuManager manager) {
			IMenuManager newMenu = new MenuManager("New");
			WizardShortcutAction proj = this.getAction(WizardIds.PROJECT);
			newMenu.add(proj);
			manager.appendToGroup(ICommonMenuConstants.GROUP_NEW, newMenu);
		}
		
		public void addActions(IMenuManager manager, IStructuredSelection selection) {
			this.createMenu(manager);
			IContainer libFolder = this.getItem().getObject().getMainProjectFolder();
			String[] ustr = new String[] {};

			this.createWizardMenu(manager, new StructuredSelection(libFolder), ustr);
			
			
		}
	}

	

	
	
	

	

	
}
