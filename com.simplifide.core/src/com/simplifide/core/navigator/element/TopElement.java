/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.element;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.internal.navigator.wizards.WizardShortcutAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.WizardActionGroup;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.navigator.NavigatorContentProvider;
import com.simplifide.core.navigator.NavigatorInterface;
import com.simplifide.core.resources.IconManager;

public abstract class TopElement<T extends ModuleObject> implements NavigatorInterface, ChangeListener{

	private ReferenceItem<T> item;
	private NavigatorContentProvider treeProvider;
	
	public TopElement(ReferenceItem<T> item) {
		this.setItem(item);
		item.addListener(this);
	}
	
	public String toString() {
		return this.getName();
	}
	
	protected MenuManager createWizardActionMenu(IMenuManager manager, IStructuredSelection selection) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		WizardActionGroup group = new WizardActionGroup(window,workbench.getNewWizardRegistry(),WizardActionGroup.TYPE_NEW);
		
		IWizardRegistry reg = workbench.getNewWizardRegistry();
		IWizardDescriptor desc = reg.findWizard("com.simplifide.core.ui.wizard.SuiteWizard");
		//WizardShortcutAction act = new WizardShortcutAction(aWindow, aDescriptor)
		
		
		MenuManager manager1 = new MenuManager("New");
		group.setContext(new ActionContext(selection));
		group.fillContextMenu(manager1);
		return manager1;
		
	}
	
	protected WizardShortcutAction getAction(String id) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		WizardActionGroup group = new WizardActionGroup(window,workbench.getNewWizardRegistry(),WizardActionGroup.TYPE_NEW);
		
		IWizardRegistry reg = workbench.getNewWizardRegistry();
		IWizardDescriptor desc = reg.findWizard(id);
		WizardShortcutAction act = new WizardShortcutAction(window, desc);
		return act;
	}
	
	protected void createWizardMenu(IMenuManager manager, IStructuredSelection selection, String[] valid) {
		MenuManager manager1 = this.createWizardActionMenu(manager, selection);
		IContributionItem[] items = manager1.getItems();
		ArrayList nlist = new ArrayList();
		for (IContributionItem item : items) {
			boolean keep = false;
			if (item instanceof ActionContributionItem) {
				ActionContributionItem act = (ActionContributionItem) item;
				String id = act.getAction().getText();
				nlist.add(id);
				for (String uvalid : valid) {
					if (id.equalsIgnoreCase(uvalid)) keep = true;
				}
			}
			if (!keep) manager1.remove(item);
		}
		manager.appendToGroup(ICommonMenuConstants.GROUP_NEW, manager1);
	}
	
	
	public abstract void addActions(IMenuManager manager, IStructuredSelection selection);

	protected List<ReferenceItem> getSelfList() {
		return this.getItem().getObject().getGenericSelfList();
	}
	
	public Object[] getChildren() {
		List outList = new ArrayList();
		List<ReferenceItem> projList = this.getSelfList();
		for (ReferenceItem proj : projList) {
			TopElement ele = ElementFactory.createElement(proj);
			outList.add(ele);
		}
		return outList.toArray();
	}
	
	public boolean hasChildren() {	
		int size = this.getSelfList().size();
		if (size > 0) return true;
		return false;
	}
	
	public Image getImageDescriptor() {
		if (item != null)
			return IconManager.getImage(item.getSearchType());
		return null;
	}
	
	public String getName() {
		if (item != null) {
			return item.getname();
		}
		return null;
	}
	
	public void stateChanged(ChangeEvent event) {
		
	}
	
	public void setItem(ReferenceItem<T> item) {
		this.item = item;
	}

	public ReferenceItem<T> getItem() {
		return item;
	}

	public void setTreeProvider(NavigatorContentProvider treeProvider) {
		this.treeProvider = treeProvider;
	}

	public NavigatorContentProvider getTreeProvider() {
		return treeProvider;
	}
	
	
}
