/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor;


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
import com.simplifide.core.verilog.editor.indent.VerilogAutoEditStrategy;
import com.simplifide.core.verilog.editor.indent.VerilogFormattingStrategy;
import com.simplifide.core.verilog.editor.indent.VerilogIndentStrategy;
import com.simplifide.core.verilog.editor.templates.VerilogTemplateProcessor;

public class VerilogConfiguration extends SourceConfiguration{

	public VerilogConfiguration(ColorManager colorManager, SourceEditor editor) {
		super(colorManager,editor);
	}

	@Override
	protected SourceScanner createSourceScanner(ColorManager manager) {
		return new VerilogScanner(manager);
	}

	public IReconciler getReconciler(ISourceViewer sourceViewer) {

		MonoReconciler reconcile = new MonoReconciler(new VerilogReconcileStrategy(this.getSourceEditor()),false);
		return reconcile;

	}

	@Override
	protected GeneralContentProcessor createTemplateProcessor(GeneralEditor editor) {
		return new VerilogTemplateProcessor(editor);
	}

	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, 
			String contentType) {
		if (contentType == IDocument.DEFAULT_CONTENT_TYPE) {
			return new IAutoEditStrategy[] {new VerilogAutoEditStrategy(),new VerilogIndentStrategy()};
    	}
    	//else if (contentType.equals(SourcePartitionScanner.SCRIPT_COMMENT)) {
    	//	return new IAutoEditStrategy[] {new ScriptAutoEditStrategy.Verilog()};
    	//}
    	return super.getAutoEditStrategies(sourceViewer, contentType);
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
		if (contentType.equals(IDocument.DEFAULT_CONTENT_TYPE)) {
			return new VerilogHover(this.getSourceEditor());
		}
		return super.getTextHover(sourceViewer, contentType,stateMask);
	}
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		if (contentType.equals(IDocument.DEFAULT_CONTENT_TYPE)) {
			return new VerilogHover(this.getSourceEditor());
		}
		return super.getTextHover(sourceViewer, contentType);
	}

	@Override
	public SourceContentFormattingStrategy getFormattingStrategy() {
		return new VerilogFormattingStrategy(this.getSourceEditor());
	}

	
	
}




