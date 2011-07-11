package com.simplifide.core.editors.search.fan;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.core.hierarchy.PathTotalElement;
import com.simplifide.base.core.hierarchy.PathTotalTreeElement;
import com.simplifide.core.resources.BasicIconManager;

public class FanSearchContentProvider implements ITreeContentProvider{

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof PathTotalTreeElement) {
			PathTotalTreeElement el = (PathTotalTreeElement) parentElement;
			return el.getChildren().toArray();
		}
		return new Object[0];
	}

	public Object getParent(Object element) {
		if (element instanceof PathTotalTreeElement) {
			PathTotalTreeElement el = (PathTotalTreeElement) element;
			return el.getChildren();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		if (element instanceof PathTotalTreeElement) {
			PathTotalTreeElement el = (PathTotalTreeElement) element;
			if (el.getChildren().size() > 0) return true;
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
		
	}
	
	public static class Label implements ILabelProvider {

		 
		public Image getImage(Object element) {
			if (element instanceof PathTotalTreeElement) {
				PathTotalTreeElement tree = (PathTotalTreeElement) element;
				if (tree.getPathElement() != null) {
					int dir = tree.getPathElement().getDirection();
					if (dir == PathTotalElement.UP) return BasicIconManager.getRealImage(BasicIconManager.MAIN_UP);
					else if (dir == PathTotalElement.FLAT) return BasicIconManager.getRealImage(BasicIconManager.MAIN_FLAT);
					else if (dir == PathTotalElement.DOWN) return BasicIconManager.getRealImage(BasicIconManager.MAIN_DOWN);
				}
				
			}
			
			return null;
		}

		 
		public String getText(Object element) {
			if (element instanceof PathTotalTreeElement) {
				PathTotalTreeElement el = (PathTotalTreeElement) element;
				return el.toString();
			}
			return "";
		}

		 
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		 
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		 
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		 
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
