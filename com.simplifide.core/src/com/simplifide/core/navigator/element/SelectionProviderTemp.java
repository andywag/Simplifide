package com.simplifide.core.navigator.element;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;

public class SelectionProviderTemp implements ISelectionProvider{

	private StructuredSelection resource;
	
	public SelectionProviderTemp(IResource res) {
		this.resource = new StructuredSelection(res);
	}
	
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// TODO Auto-generated method stub
		
	}

	
	public ISelection getSelection() {
		return this.resource;
	}

	
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		// TODO Auto-generated method stub
		
	}

	
	public void setSelection(ISelection selection) {
		// TODO Auto-generated method stub
		
	}

}
