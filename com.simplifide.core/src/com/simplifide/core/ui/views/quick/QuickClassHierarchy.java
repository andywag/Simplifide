package com.simplifide.core.ui.views.quick;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.project.hier.ClassHierarchyManager;
import com.simplifide.core.ui.tree.ReferenceItemLabelProvider;
import com.simplifide.core.ui.views.ClassContentProvider;

public class QuickClassHierarchy extends QuickViewTop{

	public QuickClassHierarchy(SourceEditor editor,Shell parent, int shellStyle, int treeStyle) {
		super(editor,parent, shellStyle, treeStyle);
	}

	protected String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected TreeViewer createTreeViewer(Composite parent, int style) {
		TreeViewer viewer = super.createTreeViewer(parent, style);
		viewer.setLabelProvider(new ReferenceItemLabelProvider());
		viewer.setContentProvider(new ClassContentProvider());
		return viewer;
		
	}

	public void setInput(Object information) {
		SourceEditor editor = com.simplifide.core.baseeditor.EditorUtilities.getActiveSourceEditor();
		int pos = editor.getCaretPosition();
		ParseDescriptor parse = editor.getDesignFile().getParseDescriptor();
		InstanceModuleTop top = EditorUtilities.getEnclosingInstanceModule(parse, pos);
		if (top != null && top instanceof ClassInstanceModule) {
			ClassInstanceModule inst = (ClassInstanceModule) top;
			ModuleObject base = inst;
			if (inst.getSuperR() != null) {
				ClassInstanceModuleDelegate del = new ClassInstanceModuleDelegate(inst.getSuperR().getObject());
				del.addReferenceItem(inst.createReferenceItem());
				while (del.mod.getSuperR() != null) {
					ClassInstanceModule nv = del.mod.getSuperR().getObject();
					ClassInstanceModuleDelegate del2 = new ClassInstanceModuleDelegate(nv);
					del2.addReferenceItem(del.createReferenceItem());
					del = del2;
				}
				base = del;
			}
			ModuleObjectWithList root = new ModuleObjectWithList();
			root.addReferenceItem(base.createReferenceItem());
			this.getfTreeViewer().setInput(root.createReferenceItem());
			this.getfTreeViewer().expandAll();
			
			
		}
		else {
			this.getfTreeViewer().setInput(ClassHierarchyManager.getInstance().getContents());
		}
	}

	
	public static class ClassInstanceModuleDelegate extends ModuleObjectWithList {
		
		public ClassInstanceModule mod;
		public  ClassInstanceModuleDelegate(ClassInstanceModule mod) {
			super(mod.getname());
			this.mod = mod;
			// Seems kludgy and is
			this.createReferenceItemWithLocation(mod.createReferenceItem().getLocation());
		}
		public int getSearchType () {
			return ReferenceUtilities.REF_INSTANCE_CLASS;
		}
	}
	
}
