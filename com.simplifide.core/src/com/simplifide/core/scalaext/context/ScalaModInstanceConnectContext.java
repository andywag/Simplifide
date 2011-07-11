package com.simplifide.core.scalaext.context;

import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.scala2.context.InstanceContext;
import com.simplifide.scala2.context.ModuleContext;

public class ScalaModInstanceConnectContext extends InstanceContext{

	private ModInstanceConnect connect;
	
	public ScalaModInstanceConnectContext(ModInstanceConnect connect) {
		this.connect = connect;
	}

	@Override
	public ModuleContext enclosing() {
		InstanceModule imod = this.connect.getEnclosingModuleReference().getObject();
		return new ScalaInstanceModuleContext(imod);
	}

	@Override
	public ModuleContext module() {
		if (this.connect.getEntityRef() != null) {
			EntityHolder holder = this.connect.getEntityRef().getObject();
			if (holder != null && holder.getInstanceModRef() != null) {
				InstanceModule imod = (InstanceModule) holder.getInstanceModRef().getObject();
				return new ScalaInstanceModuleContext(imod);
			}
		}
		return null;
	}
	
}
