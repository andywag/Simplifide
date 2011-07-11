package com.simplifide.core.project.library.har;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;

import com.simplifide.core.project.library.zip.ZipFileStorage;
import com.simplifide.core.project.library.zip.ZipFileStorageMap;
import com.simplifide.core.project.library.zip.ZipFileStore;

public class LibraryHarInformation {

	public static String EXTENSION = "har";
	
	private EclipseHarProject library;
	
	public LibraryHarInformation(EclipseHarProject library) {
		this.library = library;
	}

	
	private String getLibraryStoreName() {
		return library.getname() + "." + EXTENSION;
	}
	
	
	
	public IFileStore getMainFile() {
		IFileStore par = EFS.getLocalFileSystem().getStore(library.getResource().getParent().getRawLocationURI());
		IFileStore child = par.getChild(library.getResource().getName());
		return child;
	}
	
	public ZipFileStore getZipFileStore(String path) {
		return this.getFileStorage().getFileStore(path);
	}
	
	public ZipFileStorage getFileStorage() {
		URI main = library.getResource().getLocationURI();
		ZipFileStorage storage = ZipFileStorageMap.getInstance().getStore(main);
		return storage;
	}
	
	public IFileStore getProjectStoreFile() {
		
		return this.getFileStorage().getFileStore("project.store");
		
		/*
		IFileStore store = EFS.getLocalFileSystem().getStore(this.getStoreLocation().toURI());
		ZipFileStore root = new ZipFileStore(store,new Path(""));
		try {
			IFileStore[] children = root.childStores(0, null);
			long lo = children[0].fetchInfo().getLength();
			store = children[0];
		} catch (CoreException e1) {
			
		}
		IFileInfo info = root.fetchInfo();
		
		String path = getLibraryStoreName();
		FileStoreEditorInput in = new FileStoreEditorInput(store);
		IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		try {
			page.openEditor(in, "com.simplifide.core.vhdl.editor.VhdlEditor");
		} catch (PartInitException e) {
			HardwareLog.logError(e);
		}

		String st = store.toString();
		return store;
		*/
	}
	
	

	
}
