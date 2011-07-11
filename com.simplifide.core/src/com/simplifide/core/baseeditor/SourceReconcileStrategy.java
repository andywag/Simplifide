/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.baseeditor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;

import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.core.HardwareLog;



public abstract class SourceReconcileStrategy implements IReconcilingStrategy{
	private IDocument document;
	private GeneralEditor editor;
	
	public SourceReconcileStrategy(GeneralEditor editor) {
		this.editor = editor;
		
	}
	
	public void reconcile(IRegion partition) {

		if (this.editor == null) return; 
		
		if (editor != null && editor instanceof GeneralEditor) {
			GeneralEditor editor1 = (GeneralEditor) editor;
			if (editor1.getGeneralFile() == null) return;
			//StringReader reader = new StringReader(this.document.get());
			editor1.getGeneralFile().getBuilder().build(BuildInterface.BUILD_FILE_OPEN,this.document.get());
			//editor.parseFinished();
				
		}
		
		
	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		
		
	}

	public void setDocument(IDocument document) {
		this.document = document;
	}
	
	
}
