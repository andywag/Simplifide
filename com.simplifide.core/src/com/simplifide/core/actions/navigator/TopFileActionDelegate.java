package com.simplifide.core.actions.navigator;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.internal.ViewPluginAction;

import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.source.LocationOperations;

public abstract class TopFileActionDelegate extends ActionDelegate implements IViewActionDelegate{
 
	
	protected void createCheckMark(IAction action) {}
	
	protected IResource getResource(IAction action) {
		
		if (action instanceof ViewPluginAction) {
			 ViewPluginAction act = (ViewPluginAction) action;
			 ISelection select = act.getSelection();
			 if (select instanceof TreeSelection) {
				 TreeSelection tree = (TreeSelection) select;
				 Object obj = tree.getFirstElement();
				 if (obj instanceof IResource) {
					 IResource ufile = (IResource) obj;
					 return ufile;
				 }
			 }
		 }
		 return null;
	}
	
	protected SourceFile getSourceFile(IAction action) {
		IResource res = getResource(action);
		if (res != null) {
			SourceFile sfile = LocationOperations.getSourceFile(res.getLocationURI());
			return sfile;
		}
		return null;
	}
	
	
	  public void init(IAction action) {
		  this.createCheckMark(action);
	    }
	 public void selectionChanged(IAction action, ISelection selection) {
		 this.createCheckMark(action);
	 }

	 
	
	public void init(IViewPart view) {
	}
}
