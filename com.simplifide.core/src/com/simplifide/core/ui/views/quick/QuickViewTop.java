package com.simplifide.core.ui.views.quick;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.hierarchy.GenerateConnectorHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.LocationOperations;

public abstract class QuickViewTop extends AbstractQuickViewTop{

	private MouseListener mouseListener;
	private TreeViewer fTreeViewer;
	private SourceEditor editor;
	
	public QuickViewTop(SourceEditor editor,Shell parent, int shellStyle, int treeStyle) {
		super(parent, shellStyle, treeStyle);
		this.editor = editor;
	}

	protected TreeViewer createTreeViewer(Composite parent, int style) {
		Tree tree= new Tree(parent, SWT.SINGLE | (style & ~SWT.MULTI));
		GridData gd= new GridData(GridData.FILL_BOTH);
		gd.heightHint= tree.getItemHeight() * 12;
		gd.widthHint = gd.heightHint;
		tree.setLayoutData(gd);
		this.fTreeViewer = new TreeViewer(tree);
		this.mouseListener = new ViewMouseListener();
		this.fTreeViewer.getControl().addMouseListener(this.mouseListener);
		return fTreeViewer;
	}
	
	public void dispose() {
		this.fTreeViewer.getControl().removeMouseListener(this.mouseListener);
		super.dispose();
	}

	
	private class ViewMouseListener extends MouseAdapter{
		
		private void gotoSelection() {
                  
			IStructuredSelection select = (IStructuredSelection) getfTreeViewer().getSelection();
			Object obj = select.getFirstElement();
			if (obj instanceof ReferenceItem) {
				ModuleObject uobj = ((ReferenceItem)obj).getObject();
				if (uobj instanceof GenerateConnectorHolder) {
					GenerateConnectorHolder gen = (GenerateConnectorHolder) uobj;
					LocationOperations.goToPosition(gen.getGenerate().getLocation());
				}
				else if (uobj instanceof ConnectorHolder) {
					ConnectorHolder connect = (ConnectorHolder) uobj;
					ConnectorModule conn2 = connect.getModuleRef().getObject();
					ReferenceLocation loc = conn2.getEntityLocation();
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

	
	public void setfTreeViewer(TreeViewer fTreeViewer) {
		this.fTreeViewer = fTreeViewer;
	}

	public TreeViewer getfTreeViewer() {
		return fTreeViewer;
	}

	public void setEditor(SourceEditor editor) {
		this.editor = editor;
	}

	public SourceEditor getEditor() {
		return editor;
	}

}
