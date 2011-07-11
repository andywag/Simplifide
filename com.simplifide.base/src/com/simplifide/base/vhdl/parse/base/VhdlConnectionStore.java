/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.base;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.sourcefile.antlr.stream.ConnectionStore;
import com.simplifide.base.sourcefile.antlr.stream.EntityStore;

public class VhdlConnectionStore extends ConnectionStore{

	public VhdlConnectionStore(SuperModule smod) {
		super(smod);
	}

	@Override
	public EntityStore createEntityStore(Entity entity) {
		return new VhdlEntityStore(entity);
	}

}
