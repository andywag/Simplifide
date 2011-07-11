package com.simplifide.core.baseeditor.template;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension5;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Point;

public abstract class GeneralCompletionProposal implements ICompletionProposal,
	ICompletionProposalExtension3, 
	ICompletionProposalExtension5{

	
	public String getAdditionalProposalInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public IContextInformation getContextInformation() {
		// TODO Auto-generated method stub
		return null;
	}
	public Point getSelection(IDocument document) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getPrefixCompletionStart(IDocument document, int completionOffset) {
		// TODO Auto-generated method stub
		return 0;
	}

	public CharSequence getPrefixCompletionText(IDocument document, int completionOffset) {
		// TODO Auto-generated method stub
		return null;
	}
}
