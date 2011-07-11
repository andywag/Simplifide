/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ui.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

public class ASTNodeTreeProvider extends TopTreeProvider{

	public ASTNodeTreeProvider() {}

	public Object[] getChildren(Object parentElement) {
		TopASTNode node = (TopASTNode) parentElement;
		Object[] uobj = new Object[node.getNumberOfChildren()];
		TopASTNode child = node.getFirstASTChild();
		int i = 0;
		while (child != null) {
			uobj[i] = child;
			child = child.getNextASTSibling();
			i++;
		}
		return uobj;
	}


	public boolean hasChildren(Object element) {
		TopASTNode node = (TopASTNode) element;
		if (node.getNumberOfChildren() > 0) return true;
		return false;
	}

	public static class Label extends LabelProvider {

		public Image getImage(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getText(Object element) {
			TopASTNode node = (TopASTNode) element;
			if (node.getText() == null) return "";
			return node.getText();
		}


	}
	
	
	
}
