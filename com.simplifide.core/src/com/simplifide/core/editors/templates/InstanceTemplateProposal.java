/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.templates;


import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.refactor.model.ModInstanceConnectWrap;
import com.simplifide.core.baseeditor.template.GenerateTemplateContext;
import com.simplifide.core.editors.completion.NewReplaceValue;
import com.simplifide.core.freemarker.TemplateGenerator;

public class InstanceTemplateProposal extends TemplateProposal{


	
	public static InstanceTemplateProposal createInstanceProposal(InstanceModule module,
			ITextViewer viewer,  NewReplaceValue repval,TemplateContext context, boolean vhdl) {
		
		GenerateTemplateContext docContext = new GenerateTemplateContext(context.getContextType(), viewer.getDocument(), repval.getSpos(), repval.getLength());

		
		ModInstanceConnect connect = module.createDefaultConnection();
    	ModInstanceConnectWrap wrap = new ModInstanceConnectWrap(connect,vhdl);
    	String cfile = "refactor/verilog/instance_ent";
    	if (vhdl) cfile = "refactor/vhdl/instance_ent";
    	String temp = TemplateGenerator.generateTemplate(cfile, wrap);
    	temp = temp.replace("XXXX", "${");
    	temp = temp.replace("YYYY", "}");
    	Template template = new Template(module.getname(), "", context.getContextType().getId(), temp, true);

    	
    	return new InstanceTemplateProposal(template,context,new Region(repval.getSpos(),repval.getLength()),null);
	}
	
	public InstanceTemplateProposal(Template template, TemplateContext context, IRegion region, Image image) {
		super(template,context,region,image);
		
	}
	
	public boolean validate(IDocument document, int offset, DocumentEvent event) {
		try {
			int replaceOffset= getReplaceOffset();
			if (offset >= replaceOffset) {
				String content= document.get(replaceOffset, offset - replaceOffset);
				String[] ustr = content.split("\\.");
				return this.getTemplate().getName().toLowerCase().startsWith(ustr[ustr.length-1]);
			}
		} catch (BadLocationException e) {
			// concurrent modification - ignore
		}
		return false;
	}

	
}
