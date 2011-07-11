/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/

package com.simplifide.core.editors.actions.search;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.base.sourcefile.antlr.parse.EditorFindItem;
import com.simplifide.base.sourcefile.util.EditorUtilities;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.resources.IconManager;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFileAlone;

public class GoToSubMenu extends MenuManager{

	private SourceEditor editor;
	private EditorFindItem findItem;
	
	public GoToSubMenu(IMenuManager mgr, SourceEditor editor, EditorFindItem findItem) {
		
		super("GoTo");
		this.editor = editor;
		this.findItem = findItem;
		this.createActions();
		this.setVisible(true);
	}
	
	private boolean aloneFile(){
		if (this.editor.getDesignFile() instanceof DesignFileAlone) return true;
		return false;
	}
	
	private void createActions() {
		List<EditorFindItem> items = EditorUtilities.getHyperlinkList(this.findItem);
		for (EditorFindItem item : items) {
			if (aloneFile()) {
				this.add(new GoToActionAlone(item.getItem(),this.editor));
			}
			else {
				this.add(new GoToAction(item.getItem()));
			}
			
		}
		
	}
	
	public static class GoToAction extends Action {
		
		protected ReferenceItem item;
		
		public GoToAction(ReferenceItem item) {
			this.setImageDescriptor(IconManager.getIcon(item.getType()));
			String initDec = item.getExtraGoToInformation();
			if (initDec.equalsIgnoreCase("")) {
				initDec = ReferenceUtilities.goToString(item);
			}
			
			this.setText(initDec);
			if (item.getLocation() == null) this.setEnabled(false);
			this.item = item;
			this.setEnabled(HardwareChecker.isSearchEnabled());
		}
		
		public void run() {
			if (item.getLocation() != null) {
				//SourceLocationHandler.getInstance().goToPosition(this.item.getLocation());
				LocationOperations.goToPosition(this.item.getLocation());
			}
		}	
	}
	
	public static class GoToActionAlone extends GoToAction {
		private SourceEditor editor;
		public GoToActionAlone(ReferenceItem item,SourceEditor editor) {
			super(item);
			this.editor = editor;
			this.setEnabled(HardwareChecker.isSearchEnabled());
		}
		
		public void run() {
			if (item != null) {
				if (item.getLocation() != null) {
					this.editor.goToPosition(item.getLocation().getDocPosition(),item.getLocation().getLength());
				}
			}
		}	
		
	}
	

}
