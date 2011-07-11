/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.reference;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.core.resources.BasicIconManager;
import com.simplifide.core.search.RefactorTreeHolder;

public class SourceSearchLabelProvider implements ILabelProvider{

	public Image getImage(Object element) {
	    if (element instanceof RefactorTreeHolder.Project) {
	        return BasicIconManager.getRealImage(BasicIconManager.MAIN_PROJECT);
            }
            else if (element instanceof RefactorTreeHolder.File) {
                return BasicIconManager.getRealImage(BasicIconManager.MAIN_MODULE);
            }
            else {
                return BasicIconManager.getRealImage(BasicIconManager.MAIN_ASSIGN);
            }
            
	}

	public String getText(Object element) {
            /*if (element instanceof RefactorTreeHolder.Element) {
                RefactorTreeHolder.Element sel = (RefactorTreeHolder.Element) element;
                int line = sel.getUsage().getUsage().getLocation().getLine();
                return "Line " + line + " : " + sel.getDisplayName();
            }
            else {*/
                ModuleObject el = (ModuleObject) element;
                return el.getDisplayName();
            //}
	   
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
