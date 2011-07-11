/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.hyperlink;

import org.eclipse.jface.text.IRegion;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.core.baseeditor.hyperlink.GeneralHyperlink;
import com.simplifide.core.source.LocationOperations;

public class SourceHyperlink extends GeneralHyperlink{

	private ReferenceItem item;
	
	public SourceHyperlink(ReferenceItem item, IRegion region) {
		super(item.getLocation(),region);
		this.item = item;
	}
	
	
	public String getHyperlinkText() {
		String htext = ReferenceUtilities.returnTypeString(item.getType()) + " " + item.getname();
		if (item.getType() == ReferenceUtilities.REF_MODINSTANCE_CONNECT) {
			ModInstanceConnect con = (ModInstanceConnect)   item.getObject();
			htext += " (" + con.getEnclosingModuleReference().getname() + ")";
		}
		return htext;
	}

	public String getTypeLabel() {
		//return ReferenceUtilities.returnTypeString(item.getType());
		return null;
	}

	public void open() {
		
		if (item != null && item.getLocation() != null) {
  			LocationOperations.goToPosition(item.getLocation());
  		}
		  
		

		
		
		//this.dfile.goToHyperlink(this);
	}

	public void setItem(ReferenceItem item) {
		this.item = item;
	}

	public ReferenceItem getItem() {
		return item;
	}

}
