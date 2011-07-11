package com.simplifide.core.scalaext.context;

import com.simplifide.base.core.instance.Component;
import com.simplifide.scala2.context.ComponentContext;

public class ScalaComponentContext extends ComponentContext{

	private Component component;
	
	public ScalaComponentContext(Component component) {
		super(component.getname());
		this.component = component;
	}
	
}
