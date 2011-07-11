/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.actions.search;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.core.editors.SourceEditor;

public class ReferenceMenu extends MenuManager{

	private SourceEditor editor;
	private EditorFindItem findItem;

	public ReferenceMenu(IMenuManager mgr, SourceEditor editor, EditorFindItem findItem) {

		super("References");
		this.editor = editor;
		this.findItem = findItem;
		this.createActions();
		this.setVisible(true);
	}

	private ReferenceAction createReferenceAction(EditorFindItem findItem, SourceEditor editor,boolean global) {
		ReferenceAction action = new ReferenceAction(findItem.getItem().getname(),findItem,global);
		return action;
	}

	private void createActions() {
		
		if (findItem != null) {
			ReferenceItem item = findItem.getItem();

			if (ReferenceUtilities.checkType(item.getType(), ReferenceUtilities.REF_SYSTEMVAR) == 0 || 
				ReferenceUtilities.checkType(item.getType(), ReferenceUtilities.REF_PORT_TOP) == 0) {
				this.add(this.createReferenceAction(findItem, editor,true));
				this.add(new FanAction.In(this.findItem));
				this.add(new FanAction.Out(this.findItem));
			}
			else {
				this.add(this.createReferenceAction(findItem, editor,false));
			}
		}
	
	}




}
