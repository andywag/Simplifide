package com.simplifide.core.actions.navigator;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionDelegate;

import com.simplifide.core.HardwareLog;

public class MakeTestFileActionDelegate extends ActionDelegate implements IViewActionDelegate{

	 public void run(IAction action) {
		 HardwareLog.logInfo("Message");
	 }

	
	public void init(IViewPart view) {
		// TODO Auto-generated method stub
		
	}
	
}
