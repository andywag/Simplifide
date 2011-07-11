package com.simplifide.core.editors.templates;

import java.util.Comparator;

import org.eclipse.jface.text.contentassist.ICompletionProposal;

import com.simplifide.base.sourcefile.antlr.parse.SearchContext;
import com.simplifide.core.editors.completion.CompletionProposalInt;
import com.simplifide.core.editors.completion.SourceTemplateProposal;

public class CompletionComparator implements Comparator<ICompletionProposal>{

	private SearchContext searchContext;
	public CompletionComparator(SearchContext context) {
		this.searchContext = context;
	}
	
	private boolean isTemplate(ICompletionProposal prop) {
		if (prop instanceof SourceTemplateProposal) return true;
		return false;
	}
	
	/** Put the completion items before the template items */
	public int compare(ICompletionProposal arg0, ICompletionProposal arg1) {
		boolean t0 = isTemplate(arg0);
		boolean t1 = isTemplate(arg1);
		
		if (!t1 && t0) {
			return 1;
		}
		else if (!t0 && t1) {
			return -1;
		}
		else if (!t0 && !t1) {
			if (arg0 instanceof CompletionProposalInt && arg1 instanceof CompletionProposalInt) {
				CompletionProposalInt prop1 = (CompletionProposalInt) arg0;
				CompletionProposalInt prop2 = (CompletionProposalInt) arg1;
				return searchContext.compare(prop1.getItem(), prop2.getItem());
			}
		}
		return arg0.getDisplayString().compareToIgnoreCase(arg1.getDisplayString());
	}

}
