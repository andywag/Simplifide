/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.ide.FileStoreEditorInput;

import com.simplifide.core.HardwareLog;


public class SourceHarDocumentProvider extends FileDocumentProvider {

	
	public boolean isModifiable(Object element) {
		return true;
	}
	
	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException{
		
		if (element instanceof FileStoreEditorInput) {
			return new AnnotationModel();
		}
		return super.createAnnotationModel(element);
	}
	
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		
		if (document == null && element instanceof FileStoreEditorInput) {
			document = createEmptyDocument();
			// Open the stream
		      InputStream is = null;
		      try {
		    	FileStoreEditorInput el = (FileStoreEditorInput) element;
		    	IFileStore store = EFS.getStore(el.getURI());
		    	is = store.openInputStream(0, null);
		        setDocumentContent(document, is, getEncoding(element));
		      } finally {
		             try {
		               if (is != null) {
		                 is.close();
		               }
		           } catch (IOException x) {
		             HardwareLog.logError(x);
		           }
		       }
		}
		
		return document;
	}
	
	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		
		if (element instanceof IFileEditorInput) {
			super.doSaveDocument(monitor, element, document, overwrite);
		}
		if (element instanceof FileStoreEditorInput) {


				FileStoreEditorInput input = (FileStoreEditorInput) element;
				IFileStore fileStore = EFS.getStore(input.getURI());
				FileBuffers.getTextFileBufferManager().connectFileStore(fileStore,
						monitor);
				ITextFileBuffer buffer = FileBuffers.getTextFileBufferManager()
						.getFileStoreTextFileBuffer(fileStore);
				buffer.getDocument().set(document.get());
				buffer.commit(monitor, true);
				FileBuffers.getTextFileBufferManager().disconnectFileStore(
						fileStore, monitor);
			}
			
		}
	

	

}
