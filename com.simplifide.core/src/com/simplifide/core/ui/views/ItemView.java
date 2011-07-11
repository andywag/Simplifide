package com.simplifide.core.ui.views;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.hierarchy.GenerateConnectorHolder;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.project.hier.ProjectContentManager;
import com.simplifide.core.source.LocationOperations;

public class ItemView extends BasicTreeView implements ChangeListener{

	public static String ID = "com.simplifide.core.ui.views.ObjectView";
	
	private HierMouseListener mouseListener;
	
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		//ProjectContentManager.getInstance().addListener(this);
		this.hookMouse();
	}

	private void hookMouse() {
		this.mouseListener = new HierMouseListener();
		this.getTreeView().getControl().addMouseListener(this.mouseListener);
	}
	
	protected ITreeContentProvider getContentProv() {
		return new ObjectContentProvider();
	}
	
	public void dispose() {
		//this.getTreeView().getControl().removeMouseListener(this.mouseListener);
		super.dispose();
	}

	public void updateDisplay() {
		if (HardwareChecker.isObjectViewEnabled()) {
			this.changeInput(ProjectContentManager.getInstance().getContents());
		}
	}
	
	public void stateChanged(ChangeEvent arg0) {
		
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
				else if (uobj instanceof InstanceModule) {
					InstanceModule ins = (InstanceModule) uobj;
					ReferenceItem ref = ins.getEntityReference();
					if (ref.getLocation() != null) {
						LocationOperations.goToPosition(ref.getLocation());
					}
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
