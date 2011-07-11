/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.editors;

import java.net.URI;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.DefaultPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.editors.text.ILocationProvider;
import org.eclipse.ui.editors.text.ILocationProviderExtension;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;

import com.simplifide.core.baseeditor.color.SourcePartitionScanner;
import com.simplifide.core.project.library.zip.ZipFileStore;


public abstract class SourceDocumentProvider extends TextFileDocumentProvider {

	protected static final int DEFAULT_FILE_SIZE= 15 * 1024;
	

	public SourceDocumentProvider() {
		this.setParentDocumentProvider(new FileDocumentProvider());
		
	}
	
	public abstract SourcePartitionScanner createPartitionScanner();
	
	/**
	 * 
	 */
	public IDocument getDocument(Object element) {
		IDocument doc =  super.getDocument(element);
		if (doc.getDocumentPartitioner() == null) {
			FastPartitioner part = new FastPartitioner(createPartitionScanner(), new String[] {
				IDocument.DEFAULT_CONTENT_TYPE,
				SourcePartitionScanner.SCRIPT_COMMENT,
				SourcePartitionScanner.MULTI_COMMENT,
				SourcePartitionScanner.MULTI_DOC});
			
			
			doc.setDocumentPartitioner(part);
			part.connect(doc);
		}
		
		return doc;
	}
	
	
	protected FileInfo createFileInfo(Object element) throws CoreException {
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable= (IAdaptable) element;
		ILocationProvider provider= (ILocationProvider) adaptable.getAdapter(ILocationProvider.class);
		if (provider instanceof ILocationProviderExtension) {
			URI uri= ((ILocationProviderExtension)provider).getURI(element);
			IFileStore store = EFS.getStore(uri);
			if (store instanceof ZipFileStore) {
				ITextFileBufferManager manager= FileBuffers.getTextFileBufferManager();
				manager.connectFileStore(store, null);
				ITextFileBuffer fileBuffer= manager.getFileStoreTextFileBuffer(store);
				FileInfo info= createEmptyFileInfo();
				info.fTextFileBuffer = fileBuffer;
				info.fModel = info.fTextFileBuffer.getAnnotationModel();
				info.fCachedReadOnlyState = true;				
				return info;
			}
		}
		return super.createFileInfo(element);

		
	}
	
	
}
