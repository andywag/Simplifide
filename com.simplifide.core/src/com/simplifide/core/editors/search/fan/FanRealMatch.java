package com.simplifide.core.editors.search.fan;

import org.eclipse.search.ui.text.Match;

import com.simplifide.base.core.hierarchy.PathTotalTreeElement;

public class FanRealMatch extends Match{

	private PathTotalTreeElement element;
	
	public FanRealMatch(PathTotalTreeElement element, int offset, int length) {
		super(element, offset, length);
		this.element = element;
	}

	public void setElement(PathTotalTreeElement element) {
		this.element = element;
	}

	public PathTotalTreeElement getElement() {
		return element;
	}

}
