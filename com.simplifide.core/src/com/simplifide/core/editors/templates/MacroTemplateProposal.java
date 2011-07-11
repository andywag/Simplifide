/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors.templates;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.editors.completion.CompletionProposalInt;
import com.simplifide.core.editors.hover.HoverFactory;

public class MacroTemplateProposal extends TemplateProposal implements CompletionProposalInt,
																		  ICompletionProposal,
																		  ICompletionProposalExtension3, 
																		  ICompletionProposalExtension5
{

	private ReferenceItem ref;
	private int languageType;
	
	public MacroTemplateProposal(ReferenceItem ref, Template template, 
			TemplateContext context, IRegion region, Image image, int languageType) {
		super(template, context, region, image);
		this.ref = ref;
		this.languageType = languageType;
	}
	
	public ReferenceItem getItem() {
		return ref;
	}
	
	public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
		if (ref != null && ref.getObject() != null) {
			return HoverFactory.getTextHover(ref.getObject(), languageType);

		}
		return null;
	}
	
	public boolean validate(IDocument document, int offset, DocumentEvent event) {
		try {
			int replaceOffset= getReplaceOffset();
			if (offset >= replaceOffset) {
				String content= document.get(replaceOffset, offset - replaceOffset);
				String[] ustr = content.split("\\.");
				String retName = this.getTemplate().getName();
				String compName = ustr[ustr.length-1].substring(1);
				boolean ret =  retName.toLowerCase().startsWith(compName.toLowerCase());
				
				return ret;
			}
		} catch (BadLocationException e) {
			// concurrent modification - ignore
		}
		return false;
	}
	

	
	
}
