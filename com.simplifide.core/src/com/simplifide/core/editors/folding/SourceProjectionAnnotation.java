/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.folding;

import org.eclipse.jface.text.source.projection.ProjectionAnnotation;

import com.simplifide.base.sourcefile.antlr.node.TopASTNode;

public class SourceProjectionAnnotation extends ProjectionAnnotation{
	
	private TopASTNode node;
	
	public SourceProjectionAnnotation(TopASTNode node) {
		this.setNode(node);
	
	}

	public void setNode(TopASTNode node) {
		this.node = node;
	}

	public TopASTNode getNode() {
		return node;
	}
	
	
}
