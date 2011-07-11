package com.simplifide.core.project.library.har;

import java.net.URI;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseSuite;
import com.simplifide.core.project.library.EclipseLibraryProject;
import com.simplifide.core.project.library.storage.DependencyList;
import com.simplifide.core.project.library.storage.EclipseLibraryStore;
import com.simplifide.core.source.LocationMap;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileAlone;

public class EclipseHarProject extends EclipseLibraryProject{

	private IResource resource;
	private LibraryHarInformation information;
	
	private UniqueList<DesignFile> sourceList;
	private EclipseLibraryStore thisStore;
	
	private boolean loaded = false;

	
	public EclipseHarProject(String name, IResource resource,ReferenceItem<EclipseSuite> suiteRef) {
		super(name, null, suiteRef);
		this.setResource(resource);
		init();
	}
	
	
	
	private void init() {
		this.information = new LibraryHarInformation(this);
		this.sourceList = new UniqueList<DesignFile>();	
	}
	
	
	
	public static EclipseHarProject createHarProject(IResource resource, ReferenceItem<EclipseSuite> suiteR) {
		String name = resource.getName();
		String[] names = name.split("\\.");
		if (names.length > 1 && names[1].equalsIgnoreCase("har")) {
			EclipseHarProject project = new EclipseHarProject(names[0], resource, suiteR);
			return project;
		}
		return null;
	}

	private void loadDesignMap(EclipseLibraryStore store) {
		for (DependencyList dep : store.getFileList()) {
			URI ur = dep.getLocation();
			IFileStore fstore;
			try {
				int b;
				fstore = EFS.getStore(ur);
				DesignFile dfile = DesignFileAlone.resolveObject(fstore);
				dfile.setProjectRef(this.createReferenceItem());
				dfile.getDesignFileBuilder().attachProject(this);
				LocationMap.getInstance().addDesignFile(ur,dfile);
				sourceList.addObject(dfile.createReferenceItem());
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
			

		}
	}
	
	public void loadFiles() {
		
		this.loaded = true;
		EclipseLibraryHarStore store1 = new EclipseLibraryHarStore();
		EclipseLibraryStore store = store1.loadStorage(this, this.getInformation());
		if (store == null) {
			HardwareLog.logInfo("Issue Loading " + this);
			return;
		}
		// Set the Library Name
		if (store.getLibraryName() != null) {
			this.setname(store.getLibraryName());
			this.createReferenceItem().setname(store.getLibraryName());
		}
		
		UniqueList<DesignFile> sourceList = this.getSourceList();
		
		store.convertLocationsToHar(this);
		this.loadDesignMap(store);

		store.convertLocations(sourceList);
		store.resolveLibraryList(this);
		store.resolveDependenceList(this.getSuiteReference());
		thisStore = store;
		
	}
	
	public void build(int kind, IProgressMonitor monitor)  {
		if (thisStore == null) return;
		for (DependencyList lis : thisStore.getFileList()) {
			if (lis.isAutomaticLoad()) {
				DesignFile dfile = LocationOperations.getDesignFile(lis.getLocation());
				if (dfile != null)
					dfile.getBuilder().build(BuildInterface.BUILD_FILE_CLOSED);
			}
		}
	}
	
	
	public void setInformation(LibraryHarInformation information) {
		this.information = information;
	}

	public LibraryHarInformation getInformation() {
		return information;
	}

	public void setResource(IResource resource) {
		this.resource = resource;
	}

	public IResource getResource() {
		return resource;
	}

	public void setSourceList(UniqueList<DesignFile> sourceList) {
		this.sourceList = sourceList;
	}

	public UniqueList<DesignFile> getSourceList() {
		return sourceList;
	}

	
}
