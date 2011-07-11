package com.simplifide.core.ui.views.quick;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.project.hier.HierarchyManager;
import com.simplifide.core.ui.tree.ReferenceItemLabelProvider;
import com.simplifide.core.ui.tree.ReferenceItemTreeProvider;

public class QuickHierarchy extends QuickViewTop{

	public QuickHierarchy(SourceEditor editor,Shell parent, int shellStyle, int treeStyle) {
		super(editor,parent, shellStyle, treeStyle);
	}

	protected String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected TreeViewer createTreeViewer(Composite parent, int style) {
		TreeViewer viewer = super.createTreeViewer(parent, style);
		viewer.setLabelProvider(new ReferenceItemLabelProvider());
		viewer.setContentProvider(new ReferenceItemTreeProvider());
		return viewer;
		
	}

	public void setInput(Object information) {
		SourceEditor editor = com.simplifide.core.baseeditor.EditorUtilities.getActiveSourceEditor();
		int pos = editor.getCaretPosition();
		ParseDescriptor parse = editor.getDesignFile().getParseDescriptor();
		InstanceModuleTop top = EditorUtilities.getEnclosingInstanceModule(parse, pos);
		
		if (top != null && top instanceof InstanceModule) {
			InstanceModule inst = (InstanceModule) top;
			ConnectorModule cmod = (ConnectorModule) inst.getConnectReference().getObject();
			
			if (cmod.getParentRef() != null) {
				ModuleObject last = cmod;
					ModuleObjectWithList parents = (ModuleObjectWithList) cmod.getParentRef().getObject();
					ReferenceItem<ConnectorModule> parentR = (ReferenceItem<ConnectorModule>) parents.getObject(0);
					if (parentR != null) {
						ConnectorModule parent = parentR.getObject();
						ConnectorModuleDelegate del = new ConnectorModuleDelegate(parent);
						del.addReferenceItem(cmod.createReferenceItem());
						last = del;
						while (del != null) {
							parents = (ModuleObjectWithList) del.mod.getParentRef().getObject();
							parentR = (ReferenceItem<ConnectorModule>) parents.getObject(0);
							if (parentR != null) {
								parent = parentR.getObject();
								del = new ConnectorModuleDelegate(parent);
								del.addReferenceItem(last.createReferenceItem());
								last = del;
							}
							else {
								del = null;
							}
						}
					}
					
				
				ModuleObjectWithList root = new ModuleObjectWithList();
				root.addReferenceItem(last.createReferenceItem());
				this.getfTreeViewer().setInput(root.createReferenceItem());
				this.getfTreeViewer().expandAll();
			}
			else {
				this.getfTreeViewer().setInput(cmod.createReferenceItem());

			}
			
		}
		else {
			this.getfTreeViewer().setInput(HierarchyManager.getInstance().getHierList());

		}

	}
	
	public static class ConnectorModuleDelegate extends ModuleObjectWithList {
		
		public ConnectorModule mod;
		public  ConnectorModuleDelegate(ConnectorModule mod) {
			super(mod.getname());
			this.mod = mod;
			// Seems kludgy and is
			this.createReferenceItemWithLocation(mod.createReferenceItem().getLocation());
		}
		public int getSearchType () {
			return ReferenceUtilities.REF_CONNECTOR_MODULE;
		}
	}
}
