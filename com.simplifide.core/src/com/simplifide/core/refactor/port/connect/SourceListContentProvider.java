package com.simplifide.core.refactor.port.connect;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.core.resources.IconManager;

public class SourceListContentProvider implements IStructuredContentProvider{

	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	
	public Object[] getElements(Object inputElement) {
		ArrayList<SystemVar> newList = new ArrayList<SystemVar>();
		ArrayList<SystemVar> lis = (ArrayList<SystemVar>)inputElement;
		//return lis.toArray();
		
		for (SystemVar uvar : lis) {
			//if (uvar.getVariableType() != SystemVar.INPUT) {
				newList.add(uvar);
			//}
		}
		return newList.toArray();
	}
	
	public static class Label implements ILabelProvider{

		
		public Image getImage(Object element) {
			ModuleObject obj = (ModuleObject) element;
			Image desc = IconManager.getImage(obj.getSearchType());
			return desc;
		}

		
		public String getText(Object element) {
			SystemVar obj = (SystemVar) element;
			String t = "";
			if (obj.getVariableType() == SystemVar.INPUT) t = "input";
			else if (obj.getVariableType() == SystemVar.OUTPUT) t = "output";
			else if (obj.getVariableType() == SystemVar.INOUT) t = "inout";
			else return obj.getname();

			return obj.getname() + "(" + t + ")";
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
