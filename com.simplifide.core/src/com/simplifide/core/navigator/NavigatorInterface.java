/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.navigator;

import org.eclipse.swt.graphics.Image;

public interface NavigatorInterface {

	public String getName();
	public Object[] getChildren();
	public boolean hasChildren();
	public Image getImageDescriptor();
	
	public void setTreeProvider(NavigatorContentProvider treeProvider);
	public NavigatorContentProvider getTreeProvider();
	
}
