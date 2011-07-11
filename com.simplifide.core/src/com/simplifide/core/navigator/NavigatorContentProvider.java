/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.baseeditor.source.SourceObject;
import com.simplifide.core.builder.BuildHandler;
import com.simplifide.core.navigator.element.ElementFactory;
import com.simplifide.core.navigator.element.SuiteElement;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.source.design.DesignFile;

public class NavigatorContentProvider implements ITreeContentProvider {

	private StructuredViewer viewer;
	
	public NavigatorContentProvider() {
		ActiveSuiteHolder.getDefault().setTreeContent(this);
	}
	
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IProject) {
			IProject project = (IProject) parentElement;
			String projName = project.getName();
			EclipseSuite suite = BuildHandler.getSuite(project);
			
			return new Object[] {
					new SuiteElement(project, suite.createReferenceItem())
			};
		}
		if (parentElement instanceof NavigatorInterface) {
			NavigatorInterface nint = (NavigatorInterface) parentElement;
			//nint.setTreeProvider(this);
			return nint.getChildren();
		}
		
		if (parentElement instanceof IFile) {
			SourceObject obj = SourceObject.retrieveSourceObject((IFile)parentElement);
			if (obj instanceof DesignFile) {
				DesignFile dfile = (DesignFile) obj;
				List<ReferenceItem> slist = dfile.getDesignFileBuilder().getSuperModule().getObject().getGenericSelfList();
				ArrayList outList = new ArrayList();
				for (ReferenceItem item : slist) {
					outList.add(ElementFactory.createElement(item));
				}
				return outList.toArray();
			}
		}
		
		return null;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return true;
		}
		if (element instanceof NavigatorInterface) {
			NavigatorInterface nint = (NavigatorInterface) element;
			return nint.hasChildren();
		}
		if (element instanceof IFile) {
			SourceObject obj = SourceObject.retrieveSourceObject((IFile)element);
			if (obj instanceof DesignFile) {
				return true;
			}
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return this.getChildren(inputElement);
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		this.viewer = (StructuredViewer) viewer;
		
		
	}
	  
	
	public void fireChange() {
		Display display = this.viewer.getControl().getDisplay();
		if (display != null) {
			display.asyncExec(new Runnable() {
				public void run() {
					viewer.refresh();
					
				}
			}); 
			}
	}
	
	

	
	
	

}
