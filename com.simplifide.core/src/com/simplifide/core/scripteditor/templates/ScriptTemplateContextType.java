package com.simplifide.core.scripteditor.templates;

import java.util.List;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariable;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.template.GeneralTemplateContextType;
import com.simplifide.core.baseeditor.template.GeneralTemplateVariableResolver;
import com.simplifide.scala2.command.Command;
import com.simplifide.scala2.command.CommandParameter;

public class ScriptTemplateContextType extends GeneralTemplateContextType{

	public static String SCRIPT_ID = CoreActivator.PLUGIN_ID + ".script.context.type";
	
	private Command activeCommand;
	
	public ScriptTemplateContextType() {}
	
	public void resolve(TemplateVariable variable, TemplateContext context) {
		if (activeCommand == null) {
			super.resolve(variable, context);
			return;
		}
		CommandParameter param = (CommandParameter) activeCommand.getJavaCommandMap().get(variable.getName());
		if (param == null) {
			super.resolve(variable, context);
			return;
		}
		List choices = param.getJavaChoices();
		if (choices != null) {
			variable.setValues((String[]) choices.toArray(new String[choices.size()]));
		}
		else  super.resolve(variable,context);
	}
	
	/** I don't believe this is being used anywhere */
	public static class Resolver extends GeneralTemplateVariableResolver{

		
		public void resolve(TemplateVariable variable, TemplateContext context) {
			
			if (variable.getName().equals("clkedge")) {
				variable.setValues(new String[] {"posedge","negedge"});
				variable.setResolved(true);
			}
			else super.resolve(variable,context);
		}
	}

	public Command getActiveCommand() {
		return this.activeCommand;
	}
	
	public void setActiveCommand(Command active) {
		this.activeCommand = active;
	}
	
}
