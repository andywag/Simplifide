package com.simplifide.core.python;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;

import com.simplifide.base.license.info.HardwareChecker;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.python.context.ContextInterface;
import com.simplifide.core.python.inter.StartupInterface;
import com.simplifide.core.python.inter.menu.MenuInterface;
import com.simplifide.core.python.inter.menu.MenuItemInterface;

public class MenuLoader {

	
	public static class PythonAction extends Action {
		private MenuItemInterface inter;
		private ContextInterface context;
		public PythonAction(MenuItemInterface inter,ContextInterface context) {
			this.inter = inter;
			this.context = context;
			this.setText(inter.getDisplayName());
			this.setToolTipText(inter.getDisplayDescription());
		}
		
		public void run() {
			String display = inter.getDisplayName();
			Job ujob = new Job(display) {

				protected IStatus run(IProgressMonitor monitor) {
					inter.run(context);
					return Status.OK_STATUS;
				}
			};
			ujob.schedule();
		}
	}
	
	public static void createSuiteActions2(IMenuManager manager, ContextInterface context) {
		try {
			if (!HardwareChecker.isPythonEnabled()) return;
			StartupInterface startup = PythonStartup.getDefault().getStartup();
			if (startup == null) return;
			StartupInterface.MenuProvider menuprov = startup.getMenuProvider();
			if (menuprov == null) return;
			
			MenuInterface[] menus = menuprov.getSuiteMenus();
			for (MenuInterface menu : menus) {
				MenuManager nman = new MenuManager(menu.getDisplayName());
				for (MenuItemInterface item : menu.getMenuItems()) {
					PythonAction act = new PythonAction(item,context);
					nman.add(act);
				}
				manager.add(nman);
			}	
		}
		catch (Exception e) {
			HardwareLog.logError(e);
		}
		
	}
	
	public static void createProjectActions2(IMenuManager manager, ContextInterface context) {
		if (!HardwareChecker.isPythonEnabled()) return;
		StartupInterface startup = PythonStartup.getDefault().getStartup();
		if (startup == null) return;
		StartupInterface.MenuProvider menuprov = startup.getMenuProvider();
		if (menuprov == null) return;
		
		MenuInterface[] menus = menuprov.getProjectMenus();
		for (MenuInterface menu : menus) {
			MenuManager nman = new MenuManager(menu.getDisplayName());
			for (MenuItemInterface item : menu.getMenuItems()) {
				PythonAction act = new PythonAction(item,context);
				nman.add(act);
			}
			manager.add(nman);
		}	
	}
	
	public static void createEditorActions2(IMenuManager manager, ContextInterface context) {
		if (!HardwareChecker.isPythonEnabled()) return;
		StartupInterface startup = PythonStartup.getDefault().getStartup();
		if (startup == null) return;
		StartupInterface.MenuProvider menuprov = startup.getMenuProvider();
		if (menuprov == null) return;
		
		MenuInterface[] menus = menuprov.getEditorMenus();
		if (menus.length > 0) {
			MenuManager manager2 = new MenuManager("Python");
			manager.add(manager2);
			for (MenuInterface menu : menus) {
				MenuManager nman = new MenuManager(menu.getDisplayName());
				for (MenuItemInterface item : menu.getMenuItems()) {
					PythonAction act = new PythonAction(item,context);
					nman.add(act);
				}
				manager2.add(nman);
			}
		}
		
		
		
		
	}
	
	
	
}
