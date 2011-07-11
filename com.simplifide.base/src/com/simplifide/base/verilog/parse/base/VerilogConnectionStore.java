/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.base;

import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.sourcefile.antlr.stream.ConnectionStore;
import com.simplifide.base.sourcefile.antlr.stream.EntityStore;

public class VerilogConnectionStore extends ConnectionStore{

	public VerilogConnectionStore() {}
	public VerilogConnectionStore(SuperModule smod) {
		super(smod);
	}

	
	
	@Override
	public EntityStore createEntityStore(Entity entity) {
		return new VerilogEntityStore(entity);
	}

}
