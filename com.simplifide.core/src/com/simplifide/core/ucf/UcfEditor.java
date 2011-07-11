/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.ucf;

import java.util.HashSet;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ChainedPreferenceStore;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.baseeditor.actions.EditorActionDelegate;
import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.ui.preference.PreferenceConstants;

public class UcfEditor extends TextEditor  {

	private HashSet<EditorActionDelegate> actionSet = new HashSet<EditorActionDelegate>();
	
	private ColorManager colorManager;
	
	// private DesignFile designFile;

	public UcfEditor() {
		super();
		this.setSourceViewerConfiguration(new UcfConfiguration(this.getColorManager(),this));

	}

	public void addListener(EditorActionDelegate del) {
		this.actionSet.add(del);
	}
	
	public void dispose() {
		//getColorManager().dispose();
		
		//if (this.occListener != null) this.occListener.dispose();
		
		this.actionSet = null;
		super.dispose();

	}

	
	public void syncSave() {
		this.getEditorSite().getShell().getDisplay().syncExec(
				new Runnable() {
					public void run() {
						doSave(null);
					}
				});
	}

	

            	       
	
	public void createPartControl(Composite parent) {
		
		
		IPreferenceStore store = this.getPreferenceStore();
		IPreferenceStore current = CoreActivator.getDefault().getPreferenceStore();
		ChainedPreferenceStore chain = new ChainedPreferenceStore(new IPreferenceStore[] {current,store});
		this.setPreferenceStore(chain);
		
		super.createPartControl(parent);
		
	}


	
	
	
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		DefaultCharacterPairMatcher mz = new DefaultCharacterPairMatcher( new char[] {'(', ')', '{', '}', '[', ']'});
		support.setCharacterPairMatcher(mz);
		support.setMatchingCharacterPainterPreferenceKeys(PreferenceConstants.EDITOR_PAREN_MATCH,
				PreferenceConstants.EDITOR_PAREN_COLOR);
		super.configureSourceViewerDecorationSupport(support);

	}
	
	
	



	

	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {

		ISourceViewer viewer = new ProjectionViewer(parent, ruler,
				getOverviewRuler(), isOverviewRulerVisible(), styles);

		getSourceViewerDecorationSupport(viewer);
		return viewer;
	}


	protected DesignFile getDesignFileAlone(IFileStore fileStore) {
		return null;
	}

		
	public boolean validateEditorInputState() {
		return true;
	}
	
	
	public ColorManager getColorManager() {
		return ColorManager.getInstance();
	}


	

	
}


