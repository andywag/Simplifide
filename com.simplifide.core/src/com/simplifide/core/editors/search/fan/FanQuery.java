/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.search.fan;

import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.search.TopSearchQuery;
import com.simplifide.core.search.TopSearchResult;

public abstract class FanQuery extends TopSearchQuery{

	public FanQuery(EditorFindItem item) {
		super(item);
	}

	@Override
	public TopSearchResult createSearchResult() {
		return new FanSearchResult(this);
	}

	protected SystemVar getVariable() {
		EditorFindItem findItem = this.getFindItem();
		ReferenceItem ref = findItem.getItem();
		if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_SYSTEMVAR)==0) {
			return (SystemVar) ref.getObject();
		}
		else if (ReferenceUtilities.checkType(ref.getSearchType(), ReferenceUtilities.REF_PORT_TOP)==0) {
			PortTop port = (PortTop) ref.getObject();
			return port.getLocalVar();
		}
		return null;
	}
	

}
