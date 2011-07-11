/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import com.simplifide.core.ActiveSuiteHolder;
import com.simplifide.core.navigator.element.BasicProjectElement;
import com.simplifide.core.navigator.element.SuiteElement;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.EclipseSuite;

public class NavigatorLabelProvider implements ILabelProvider, IColorProvider, IFontProvider {

	public Image getImage(Object element) {
		if (element instanceof NavigatorInterface) {
			NavigatorInterface nint = (NavigatorInterface) element;
			if (nint.getImageDescriptor() != null) {
				return nint.getImageDescriptor();
				
			}
		}
		return null;
	}

	public String getText(Object element) {
	
		if (element instanceof NavigatorInterface) {
			NavigatorInterface nint = (NavigatorInterface) element;
			return nint.getName();
		}
		return null;
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

	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public Color getForeground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public Font getFont(Object element) {
		if (element instanceof SuiteElement) {
			SuiteElement el = (SuiteElement) element;
			EclipseSuite suite = el.getItem().getObject();
			EclipseSuite active = ActiveSuiteHolder.getDefault().getSuite();
			if (suite.equals(active)) {
				Font ufont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
				return ufont;
			}
		}
		else if (element instanceof BasicProjectElement) {
			BasicProjectElement proj = (BasicProjectElement) element;
			EclipseBaseProject base = (EclipseBaseProject) proj.getItem().getObject();
			EclipseSuite suite = (EclipseSuite) base.getSuiteReference().getObject();
			EclipseBaseProject main = suite.getMainProject();
			if (main != null && main.getname().equalsIgnoreCase(proj.getName())) {
				Font ufont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
				return ufont;
			}
		}
		
		// TODO Auto-generated method stub
		return null;
	}

}
