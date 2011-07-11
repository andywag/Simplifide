 package com.simplifide.core.refactor.port.connect;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.HierarchyList;
import com.simplifide.base.core.hierarchy.PathTotalElement;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.core.ActiveSuiteHolder;

public class ConnectPortInputComposite extends Composite{

	
	private Combo instanceCombo;
    private Combo signalCombo;
    
    private ArrayList<PathTreeElement> pathList;
    private List<ModuleObject> portList;
    
	public ConnectPortInputComposite(Composite parent, int style, String prefix) {
		super(parent, style);
		this.create(prefix);
	}
	
	public PathTotalElement getPathTotalElement() {
		//PathTotalElement tot = new PathTotalElement(this.getPath(),this.getPort());
		//return tot;
		return new PathTotalElement(null,null,0);
	}
	
	private PathTreeElement getPath() {
		int sel = instanceCombo.getSelectionIndex();
		if (sel > 0) {
			PathTreeElement el = pathList.get(sel);
			return el;
		}
		return null;
	}
	
	private ModuleObject getPort() {
		int sel = signalCombo.getSelectionIndex();
		if (sel >= 0) {
			ModuleObject obj = portList.get(sel);
			return obj;
		}
		return null;
	}
	
	private void create(String prefix) {
		GridLayout gridLayout = new GridLayout( 1, false );
        gridLayout.marginWidth = 10;
        gridLayout.marginHeight = 10;
        this.setLayout( gridLayout );
        
        String labelText = prefix + " Port Module";
        Label nameLabel = new Label(this,SWT.LEFT);
        nameLabel.setText(labelText);
        
        this.instanceCombo = new Combo(this,SWT.READ_ONLY);
        this.instanceCombo.addModifyListener(new ComboListener());
        HierarchyList list = ActiveSuiteHolder.getDefault().getSuite().getHierListReference().getObject();
        this.pathList = list.getFullList();
        for (PathTreeElement path : pathList) {
            this.instanceCombo.add(path.getPathName());
        }
        labelText = prefix + " Signal";
        Label nameLabel2 = new Label(this,SWT.LEFT);
        nameLabel2.setText(labelText);
        this.signalCombo = new Combo(this,SWT.READ_ONLY);
	}
	
	public  class ComboListener implements ModifyListener {
    	public ComboListener() {}


		public void modifyText(ModifyEvent e) {
			int sel = instanceCombo.getSelectionIndex();
			if (sel > 0) {
				PathTreeElement el = pathList.get(sel);
				InstanceModule instMod = el.getModule();
				portList = instMod.getAllVars();
				for (ModuleObject item : portList){
					signalCombo.add(item.getname());
				}
			}
			
			
		}
    }

}
