package com.simplifide.core.scalaext.context;

import com.simplifide.core.source.design.DesignFile;
import com.simplifide.scala2.context.DesignContext;

public class ScalaDesignContext extends DesignContext {

	private DesignFile design;
	public ScalaDesignContext(DesignFile design) {
		this.setDesign(design);
	}
	
	public String getLocation() {
		return this.getDesign().getUri().getPath();
	}
	
	public String getName() {
		return this.getDesign().getname();
	}

	public void setDesign(DesignFile design) {
		this.design = design;
	}

	public DesignFile getDesign() {
		return design;
	}

	
}
