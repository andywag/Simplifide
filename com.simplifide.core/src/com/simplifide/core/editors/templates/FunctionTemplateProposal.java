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
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.swt.graphics.Image;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.baseeditor.template.GeneralTemplateProposal;
import com.simplifide.core.editors.hover.HoverFactory;

public class FunctionTemplateProposal extends GeneralTemplateProposal
{

	private ReferenceItem ref;
	private int languageType;
	
	public FunctionTemplateProposal(ReferenceItem ref, Template template, 
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
				return this.getTemplate().getName().toLowerCase().startsWith(ustr[ustr.length-1]);
			}
		} catch (BadLocationException e) {
			// concurrent modification - ignore
		}
		return false;
	}
	

	
	
}
