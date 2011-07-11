package com.simplifide.core.scripteditor.templates;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.swt.graphics.Image;

import com.simplifide.core.baseeditor.template.GeneralTemplate;
import com.simplifide.core.baseeditor.template.GeneralTemplateProposal;
import com.simplifide.core.resources.BasicIconManager;
import com.simplifide.scala2.command.Command;

public class ScriptTemplateProposal extends GeneralTemplateProposal{

	private Command command;
	public ScriptTemplateProposal(Command command, Template template, TemplateContext context,
			IRegion region, Image image) {
		super(template, context, region, image);
		this.command = command;
		
	}
	
	
	
	public static ScriptTemplateProposal createProposal(Command command, IRegion region, 
			TemplateContext context, boolean vhdl) {
		CommandTemplateWrapper wrap = new CommandTemplateWrapper(command);
		if (vhdl) wrap = new CommandTemplateWrapper.Vhdl(command);
		
		GeneralTemplate template = new GeneralTemplate(wrap,ScriptTemplateContextType.SCRIPT_ID);
		ScriptTemplateProposal com = new ScriptTemplateProposal(command, template, 
				context, region, null);
		
		return com;
	}
	
	public void apply(ITextViewer viewer, char trigger, int stateMask, int offset) {
		ScriptTemplateContextType ucont = (ScriptTemplateContextType) this.getContext().getContextType();
		ucont.setActiveCommand(this.command);
		super.apply(viewer, trigger, stateMask, offset);
		
	}
	
	public Image getImage() {
		return BasicIconManager.getRealImage(BasicIconManager.MAIN_ASSIGN);
	}
	
	

}



