/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.fan;

import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorPart;

import com.simplifide.core.search.TopSearchResult;

public class FanSearchResult extends TopSearchResult{

	public FanSearchResult(ISearchQuery query) {
		super(query);
	}

	@Override
	public String getLabel() {
		return "FanIn";
	}

	public boolean isShownInEditor(Match match, IEditorPart editor) {
		return false;
	}
	
	
}
