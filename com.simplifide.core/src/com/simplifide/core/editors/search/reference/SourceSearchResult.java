/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.reference;

import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.search.TopSearchResult;

public class SourceSearchResult extends TopSearchResult {

	
	public SourceSearchResult(SourceSearchQuery query) {
		super(query);    
	}
	
	public String getLabel() {
		SourceSearchQuery qu = (SourceSearchQuery) this.getQuery();
		EditorFindItem item = qu.getFindItem();
		
	    return "References of " + item.getItem().getname();
	}

	

   

}
