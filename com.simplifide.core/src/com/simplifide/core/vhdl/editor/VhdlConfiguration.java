/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.editor;


import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.source.ISourceViewer;

import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourcePartitionScanner;
import com.simplifide.core.baseeditor.color.SourceScanner;
import com.simplifide.core.baseeditor.template.GeneralContentProcessor;
import com.simplifide.core.editors.SourceConfiguration;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.editors.format.SourceContentFormattingStrategy;
import com.simplifide.core.editors.indent.SourceAutoEditStrategy;
import com.simplifide.core.scripteditor.format.ScriptAutoEditStrategy;
import com.simplifide.core.vhdl.editor.indent.VhdlFormattingStrategy;
import com.simplifide.core.vhdl.editor.indent.VhdlIndentStrategy;
import com.simplifide.core.vhdl.editor.templates.VhdlTemplateProcessor;

public class VhdlConfiguration extends SourceConfiguration {

    public VhdlConfiguration(ColorManager colorManager, SourceEditor editor) {
        super(colorManager,editor);
    }

    @Override
    protected SourceScanner createSourceScanner(ColorManager manager) {
        return new VhdlScanner(manager);
    }
    public IReconciler getReconciler(ISourceViewer sourceViewer) {
        MonoReconciler reconcile = new MonoReconciler(new VhdlReconcileStrategy(this.getSourceEditor()),false);
        return reconcile;
    }

    public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, 
            String contentType) {
    	if (contentType == IDocument.DEFAULT_CONTENT_TYPE) {
    		return new IAutoEditStrategy[] {new SourceAutoEditStrategy(),new VhdlIndentStrategy()};
    	}
    	else if (contentType == SourcePartitionScanner.SCRIPT_COMMENT) {
    		return new IAutoEditStrategy[] {new ScriptAutoEditStrategy.Vhdl()};
    	}
    	return super.getAutoEditStrategies(sourceViewer, contentType);

    }

    @Override
    protected GeneralContentProcessor createTemplateProcessor(GeneralEditor editor) {
        return new VhdlTemplateProcessor(editor);
    }
    
    @Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
    	if (contentType.equals(IDocument.DEFAULT_CONTENT_TYPE)) {
			return new VhdlHover(this.getSourceEditor());
		}
		return super.getTextHover(sourceViewer, contentType,stateMask);
	}
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		if (contentType.equals(IDocument.DEFAULT_CONTENT_TYPE)) {
			return new VhdlHover(this.getSourceEditor());
		}
		return super.getTextHover(sourceViewer, contentType);
	}
	
	

	@Override
	public SourceContentFormattingStrategy getFormattingStrategy() {
		return new VhdlFormattingStrategy(this.getSourceEditor());
	}


}
