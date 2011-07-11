package com.simplifide.core.scripteditor.templates;

import com.simplifide.base.api.template.TemplateCompletionInterface;
import com.simplifide.scala2.command.*;

public class CommandTemplateWrapper implements TemplateCompletionInterface{

	protected Command command;
	
	public CommandTemplateWrapper(Command command) {
		this.command = command;
	}
	
	public String getTemplateDescription() {
		return this.command.getDescription();
	}

	public String getTemplateDisplayName() {
		return this.command.getName();
	}

	public String getTemplatePattern() {
		return this.command.getCommand();
	}

	public boolean isAutoComplete() {
		return false;
	}

	public static class Vhdl extends CommandTemplateWrapper {
		public Vhdl(Command command) {super(command);}
		
		public String getTemplatePattern() {
			String com =  this.command.getCommand();
			return com.replace("\n", "\n-- ");
		}
		
	}
	

}
