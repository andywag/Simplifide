/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator.filter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.simplifide.core.navigator.NavigatorInterface;
import com.simplifide.core.navigator.element.SuiteElement;

public class SuiteFilter extends ViewerFilter {

	public SuiteFilter() {
		// TODO Auto-generated constructor stub
	}
	
	/*private TopElement createProjectReference(Object obj) {
		if (obj instanceof IProject) {
			IProject proj = (IProject) obj;
			try {
				if (proj.isOpen() && proj.hasNature(HardwareNature.ID)) {
					String projName = proj.getName();
					
					return new SuiteElement(projName);
					
				}
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
		}
		
		return null;
	}
	
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		if (parent instanceof IWorkspaceRoot) {
			ArrayList outList = new ArrayList();
			for (Object element : elements) {
				TopElement el = this.createProjectReference(element);
				if (el != null) outList.add(el);
			}
			return outList.toArray();
		}
		return super.filter(viewer, parent, elements);
	}*/

	private boolean checkNames(String name) {
		if (name.equalsIgnoreCase("script")) return true;
		/*if (ProjectRootStructureDirectory.getDefault().getBuildStructure() != null &&
			name.equalsIgnoreCase(ProjectRootStructureDirectory.getDefault().getBuildStructure().getName())) return true;
		
		if (ProjectRootStructureDirectory.getDefault().getSynthesisStructure() != null &&
			name.equalsIgnoreCase(ProjectRootStructureDirectory.getDefault().getSynthesisStructure().getName())) return true;
		
		if (ProjectRootStructureDirectory.getDefault().getRouteStructure() != null &&
			name.equalsIgnoreCase(ProjectRootStructureDirectory.getDefault().getRouteStructure().getName())) return true;
		
		if (ProjectRootStructureDirectory.getDefault().getTestStructure() != null &&
			name.equalsIgnoreCase(ProjectRootStructureDirectory.getDefault().getTestStructure().getName())) return true;
		*/
		return false;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof NavigatorInterface || element instanceof IProject) {
			return true;
		}
		if (element instanceof IFile) {
			return true;
		}
		if (element instanceof IFolder) {
			IFolder el = (IFolder) element;
			if (checkNames(el.getName())) {
				if (parentElement instanceof TreePath) {
					TreePath path = (TreePath) parentElement;
					if (path.getLastSegment() instanceof SuiteElement) return true;
				}
			}
			else return false;
		}
		// TODO Auto-generated method stub
		return false;
	}

}
