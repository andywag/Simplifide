/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.views;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.hierarchy.GenerateConnectorHolder;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.project.hier.HierarchyManager;
import com.simplifide.core.source.LocationOperations;

public class HierarchyView extends BasicTreeView implements ChangeListener{

	public static String ID = "hardware.ui.views.HierarchyView";
	
	public void HiearchyView() {}
	
	private void createView(Composite parent) {
		if (HardwareChecker.isHierarchyEnabled()) {
			this.getTreeView().setInput(HierarchyManager.getInstance().getHierList());
		}
		
		HierarchyManager.getInstance().addListener(this);
	}
	
	private void hookMouse() {
		this.getTreeView().getControl().addMouseListener(new HierMouseListener());
	}
	
	private void hookPopUpListener() {
		final Tree tree = this.getTreeView().getTree();
		final Menu menu = new Menu (tree.getShell(), SWT.POP_UP);
        tree.setMenu (menu); 
        menu.addListener (SWT.Show, new Listener () {
            public void handleEvent (Event event) {
                    MenuItem [] items = menu.getItems ();
                    for (int i=0; i<items.length; i++) {
                            items [i].dispose ();
                    }
                    Point pt = tree.toControl (tree.getDisplay().getCursorLocation ());
                    TreeItem treeItem = tree.getItem (pt);
                    if (treeItem != null) {
                            MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
                            menuItem.setText ("Architecture");
                            menuItem.addSelectionListener(new PopupListener(false));
                            menuItem = new MenuItem (menu, SWT.PUSH);
                            menuItem.setText ("Entity");
                            menuItem.addSelectionListener(new PopupListener( true));
                    }
            }
    }); 

	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		this.createView(parent);
		this.hookMouse();
		this.hookPopUpListener();
		// TODO Auto-generated method stub	
	}

	public void dispose() {
		HierarchyManager.getInstance().removeListener(this);
		super.dispose();
	}
	
	

	public void stateChanged(ChangeEvent arg0) {
		if (HardwareChecker.isHierarchyEnabled()) {
			this.changeInput(HierarchyManager.getInstance().getHierList());
		}
	}
	
	private class PopupListener implements SelectionListener {
		
		
		private boolean  entity;
		
		public PopupListener( boolean entity) {
			
			this.entity = entity;
		}

		public void widgetDefaultSelected(SelectionEvent e) {}

		public void widgetSelected(SelectionEvent e) {
			IStructuredSelection select = (IStructuredSelection) getTreeView().getSelection();
			Object obj = select.getFirstElement();
			if (obj instanceof ReferenceItem) {
				ModuleObject uobj = ((ReferenceItem)obj).getObject();
				if (uobj instanceof GenerateConnectorHolder) {
					// Do Nothing for Generate
				}
				else if (uobj instanceof ConnectorHolder) {
					ConnectorHolder connect = (ConnectorHolder) uobj;
					ConnectorModule conn2 = connect.getModuleRef().getObject();
					InstanceModule imod = (InstanceModule) conn2.getInstModRef().getObject();
					ReferenceLocation loc = imod.getEntityReference().getLocation();
					if (!entity) loc = imod.getArchitectureReference().getLocation();
					
					
					LocationOperations.goToPosition(loc);
				}
				else if (uobj instanceof ConnectorModule) {
					ConnectorModule connect = (ConnectorModule) uobj;
					InstanceModule imod = (InstanceModule) connect.getInstModRef().getObject();
					ReferenceLocation loc = imod.getEntityReference().getLocation();
					if (!entity) loc = imod.getArchitectureReference().getLocation();
					
					
					LocationOperations.goToPosition(loc);
				}
				 
			}
			
		}
	}
	
	
	private class HierMouseListener extends MouseAdapter{
		
		private void gotoSelection() {
                  
			IStructuredSelection select = (IStructuredSelection) getTreeView().getSelection();
			Object obj = select.getFirstElement();
			if (obj instanceof ReferenceItem) {
				ModuleObject uobj = ((ReferenceItem)obj).getObject();
				if (uobj instanceof GenerateConnectorHolder) {
					GenerateConnectorHolder gen = (GenerateConnectorHolder) uobj;
					//SourceLocationHandler.getInstance().goToPosition(gen.getGenerate().getLocation());
					LocationOperations.goToPosition(gen.getGenerate().getLocation());
				}
				else if (uobj instanceof ConnectorHolder) {
					ConnectorHolder connect = (ConnectorHolder) uobj;
					ConnectorModule conn2 = connect.getModuleRef().getObject();
					ReferenceLocation loc = conn2.getEntityLocation();
					if (loc == null) loc = connect.createReferenceItem().getLocation();
					//SourceLocationHandler.getInstance().goToPosition(loc);
					LocationOperations.goToPosition(loc);
				}
				else if (uobj instanceof ConnectorModule) {
					ConnectorModule connect = (ConnectorModule) uobj;
					ReferenceLocation loc = connect.getEntityLocation();
					if (loc == null) loc = connect.createReferenceItem().getLocation();
					LocationOperations.goToPosition(loc);
				}
				 
			}
		}
		
		public void mouseDoubleClick(MouseEvent e) {
			this.gotoSelection();
			
		}
		public void mouseDown(MouseEvent e) {
			
		}
	}

}
