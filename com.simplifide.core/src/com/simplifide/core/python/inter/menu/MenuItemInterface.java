package com.simplifide.core.python.inter.menu;

import com.simplifide.core.python.context.ContextInterface;

public interface MenuItemInterface {

	public String getName();
	public String getDisplayName();
	public String getDisplayDescription();
	public void run(ContextInterface context);
	
}
