package com.simplifide.core.baseeditor.hover;

import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.information.IInformationProviderExtension2;

import com.simplifide.core.baseeditor.GeneralEditor;

public abstract class GeneralTextHover implements ITextHover, ITextHoverExtension, IInformationProviderExtension2{

	private GeneralEditor editor;

	public GeneralTextHover(GeneralEditor editor) {
		this.editor = editor;
	}
	
	public void setEditor(GeneralEditor editor) {
		this.editor = editor;
	}

	public GeneralEditor getEditor() {
		return editor;
	}

	 public IInformationControlCreator getHoverControlCreator() {
	        return new SourceInformationControlCreator(this.getEditor());
	    }
	    
	    public IInformationControlCreator getInformationPresenterControlCreator() {
	    	return new SourceInformationControlCreator(this.getEditor());
	    }

	
	
	
}
