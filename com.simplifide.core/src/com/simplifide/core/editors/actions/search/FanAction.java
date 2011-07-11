/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.actions.search;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.editors.search.fan.FanInQuery;
import com.simplifide.core.editors.search.fan.FanOutQuery;
import com.simplifide.core.search.TopSearchQuery;

public abstract class FanAction extends SearchAction{

	
	
	public FanAction(String actionName, EditorFindItem item) {
		super(actionName, item);
		this.setEnabled(HardwareChecker.isRefactoringEnabled());
	}

	public static class In extends FanAction {

		public In(EditorFindItem itemList) {
			super("Path To Signal", itemList);
		}

		@Override
		public TopSearchQuery getSearchQuery(EditorFindItem itemList) {
			return new FanInQuery(itemList);
		}
		
	}
	
	public static class Out extends FanAction {

		public Out( EditorFindItem itemList) {
			super("Path From Signal", itemList);
		}
		
		@Override
		public TopSearchQuery getSearchQuery(EditorFindItem itemList) {
			return new FanOutQuery(itemList);
		}
		
	}
	
}
