package com.simplifide.core.project.library;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.library.storage.EclipseLibraryStore;
import com.simplifide.core.source.design.DesignFile;

/** Eclipse Project which is compiled into a library */
public class EclipseLibraryStoreProject extends EclipseLibraryProject{

	private boolean loaded = false;
	
	public EclipseLibraryStoreProject(String name, IFolder folder,ReferenceItem<EclipseSuite> suiteRef) {
		super(name, folder, suiteRef);
	}
	
	public void loadFiles() {
		try {
			super.loadFiles();
			this.init();
			this.loaded = true;
		}
		catch (Exception e) {
			HardwareLog.logError(e);
		}
	}
	
	
	
	private void init() {
		// Creates the Reference Items for the submodules
		EclipseLibraryStore store = EclipseLibraryStore.readObject(this);
		if (store == null) {
			HardwareLog.logInfo("Missing Store" + this);
			return;
		}
		UniqueList<DesignFile> sourceList = this.getSourceList();
		store.convertLocations(sourceList);
		store.resolveLibraryList(this);
		store.resolveDependenceList(this.getSuiteReference());
	}
	
	/** Builds the Design */
	public void build(int kind,  IProgressMonitor monitor)  {
		if (!loaded) super.build(kind, monitor);
	}
	
	

}
