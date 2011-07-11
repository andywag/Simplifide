/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.completion;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.core.editors.hover.HoverFactory;

public class SourceTemplateProposal extends TemplateProposal{

	private ModuleObject obj;
	
	public SourceTemplateProposal(ModuleObject obj, Template template, TemplateContext context,
			IRegion region, Image image) {
		super(template, context, region, image);
		this.obj = obj;
	}
	 
	public String getAdditionalProposalInfo() {
		if (obj != null) {
			return HoverFactory.getTextHover(obj, 0);
		}
		return super.getAdditionalProposalInfo();
	}

}
