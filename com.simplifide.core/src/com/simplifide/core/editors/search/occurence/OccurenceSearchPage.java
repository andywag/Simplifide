/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.occurence;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;

public class OccurenceSearchPage extends AbstractTextSearchViewPage{

	
    private TreeViewer viewer;
    
	public OccurenceSearchPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_FLAT);
	}
	
	
	@Override
	protected void clear() {
	}


	
	@Override
	protected void configureTableViewer(TableViewer viewer) {
		viewer.setContentProvider(new OccurenceContentProvider());
		viewer.setLabelProvider(new OccurenceContentProvider.Label());			
	}

	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		
		viewer.setContentProvider(new OccurenceContentProvider());
		viewer.setLabelProvider(new OccurenceContentProvider.Label());	
	}

	@Override
	protected void elementsChanged(Object[] objects) {
	
    }

        
}


