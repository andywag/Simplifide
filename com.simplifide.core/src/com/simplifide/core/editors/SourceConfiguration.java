/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors;



import java.util.Arrays;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.GeneralConfiguration;
import com.simplifide.core.baseeditor.GeneralEditor;
import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourcePartitionScanner;
import com.simplifide.core.baseeditor.hover.SourceInformationControlCreator;
import com.simplifide.core.baseeditor.hyperlink.GeneralHyperlinkDetector;
import com.simplifide.core.editors.format.SourceContentFormattingStrategy;
import com.simplifide.core.editors.hyperlink.SourceHyperlinkDetector;
//import com.simplifide.core.scripteditor.ScriptScanner;
//import com.simplifide.core.scripteditor.hover.ScriptTextHover;
//import com.simplifide.core.scripteditor.templates.ScriptContentProcessor;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class SourceConfiguration extends GeneralConfiguration {
	
	

	public SourceConfiguration(ColorManager colorManager, SourceEditor editor) {
 		super(colorManager,editor);
	}
	
	public abstract SourceContentFormattingStrategy getFormattingStrategy();

	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		
		
		ContentAssistant assistant = (ContentAssistant) super.getContentAssistant(sourceViewer);
		//assistant.setContentAssistProcessor(new ScriptContentProcessor(this.getEditor()),
		//		SourcePartitionScanner.SCRIPT_COMMENT);
		assistant.setInformationControlCreator(new SourceInformationControlCreator(null));
		
		return assistant;
	}
	
	public IContentFormatter getContentFormatter(ISourceViewer viewer) {
		MultiPassContentFormatter formatter = new MultiPassContentFormatter(getConfiguredDocumentPartitioning(viewer),IDocument.DEFAULT_CONTENT_TYPE);
		formatter.setMasterStrategy(this.getFormattingStrategy());
		return formatter;
	}
	
	
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new DefaultAnnotationHover();
	}
	
	
	public String[] getIndentPrefixes(ISourceViewer viewer, String str) {
		
		int indentWidth = CoreActivator.getDefault().getPreferenceStore().getInt(PreferenceConstants.INDENT_LENGTH);
		boolean tabs = CoreActivator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.INDENT_TAB);
		if (tabs) {
			return new String[] {"\t"};
		}
		else {
			char[] spaces= new char[indentWidth];
			Arrays.fill(spaces, ' ');
			return new String[] { new String(spaces), "" }; //$NON-NLS-1$
		}
	}
	

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			SourcePartitionScanner.SCRIPT_COMMENT,
			SourcePartitionScanner.MULTI_COMMENT,
			SourcePartitionScanner.MULTI_DOC};
	}
	
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getSourceScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		
		
		//dr= new DefaultDamagerRepairer(new ScriptScanner(this.getColorManager()));
		//reconciler.setDamager(dr, SourcePartitionScanner.SCRIPT_COMMENT);
		//reconciler.setRepairer(dr, SourcePartitionScanner.SCRIPT_COMMENT);
	
		dr= new DefaultDamagerRepairer(new CommentScanner.Comment(this.getColorManager()));
		reconciler.setDamager(dr, SourcePartitionScanner.MULTI_COMMENT);
		reconciler.setRepairer(dr, SourcePartitionScanner.MULTI_COMMENT);
		
		dr= new DefaultDamagerRepairer(new CommentScanner.Doc(this.getColorManager()));
		reconciler.setDamager(dr, SourcePartitionScanner.MULTI_DOC);
		reconciler.setRepairer(dr, SourcePartitionScanner.MULTI_DOC);
		
		return reconciler;
	}
	

	
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType, int stateMask) {
		if (contentType == SourcePartitionScanner.SCRIPT_COMMENT) {
			//return new ScriptTextHover(this.getEditor());
		}
		return null;
	}
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		if (contentType == SourcePartitionScanner.SCRIPT_COMMENT) {
			//return new ScriptTextHover(this.getEditor());
		}
		return null;
	}

	
	
	
	
	protected GeneralHyperlinkDetector createHyperlinkDetector(GeneralEditor editor) {
		return new SourceHyperlinkDetector(editor);
	}
	
	
	


	
	

	
	
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		return new SourceDoubleClickStrategy(this.getSourceEditor());
	}

	

	
	public SourceEditor getSourceEditor() {
		return (SourceEditor) this.getEditor();
	}
	

}
