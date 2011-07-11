package com.simplifide.core.baseeditor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.DefaultHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.MultipleHyperlinkPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourceScanner;
import com.simplifide.core.baseeditor.hover.SourceInformationControlCreator;
import com.simplifide.core.baseeditor.hyperlink.GeneralHyperlinkDetector;
import com.simplifide.core.baseeditor.template.GeneralContentProcessor;
import com.simplifide.core.editors.color.SourceColorConstants;
import com.simplifide.core.ui.preference.PreferenceConstants;

public abstract class GeneralConfiguration extends TextSourceViewerConfiguration{

	private ColorManager colorManager;
	private GeneralEditor editor;
	
	private SourceScanner scanner;
	
	public GeneralConfiguration(ColorManager colorManager, GeneralEditor editor) {
		super(EditorsUI.getPreferenceStore());
		this.setColorManager(colorManager);
		this.setEditor(editor);

	}
	
	protected abstract GeneralContentProcessor createTemplateProcessor(GeneralEditor editor);

	
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		
		ContentAssistant assistant = new ContentAssistant();
		IPreferenceStore store = CoreActivator.getDefault().getPreferenceStore();
		
		
		assistant.enableAutoInsert(store.getBoolean(PreferenceConstants.COMPLETE_AUTO_INSERT));
		assistant.enableAutoActivation(store.getBoolean(PreferenceConstants.COMPLETE_AUTO_ACTIVATION));
		assistant.setAutoActivationDelay(store.getInt(PreferenceConstants.COMPLETE_AUTO_TIME));
		
		Color fg = this.getColorManager().getColor(PreferenceConverter.getColor(store, PreferenceConstants.COMPLETE_FOREGROUND));
		Color bg = this.getColorManager().getColor(PreferenceConverter.getColor(store, PreferenceConstants.COMPLETE_BACKGROUND));
		assistant.setContextSelectorForeground(fg);
		assistant.setContextSelectorBackground(bg);
		assistant.setInformationControlCreator(new SourceInformationControlCreator(null));
		
		GeneralContentProcessor proc = this.createTemplateProcessor(this.getEditor());
		assistant.setContentAssistProcessor(proc, IDocument.DEFAULT_CONTENT_TYPE);
		
		return assistant;
	}
	
	protected abstract SourceScanner createSourceScanner(ColorManager manager);
	protected abstract GeneralHyperlinkDetector createHyperlinkDetector(GeneralEditor editor);

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getSourceScanner());
		
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		
		
		return reconciler;
	}
	
	protected SourceScanner getSourceScanner() {
		
		if (getScanner() == null) {
			setScanner(this.createSourceScanner(this.getColorManager()));
			getScanner().setDefaultReturnToken(
				new Token(
					new TextAttribute(
						getColorManager().getColor(SourceColorConstants.DEFAULT))));
		}
		return getScanner();
	}
	
	public IHyperlinkPresenter getHyperlinkPresenter(ISourceViewer sourceViewer) {
		return new MultipleHyperlinkPresenter(CoreActivator.getDefault().getPreferenceStore());
	}
	
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		if (sourceViewer == null)
			return null;
		
		return new IHyperlinkDetector[] { this.createHyperlinkDetector(editor) };
	}
	
	
	
	public void setEditor(GeneralEditor editor) {
		this.editor = editor;
	}

	public GeneralEditor getEditor() {
		return editor;
	}

	public void setColorManager(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	public ColorManager getColorManager() {
		return colorManager;
	}
	
	
	public void setScanner(SourceScanner scanner) {
		this.scanner = scanner;
	}
	public SourceScanner getScanner() {
		return scanner;
	}
}
