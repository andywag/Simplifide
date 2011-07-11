package com.simplifide.core.baseeditor.template;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import com.simplifide.core.baseeditor.GeneralEditor;

public class GeneralContentProcessor implements IContentAssistProcessor{

	private GeneralEditor editor;
	
	public GeneralContentProcessor(GeneralEditor editor) {
		this.setEditor(editor);
	}
	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		
		return null;
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEditor(GeneralEditor editor) {
		this.editor = editor;
	}

	public GeneralEditor getEditor() {
		return editor;
	}

}
