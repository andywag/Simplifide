/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.project.library.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.base.basic.struct.UniqueList;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.library.har.EclipseHarProject;
import com.simplifide.core.project.library.har.LibraryHarGenerator;
import com.simplifide.core.project.library.zip.ZipFileStore;
import com.simplifide.core.project.structure.ProjectStructureHolderTop;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.util.FileUtility;




/** Class which stores the context of the project library */
public class EclipseLibraryStore implements Serializable{

	
	private static final long serialVersionUID = -4372904802200826466L;
	
	private String libraryName;
	private URI baseLocation;
	public List<DependencyList> fileList = new ArrayList<DependencyList>();
	public ArrayList<InstanceStore> instanceList = new ArrayList<InstanceStore>();
	
	
	public EclipseLibraryStore() {}
	
	private static File getConfigFile(EclipseBaseProject library) {
		IFile ifile = library.getBaseFolder().getFile(ProjectStructureHolderTop.FILE_CONFIG);
		File file = ifile.getLocation().toFile();
		return file;
	}
	
	private static File getConfigFileCreate(EclipseBaseProject library) {
		
		File file = getConfigFile(library);
		if (!file.exists()) {
			try {
				file.createNewFile();
				return file;
			} catch (IOException e) {
				HardwareLog.logError(e);
			}
		}
		return file;
	}
	
	public void convertLocationsToHar(EclipseHarProject har) {
		IFileStore root = har.getInformation().getMainFile();
		
		for (DependencyList lis : this.fileList) {
			URI u = lis.getLocation();
			URI rel = baseLocation.relativize(u);
			String urel = rel.toString();
			ZipFileStore rels = har.getInformation().getZipFileStore(rel.toString());
			lis.setLocation(rels.toURI());
		}
		for (InstanceStore inst : this.instanceList) {
			inst.convertLocationsToHar(har, baseLocation);
		}
	}
	
	private void writeObject(EclipseBaseProject library) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(getConfigFileCreate(library));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			
			oos.close();
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		}
		
	}
	
	public static EclipseLibraryStore readObject(EclipseBaseProject library) {
		FileInputStream fis;
		try {
			File ufile = getConfigFile(library);
			if (!ufile.exists()) return null;
			
			fis = new FileInputStream(ufile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			EclipseLibraryStore store = (EclipseLibraryStore) ois.readObject();
			ois.close();
			fis.close();
			return store;
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		} catch (ClassNotFoundException e) {
			HardwareLog.logError(e);
		}
		
		
		return null;

	}
	
	
	
	public void createStorage(EclipseBaseProject library) {		
		// Save up the design files and dependencies
		this.baseLocation = library.getDesignIFolder().getParent().getLocationURI();
		this.libraryName = library.getname();
		
		int total = 0;
		for (DesignFile dfile : library.getSourceList().getRealSelfList()) {
			DependencyList lis = new DependencyList(dfile);
			if (dfile.getParseDescriptor().getModule().isContainsDefines())  {
				lis.setAutomaticLoad(true);
				total++;
			}
			this.fileList.add(lis);
		}
		
		// Convert the instance module to instance store and then save the values */
		for (ReferenceItem item : library.getGenericSelfList()) {
			InstanceStore store = InstanceStore.newInstanceStore(item);
			if (store != null) this.instanceList.add(store);
		}
		this.writeObject(library);	
		
		File location = FileUtility.convertIFolder2File(library.getDesignIFolder().getParent());
		String namString = library.getname() + ".har";
		File store = new File(location.getParent(),namString);
		LibraryHarGenerator.createHarFile(store.toString(),location.toString() );
		try {
			library.getBaseFolder().getParent().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
	}
	
	/** Convert all of the references in this library to have the proper base location
	 * */
	public void convertLocations(UniqueList<DesignFile> sourceList) {
		for (int i=0;i<this.fileList.size();i++) {
			DependencyList dep = (DependencyList) this.fileList.get(i);
			String loc = dep.getLocation().getPath();
			File ufile = new File(loc);
			for (ReferenceItem<? extends DesignFile> dfileRef : sourceList.getGenericSelfList()) {
				String fname = dfileRef.getObject().getname();
				if (ufile.getName().equalsIgnoreCase(fname)) {
					dep.setLocation(dfileRef.getObject().getUri());
				}
			}
		}
		for (int i=0;i<this.instanceList.size();i++) {
			InstanceStore store = (InstanceStore) this.instanceList.get(i);
			store.convertLocations(sourceList);
		}
	}
	
	/** Load up the dependency lists for the files */
	public void resolveDependenceList(ReferenceItem<? extends CoreProjectSuite> suiteRef) {
		for (int i=0;i<this.fileList.size();i++) {
			DependencyList dep = (DependencyList) this.fileList.get(i);
			dep.convertList(suiteRef);
		}
	}
	
	/** Load up the dependent items */
	public void resolveLibraryList(EclipseBaseProject library) {
		for (int i=0;i<this.instanceList.size();i++) {
			InstanceStore store = (InstanceStore) this.instanceList.get(i);
			ReferenceItem ref = store.loadStore(library);
			if (store != null) library.addReferenceItem(ref);
		}
	}
	
	
	public void setFileList(List<DependencyList> fileList) {
		this.fileList = fileList;
	}

	public List<DependencyList> getFileList() {
		return fileList;
	}

	public void setInstanceList(ArrayList instanceList) {
		this.instanceList = instanceList;
	}

	public List getInstanceList() {
		return instanceList;
	}

	public void setBaseLocation(URI baseLocation) {
		this.baseLocation = baseLocation;
	}

	public URI getBaseLocation() {
		return baseLocation;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public String getLibraryName() {
		return libraryName;
	}



	
	
	
	
}
