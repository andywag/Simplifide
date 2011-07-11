/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ucf;



import java.util.Arrays;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.MultipleHyperlinkPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourceScanner;
import com.simplifide.core.editors.color.SourceColorConstants;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class UcfConfiguration extends TextSourceViewerConfiguration {
	
	private SourceScanner scanner;
	private ColorManager colorManager;
	private UcfEditor editor;

	public UcfConfiguration(ColorManager colorManager, UcfEditor editor) {
		super(EditorsUI.getPreferenceStore());
		this.setEditor(editor);
		this.colorManager = colorManager;		
		
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
	

	

	public IHyperlinkPresenter getHyperlinkPresenter(ISourceViewer sourceViewer) {
		return new MultipleHyperlinkPresenter(CoreActivator.getDefault().getPreferenceStore());
	}
	
	/*
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (sourceViewer == null)
			return null;
		IHyperlinkPresenter pres = this.getHyperlinkPresenter(sourceViewer);
		if (pres instanceof DefaultHyperlinkPresenter) {
			DefaultHyperlinkPresenter def = (DefaultHyperlinkPresenter) pres;
			Color col = new Color(sourceViewer.getTextWidget().getDisplay(),new RGB(0,0,128));
			//def.setColor(col);
			//sourceViewer.getTextWidget().getDisplay().
		}
		return new IHyperlinkDetector[] { new SourceHyperlinkDetector(this.editor) };
	}*/
	
	/*
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		
		ContentAssistant assistant = new ContentAssistant();
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
		
		
		assistant.enableAutoInsert(store.getBoolean(PreferenceConstants.COMPLETE_AUTO_INSERT));
		assistant.enableAutoActivation(store.getBoolean(PreferenceConstants.COMPLETE_AUTO_ACTIVATION));
		assistant.setAutoActivationDelay(store.getInt(PreferenceConstants.COMPLETE_AUTO_TIME));
		
		Color fg = this.editor.getColorManager().getColor(PreferenceConverter.getColor(store, PreferenceConstants.COMPLETE_FOREGROUND));
		Color bg = this.editor.getColorManager().getColor(PreferenceConverter.getColor(store, PreferenceConstants.COMPLETE_BACKGROUND));
		assistant.setContextSelectorForeground(fg);
		assistant.setContextSelectorBackground(bg);
		assistant.setInformationControlCreator(new SourceInformationControlCreator(null));
		
		SourceTemplateProcessor proc = this.createTemplateProcessor(this.getEditor());
		assistant.setContentAssistProcessor(proc, IDocument.DEFAULT_CONTENT_TYPE);
		return assistant;
	}*/
	

	protected SourceScanner getSourceScanner() {
		
		if (getScanner() == null) {
			setScanner(new UcfScanner(this.colorManager));
			getScanner().setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(SourceColorConstants.DEFAULT))));
		}
		return getScanner();
	}
	

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getSourceScanner());
		

		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		
		
		return reconciler;
	}
	
	/*
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		return new SourceDoubleClickStrategy(this.editor);
	}*/

	
	
	public void setScanner(SourceScanner scanner) {
		this.scanner = scanner;
	}
	public SourceScanner getScanner() {
		return scanner;
	}
	public void setEditor(UcfEditor editor) {
		this.editor = editor;
	}
	public UcfEditor getEditor() {
		return editor;
	}
	

}
