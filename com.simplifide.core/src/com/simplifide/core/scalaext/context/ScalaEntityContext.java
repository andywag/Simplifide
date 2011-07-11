package com.simplifide.core.scalaext.context;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.scala2.context.EntityContext;

public class ScalaEntityContext extends EntityContext{

	
	private Entity entity;
	
	public ScalaEntityContext(Entity entity) {
		super(entity.getname());
		this.entity = entity;
	}
	
}
