/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.actions.search;

import org.eclipse.jface.action.Action;
import org.eclipse.search.ui.NewSearchUI;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.search.TopSearchQuery;

public abstract class SearchAction extends Action{

	private EditorFindItem editorFindItem;
	
	public SearchAction(String actionName, EditorFindItem findItem) {
		this.setText(actionName);
		this.editorFindItem = findItem;
		this.setEnabled(HardwareChecker.isRefactoringEnabled());
	}

	public abstract TopSearchQuery getSearchQuery(EditorFindItem findItem);
	
	public void run() {
		
		//ISearchResultViewPart searchPart = NewSearchUI.activateSearchResultView();
		TopSearchQuery query = this.getSearchQuery(this.editorFindItem);
		NewSearchUI.runQueryInBackground(query, null);
	}

	public void setEditorFindItem(EditorFindItem editorFindItem) {
		this.editorFindItem = editorFindItem;
	}

	public EditorFindItem getEditorFindItem() {
		return editorFindItem;
	}
	
}
