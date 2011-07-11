package com.simplifide.core.vhdl.editor.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.handlers.HandlerUtil;

import com.simplifide.core.HardwareLog;

public class TestAction extends Action{
	
	public TestAction() {
		this.setEnabled(true);
	}
	
	public void run() {
		HardwareLog.logInfo("Test Action");
	}
	
	public static class Delegate extends ActionDelegate implements IEditorActionDelegate {

		public Delegate() {}
		
		public void run(IAction action) {
			HardwareLog.logInfo("Test Test Test");
		}
		 
		public void setActiveEditor(IAction action, IEditorPart targetEditor) {
			HardwareLog.logInfo("Setting editor");
		}
		
		   public void runWithEvent(IAction action, Event event) {
				HardwareLog.logInfo("Test Test Test");
		    }
		
	}

	public static class Handler extends AbstractHandler {

		 
		public Object execute(ExecutionEvent event) throws ExecutionException {
			
			return null;
		}
		
		public boolean isHandled() {return true;}
		public boolean isEnabled() {return true;}
		
	}
	
}
