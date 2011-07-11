package com.simplifide.core.baseeditor.hyperlink;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.source.LocationOperations;

public abstract class GeneralHyperlink implements IHyperlink{

	private IRegion region;
	private ReferenceLocation location;
	
	public GeneralHyperlink(IRegion region) {
		this(null,region);
	}
	public GeneralHyperlink(ReferenceLocation location, IRegion region) {
		this.region = region;
		this.location = location;
	}
	
	public IRegion getHyperlinkRegion() {
		return this.region;
	}

	public void open() {
		if (location != null) LocationOperations.goToPosition(location);
	}

}
