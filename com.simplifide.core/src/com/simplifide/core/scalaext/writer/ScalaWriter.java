package com.simplifide.core.scalaext.writer;


import com.simplifide.core.refactor.InstanceWriter;
import com.simplifide.core.scalaext.context.ScalaInstanceModuleContext;
import com.simplifide.scala2.context.ModuleContext;
import com.simplifide.scala2.top.WriterInterface;

public class ScalaWriter extends WriterInterface{

	
	private ScalaWriter instance;
	
	public ScalaWriter getInstance() {
		if (instance == null) instance = new ScalaWriter();
		return instance;
	}
	
	public String createComponent(ModuleContext context) {
		ScalaInstanceModuleContext mod = (ScalaInstanceModuleContext) context;
 		return InstanceWriter.createComponent(true, mod.getModule());
	}

	
	public String createInstance(ModuleContext context, java.util.HashMap map) {
		ScalaInstanceModuleContext mod = (ScalaInstanceModuleContext) context;
 		return InstanceWriter.createInstance(true, mod.getModule(),map);
	}

}
