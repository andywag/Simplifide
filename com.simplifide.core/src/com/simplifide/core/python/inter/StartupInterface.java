package com.simplifide.core.python.inter;

import com.simplifide.core.python.context.SaveInterface;
import com.simplifide.core.python.inter.menu.MenuInterface;

public interface StartupInterface {

	public void initial();
	public PathProvider getPathProvider();
	public MenuProvider getMenuProvider();
	public SaveActionProvider[] getSaveActionProviders();
	
	interface PathProvider {
		public String[] getPaths();
	}
	
	interface MenuProvider {
		public MenuInterface[] getSuiteMenus();
		public MenuInterface[] getProjectMenus();
		public MenuInterface[] getEditorMenus();
	}
	
	interface SaveActionProvider {
		public void save(SaveInterface save);
		public void build(SaveInterface save);
		public void clean(SaveInterface save);
		public void expand(SaveInterface save);
	}
	
}
