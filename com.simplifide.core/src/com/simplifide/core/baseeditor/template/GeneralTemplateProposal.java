package com.simplifide.core.baseeditor.template;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public class GeneralTemplateProposal extends TemplateProposal implements ICompletionProposalExtension,
		ICompletionProposalExtension3,
		ICompletionProposalExtension5,
		ICompletionProposalExtension6{
	
	

	public GeneralTemplateProposal(Template template, TemplateContext context,
			IRegion region, Image image) {
		super(template, context, region, image);
	}

	public StyledString getStyledDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAdditionalProposalInfo(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
