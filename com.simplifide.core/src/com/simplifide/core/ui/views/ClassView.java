package com.simplifide.core.ui.views;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.hierarchy.GenerateConnectorHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.project.hier.ClassHierarchyManager;
import com.simplifide.core.source.LocationOperations;

public class ClassView extends BasicTreeView {

	public static String ID = "com.simplifide.core.ui.views.ClassView";
	
	private HierMouseListener mouseListener;
	
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		this.hookMouse();
	}

	private void hookMouse() {
		this.mouseListener = new HierMouseListener();
		this.getTreeView().getControl().addMouseListener(this.mouseListener);
	}
	
	protected ITreeContentProvider getContentProv() {
		return new ClassContentProvider();
	}
	
	public void dispose() {
		//this.getTreeView().getControl().removeMouseListener(this.mouseListener);
		super.dispose();
	}

	public void updateDisplay() {
		if (HardwareChecker.isClassHierarchyEnabled()) {
			this.changeInput(ClassHierarchyManager.getInstance().getContents());
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
					//SourceLocationHandler.getInstance().goToPosition(loc);
					LocationOperations.goToPosition(loc);
				}
				else if (uobj instanceof ConnectorModule) {
					ConnectorModule connect = (ConnectorModule) uobj;
					ReferenceLocation loc = connect.getEntityLocation();
					LocationOperations.goToPosition(loc);
				}
				else {
					ReferenceItem ref = (ReferenceItem) obj;
					ReferenceLocation loc = ref.getLocation();
					if (loc != null) LocationOperations.goToPosition(loc);
				}
				 
			}
		}
		public void mouseDoubleClick(MouseEvent e) {
			this.gotoSelection();
			
		}
	}

}
