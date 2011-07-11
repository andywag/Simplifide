package com.simplifide.core.refactor.port.connect;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import com.simplifide.base.core.hierarchy.HierarchyList;
import com.simplifide.base.core.hierarchy.PathTotalElement;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.core.ActiveSuiteHolder;

public class ConnectPortTreeComposite extends Composite {

	private TreeViewer  sourceViewer;
	private TreeViewer  destViewer;
	private ListViewer  sourceList;
	//private Button    allCheckBox;
	
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ConnectPortTreeComposite(Composite parent, int style) {
		super(parent, style);
		
		 HierarchyList list = ActiveSuiteHolder.getDefault().getSuite().getHierListReference().getObject();
		 PathTreeElement treeRoot = list.getTreeRoot();
		 
		sourceViewer = new TreeViewer(this, SWT.BORDER);
		Tree tree = sourceViewer.getTree();
		tree.setBounds(10, 30, 300, 400);

		sourceViewer.setContentProvider(new PathTreeContentProvider());
		sourceViewer.setLabelProvider(new PathTreeContentProvider.Label());
		sourceViewer.setInput(treeRoot);
		sourceViewer.addSelectionChangedListener(new SourceTreeListener());
		
		
		this.sourceList = new ListViewer(this, SWT.BORDER | SWT.V_SCROLL  );
		this.sourceList.getList().setBounds(10, 460, 300, 200);
		this.sourceList.setContentProvider(new SourceListContentProvider());
		this.sourceList.setLabelProvider(new SourceListContentProvider.Label());
		//this.sourceList.setCont
		
		destViewer = new TreeViewer(this, SWT.BORDER | SWT.MULTI);
		tree = destViewer.getTree();
		tree.setBounds(340, 30, 300, 400);
		destViewer.setContentProvider(new PathTreeContentProvider());
		destViewer.setLabelProvider(new PathTreeContentProvider.Label());
		destViewer.setInput(treeRoot);
		
		
		
		//this.allCheckBox = new Button(this,SWT.CHECK);
		//this.allCheckBox.setBounds(340, 460, 200, 15);
		//this.allCheckBox.setText("All Instances of Destination");
		//this.allCheckBox.setEnabled(true);

		Label lblSourceLocation = new Label(this, SWT.NONE);
		lblSourceLocation.setBounds(110, 10, 98, 15);
		lblSourceLocation.setText("Source Location");


		Label lblDestinationLocation = new Label(this, SWT.NONE);
		lblDestinationLocation.setBounds(440, 10, 127, 15);
		lblDestinationLocation.setText("Destination Location");


		Label lblSourcePort = new Label(this, SWT.NONE);
		lblSourcePort.setBounds(109, 276, 101, 15);
		lblSourcePort.setText("Source Port");
	}

	private ArrayList<SystemVar> getSignalList() {
		IStructuredSelection sel = (IStructuredSelection) this.sourceList.getSelection();
		Object[] objects = sel.toArray();
		ArrayList<SystemVar> newList = new ArrayList<SystemVar>();
		for (Object object : objects) {
			newList.add((SystemVar)object);
		}
		return newList;
	}
	
	public ArrayList<PathTotalElement> getSource() {
		ArrayList<PathTotalElement> pathList = new ArrayList<PathTotalElement>();
		ITreeSelection select = (TreeSelection) this.sourceViewer.getSelection();
		
		PathTreeElement obj = ((PathTreeElement)select.getFirstElement());
		
		for (SystemVar tvar : this.getSignalList()) {
			PathTotalElement el = new PathTotalElement(obj,tvar,0);
			pathList.add(el);
		}
		return pathList;
	}
	
	public ArrayList<PathTotalElement> getDestination() {
		ITreeSelection select = (TreeSelection) this.destViewer.getSelection();
		TreePath[] paths = select.getPaths();
		ArrayList<PathTotalElement> elements = new ArrayList<PathTotalElement>();
		for (TreePath path : paths) {
			PathTreeElement pobj = (PathTreeElement) path.getLastSegment();
			elements.add(new PathTotalElement(pobj,null,0));
		}
		
		return elements;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
		
	public class SourceTreeListener implements ISelectionChangedListener {

		private void handleInstanceModuleChange(InstanceModule umod) {
			sourceList.setInput(umod.getAllVars());
			
		}
		
		public void selectionChanged(SelectionChangedEvent event) {
			ITreeSelection selection = (ITreeSelection) event.getSelection();
			PathTreeElement ref = (PathTreeElement) selection.getFirstElement();
			this.handleInstanceModuleChange(ref.getModule());
			
			
		}

		
		
		
	}
	
}
