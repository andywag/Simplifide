package com.simplifide.base.core.project.define;

import java.util.List;

import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.port.PortTop;

public class DefineMacro extends DefineObject {

	private FunctionDeclaration function;
	
	public DefineMacro(String name,FunctionDeclaration function) {
		super(name);
		this.function = function;
	}
	
	public DefineCall getDefineCall(DefineObject proto) {
		return new DefineCall.Macro(this, proto);
		
	}
	
	public List<PortTop> getPorts() {
		return this.function.getOrderedList();
	}
	

	public String getTemplatePattern() {
		return "`" + this.function.getTemplatePattern();
	}
	
	public String getDisplayName() {
		return function.getDisplayName();
	}
	
	public void setFunction(FunctionDeclaration function) {
		this.function = function;
	}

	public FunctionDeclaration getFunction() {
		return function;
	}	
	
	

}
