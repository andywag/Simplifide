/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.actions.search;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.editors.search.reference.SourceSearchQuery;
import com.simplifide.core.resources.IconManager;
import com.simplifide.core.search.TopSearchQuery;

public class ReferenceAction extends SearchAction{

	private boolean global;

	public ReferenceAction(String action, EditorFindItem item, boolean global) {
		super(action,item);
		ReferenceItem uitem = item.getItem();
		this.setImageDescriptor(IconManager.getIcon(uitem.getType()));
		this.global = global;
		String text = ReferenceUtilities.goToString(uitem);
		if (global) text = "Global " + text;
		this.setText(text);
		
	}


	@Override
	public TopSearchQuery getSearchQuery(EditorFindItem item) {
		SourceSearchQuery query = new SourceSearchQuery(item,global);
		return query;
	}



}
