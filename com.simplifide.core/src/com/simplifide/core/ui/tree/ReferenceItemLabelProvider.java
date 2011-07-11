/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.tree;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.resources.IconManager;

public class ReferenceItemLabelProvider extends LabelProvider implements IColorProvider, IFontProvider{

	public ReferenceItemLabelProvider() {}
	
	public Image getImage(Object element) {
		ReferenceItem ref = (ReferenceItem) element;
		return IconManager.getImage(ref.getType());
        
    }

 
    public String getText(Object element) {
        ReferenceItem item = (ReferenceItem) element;
        return item.getDisplayName();
    }

	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public Color getForeground(Object element) {
		ReferenceItem ref = (ReferenceItem) element;
		if (ref.getObject() instanceof ConnectorHolder.NotFound) {
			RGB red = new RGB(255,0,0);
			return new Color(null,red);
		}
		return null;
	}

	public Font getFont(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
