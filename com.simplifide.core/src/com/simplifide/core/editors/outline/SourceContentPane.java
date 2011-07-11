/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.outline;



import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.baseeditor.outline.GeneralContentPane;
import com.simplifide.core.baseeditor.outline.SourceTreeViewer;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.refactor.port.add.AddPortRefactorProcessor;
import com.simplifide.core.refactor.port.add.AddPortRefactoring;
import com.simplifide.core.refactor.port.add.AddPortWizard;
import com.simplifide.core.refactor.port.connect.ConnectPortRefactorProcessor;
import com.simplifide.core.refactor.port.connect.ConnectPortRefactoring;
import com.simplifide.core.refactor.port.connect.ConnectPortWizard;
import com.simplifide.core.refactor.port.remove.RemovePortRefactorProcessor;
import com.simplifide.core.refactor.port.remove.RemovePortRefactoring;
import com.simplifide.core.refactor.port.remove.RemovePortWizard;
import com.simplifide.core.refactor.rename.RenameRefactorProcessor;
import com.simplifide.core.refactor.rename.RenameRefactoring;
import com.simplifide.core.refactor.rename.RenameWizard;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.ui.tree.ReferenceItemLabelProvider;
import com.simplifide.core.ui.tree.ReferenceItemTreeSorter;



public class SourceContentPane extends GeneralContentPane
 {
	
	
	

	
	public SourceContentPane(SourceEditor editor) {
		super(editor);
	}
	

    protected void createActions() {
    	IViewPart view = null;
    	if (this.getSite() != null && this.getSite().getPage() != null) {
    		view = this.getSite().getPage().findView("org.eclipse.ui.views.ContentOutline");
    	}
    	

    	if (view != null) {
        	OutlineFilterMenu menu = OutlineFilterMenu.getInstance();
        	menu.setPage(this);
    		IMenuManager manager = view.getViewSite().getActionBars().getMenuManager();
    		manager.add(menu);
    	}
    }
    
    protected void removeActions() {
    	IViewPart view = this.getSite().getPage().findView("org.eclipse.ui.views.ContentOutline");
    	if (view != null) {
    		OutlineFilterMenu menu = OutlineFilterMenu.getInstance();
        	menu.setPage(null);
    		IMenuManager manager = view.getViewSite().getActionBars().getMenuManager();
    		manager.remove(menu);
    	}
    }
    
    private ReferenceItem getEntityR(ReferenceItem ref) {
    	if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_ENTITY) == 0) {
			return ref;
		}
    	else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_HARDWARE_MODULE) == 0) {
    		HardwareModule moda = (HardwareModule) ref.getObject();
    		InstanceModule mod = (InstanceModule) moda.getInstModRef().getObject();
    		return mod.getEntityReference();
		}
		else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_INSTANCE_MODULE_TOP) == 0) {
			InstanceModule mod = (InstanceModule) ref.getObject();
			return mod.getEntityReference();
		}
		else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_MODINSTANCE_CONNECT) == 0) {
			ModInstanceConnect mod = (ModInstanceConnect) ref.getObject();
			return mod.getEntityRef();
		}
    	return null;
    }
    
    private void createMenu(final Menu menu, TreeItem item) {
    	if (item.getData() instanceof ReferenceItem) {
    		ReferenceItem ref = (ReferenceItem) item.getData();
    		if (ref != null) {
    			ReferenceLocation loc = ref.getLocation();
    			if (loc != null) {
    				EditorFindItem fitem = new EditorFindItem(ref,ref,loc.getDocPosition(),loc.getDocPosition() + loc.getLength());
                    MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
                    menuItem.setText ("Rename");
                    menuItem.addSelectionListener(new RenameListener(fitem));
                    ReferenceItem eref = getEntityR(ref);
                    if (eref != null) {
                    	MenuItem addItem = new MenuItem (menu, SWT.PUSH);
                    	addItem.setText ("Add Port");
                    	addItem.addSelectionListener(new AddListener(eref));
                    	MenuItem connectItem = new MenuItem (menu, SWT.PUSH);
                    	connectItem.setText ("Connect Port");
                    	connectItem.addSelectionListener(new ConnectListener(eref));
                    	MenuItem removeItem = new MenuItem (menu, SWT.PUSH);
                    	removeItem.setText ("Remove Port");
                    	removeItem.addSelectionListener(new RemoveListener(eref));

                    }
    			}
    		}
    	}
    	
    	
    }
    
	private void hookPopUpListener() {
		final Tree tree = this.getTreeViewer().getTree();
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
                        createMenu(menu,treeItem);    
                    
                    }
            }
    }); 

	}
    
    
    protected void createTreeViewer() {
    	TreeViewer viewer = this.getTreeViewer();
        viewer.setContentProvider(new SourceContentPaneTreeProvider());
        viewer.setLabelProvider(new ReferenceItemLabelProvider());
        viewer.setSorter(new ReferenceItemTreeSorter());
        
        // Add the Actions and Filter
        //this.addActions();
        viewer.addFilter(new SourceOutlineFilter());
        
        // Add the listener for GoTo Operations
        viewer.addDoubleClickListener(new DoubleClick());
        this.hookPopUpListener();
        // TODO Need to add a menu manager which applies actions
        
    }
	
	public void createControl(Composite parent) {
        super.createControl(parent);
        
    }

	private void normalizePath(TreeViewer view, TreePath[] initpath, TreePath[] finalpath) {
		
		//Object obj = view.getData("polar_top");
		//initpath[0].get
		//int alpha = 1;
		
	}
	
	protected void setInput() {
		SourceTreeViewer view = getTreeViewer();
		if (view == null) return;
		if (this.getSourceEditor() != null && view != null) {
			if (getSourceEditor().getDesignFile() != null) {
				ReferenceItem item = getSourceEditor().getDesignFile().getModuleRef();
				if (item != null) {

					TreePath[] initpath = view.getExpandedTreePaths();
					view.setInput(item);
					view.expandToLevel(2);
					//view.expandAll();
					TreePath[] finalpath = view.getExpandedTreePaths();
					this.normalizePath(view, initpath, finalpath);
					
					
				}
				
			} 
		} else {
			view.setInput(null);
		}

	} 
	
	
	
	public SourceEditor getSourceEditor() {
		return (SourceEditor) getEditor();
	}
	
	public class DoubleClick implements IDoubleClickListener {
		public DoubleClick() {}

		public void doubleClick(DoubleClickEvent event) {
            if (!HardwareChecker.isHyperLinkEnabled()) return;
			
            IStructuredSelection select = (IStructuredSelection) event.getSelection();
			Object obj = select.getFirstElement();
			if (obj instanceof ReferenceItem) {
				ReferenceItem item = (ReferenceItem) obj;
				ReferenceLocation location = item.getLocation();
				if (location != null) {
					LocationOperations.goToPosition(location);
					//SourceLocationHandler.getInstance().goToPosition(location);

				}
			}
		}
		
	}
	 
	private class RenameListener implements SelectionListener {
		
		EditorFindItem item;
		public RenameListener(EditorFindItem item) {
			this.item = item;
		}
		public void widgetSelected(SelectionEvent e) {
			RenameRefactorProcessor processor = new RenameRefactorProcessor( item ,null );
	        RenameRefactoring ref = new RenameRefactoring( processor ); 
	        RenameWizard wizard = new RenameWizard( ref );
	        RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(wizard ); 
	        try { 
	            op.run(getTreeViewer().getTree().getShell(), "" ); 
	        } catch( InterruptedException irex ) { 
	            
	        }
			
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
private class AddListener implements SelectionListener {
		
		ReferenceItem ref;
		public AddListener(ReferenceItem ref) {
			this.ref = ref;
		}
		public void widgetSelected(SelectionEvent e) {
			AddPortRefactorProcessor processor = new AddPortRefactorProcessor(
					ref, null);
			AddPortRefactoring ref = new AddPortRefactoring(processor);
			AddPortWizard wizard = new AddPortWizard(ref);
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
					wizard);
			try {
				String titleForFailedChecks = ""; //$NON-NLS-1$ 
				op.run(getTreeViewer().getTree().getShell(), titleForFailedChecks);
			} catch (InterruptedException irex) {

			}
			
		}

		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
private class ConnectListener implements SelectionListener {
	
	ReferenceItem ref;
	public ConnectListener(ReferenceItem ref) {
		this.ref = ref;
	}
	public void widgetSelected(SelectionEvent e) {
		ConnectPortRefactorProcessor processor = new ConnectPortRefactorProcessor(
				ref, null);
		ConnectPortRefactoring ref = new ConnectPortRefactoring(processor);
		ConnectPortWizard wizard = new ConnectPortWizard(ref);
		RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
				wizard);
		try {
			String titleForFailedChecks = ""; //$NON-NLS-1$ 
			op.run(getTreeViewer().getTree().getShell(), titleForFailedChecks);
		} catch (InterruptedException irex) {

		}
		
	}
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	
	private class RemoveListener implements SelectionListener {
		
		ReferenceItem ref;
		public RemoveListener(ReferenceItem ref) {
			this.ref = ref;
		}
		public void widgetSelected(SelectionEvent e) {
			RemovePortRefactorProcessor processor = new RemovePortRefactorProcessor(
					ref,null);
			RemovePortRefactoring ref = new RemovePortRefactoring(processor);
			RemovePortWizard wizard = new RemovePortWizard(ref);
			RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
					wizard);
			try {
				String titleForFailedChecks = ""; //$NON-NLS-1$ 
				op.run(getTreeViewer().getTree().getShell(), titleForFailedChecks);
			} catch (InterruptedException irex) {

			}
			
		}


	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
	
	/*
	public class TreeMouseListener extends MouseAdapter {
		 public void mouseDown(MouseEvent e){
			 Shell shell = getTreeViewer().getTree().getShell();
				if(e.button==3){
					Menu menu = new Menu(shell,SWT.POP_UP);
					MenuItem item = new MenuItem(menu, SWT.PUSH);
					item.setText("alpha");
					item.setMenu(menu);
					shell.open();
					System.out.println("3 pressed");
				}
			}
	}
	*/
	
	
	
 }
