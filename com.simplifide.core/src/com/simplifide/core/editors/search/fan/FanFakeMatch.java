package com.simplifide.core.editors.search.fan;

import org.eclipse.search.ui.text.Match;

import com.simplifide.base.core.hierarchy.PathTotalTreeElement;

public class FanFakeMatch extends Match{

	private  PathTotalTreeElement pathElement;
	
	public FanFakeMatch(PathTotalTreeElement pathElement) {
		super(pathElement, 1,1);
		this.setPathElement(pathElement);
	}

	

	
	public void setPathElement(PathTotalTreeElement pathElement) {
		this.pathElement = pathElement;
	}

	public PathTotalTreeElement getPathElement() {
		return pathElement;
	}


}
