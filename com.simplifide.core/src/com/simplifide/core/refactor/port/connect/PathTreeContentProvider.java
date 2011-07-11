package com.simplifide.core.refactor.port.connect;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.core.hierarchy.GeneratePathTreeElement;
import com.simplifide.base.core.hierarchy.PathTreeElement;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.core.resources.IconManager;

public class PathTreeContentProvider implements ITreeContentProvider{

	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof PathTreeElement) {
			PathTreeElement el = (PathTreeElement) parentElement;
			return el.getPathList().toArray();
		}
		else {
			ArrayList pathList = (ArrayList) parentElement;
			return pathList.toArray();
		}
		
	}

	
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean hasChildren(Object element) {
		if (element instanceof PathTreeElement) {
			PathTreeElement el = (PathTreeElement) element;
			if (el.getPathList().size() > 0) return true;
		}
		else {
			ArrayList pathList = (ArrayList) element;
			if (pathList.size() > 0) return true;
		}
		return false;
	}

	
	public Object[] getElements(Object inputElement) {
		return this.getChildren(inputElement);

	}

	
	public void dispose() {
		
	}

	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}
	
	public static class Label extends LabelProvider implements IColorProvider, IFontProvider {

		public Image getImage(Object element) {
			if (element instanceof GeneratePathTreeElement) {
				Image desc = 	IconManager.getImage(ReferenceUtilitiesInterface.REF_GENERATE_STATEMENT);
				return desc;
			}
			Image desc = IconManager.getImage(ReferenceUtilitiesInterface.REF_ENTITY);
	        return desc;
	    }

	 
	    public String getText(Object element) {
	    	if (element instanceof GeneratePathTreeElement) {
	    		GeneratePathTreeElement item = (GeneratePathTreeElement) element;
	    		return item.getName();
	    	}
	    	else if (element instanceof PathTreeElement) {
		        PathTreeElement item = (PathTreeElement) element;
		        if (item.getConnection() != null) return item.getConnection().getDisplayName(); 
		        else return item.getModule().getname();
	    	}
	        return "";
	    }
		
		public Color getBackground(Object element) {
			return null;
		}

		
		public Color getForeground(Object element) {
			return null;
		}

		
		public Font getFont(Object element) {
			return null;
		}
		
	}

}
