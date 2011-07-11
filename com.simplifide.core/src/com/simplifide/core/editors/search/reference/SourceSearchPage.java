/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.reference;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUsage;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.search.RefactorTreeHolder;
import com.simplifide.core.search.SourceMatch;
import com.simplifide.core.source.LocationOperations;

public class SourceSearchPage extends AbstractTextSearchViewPage{

	
    private TreeViewer viewer;
    
	public SourceSearchPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_TREE);
	}
	
	@Override
	protected void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void configureTableViewer(TableViewer viewer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		viewer.setContentProvider(new SourceSearchContentProvider());
		viewer.setLabelProvider(new SourceSearchLabelProvider());	
        viewer.setSorter(new ViewerSorter());
        viewer.getControl().addMouseListener(new HierMouseListener());
        this.viewer = viewer;
	}

	@Override
	protected void elementsChanged(Object[] objects) {
		if (objects == null || objects.length == 0) return;
            SourceMatch[] matches = new SourceMatch[objects.length];
            for (int i=0;i<objects.length;i++) {
                if (objects[i] instanceof SourceMatch) {
                    matches[i] = (SourceMatch) objects[i];
                }
                else if (objects[i] instanceof ReferenceUsage) {
                    ReferenceUsage usage = (ReferenceUsage) objects[i];
                    matches[i] = new SourceMatch(usage);
                }
            }
            if (matches != null) {
                RefactorTreeHolder.Root root = new RefactorTreeHolder.Root();
                    for (SourceMatch match : matches) {
                        root.addElement(match);
                        root.sortElements();
                    }
                
                    this.getViewer().setInput(root);
                }
            }

        private class HierMouseListener extends MouseAdapter{
                
                private void gotoSelection() {
                if (!HardwareChecker.isSearchEnabled()) return;
                        IStructuredSelection select = (IStructuredSelection) viewer.getSelection();
                        Object obj = select.getFirstElement();
                        if (obj instanceof RefactorTreeHolder.Element) {
                            RefactorTreeHolder.Element element = (RefactorTreeHolder.Element) obj;
                            ReferenceLocation location = element.getUsage().getUsage().getLocation();
                            //SourceLocationHandler.getInstance().goToPosition(location);  
                            LocationOperations.goToPosition(location);
                        }
                }
                
                public void mouseDoubleClick(MouseEvent e) {
                        this.gotoSelection();
                        
                }
                public void mouseDown(MouseEvent e) {
                        
                }
        }
        
}


