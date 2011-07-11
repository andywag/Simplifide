package com.simplifide.core.actions.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.internal.ViewPluginAction;

import com.simplifide.core.HardwareLog;

public class MainFileActionDelegate extends ActionDelegate implements IViewActionDelegate{

	 public void run(IAction action) {
		 
		 if (action instanceof ViewPluginAction) {
			 ViewPluginAction act = (ViewPluginAction) action;
			 ISelection select = act.getSelection();
			 if (select instanceof TreeSelection) {
				 TreeSelection tree = (TreeSelection) select;
				 Object obj = tree.getFirstElement();
				 if (obj instanceof IFile) {
					 IFile ufile = (IFile) obj;
					
				 }
				 HardwareLog.logInfo("");
			 }
			 
		 }
		 HardwareLog.logInfo("Message");
	 }

	  public void init(IAction action) {
	  }
	 
	
	public void init(IViewPart view) {
		
		
	}
	
}
