package com.simplifide.core.project.library.har;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.project.library.storage.EclipseLibraryStore;
import com.simplifide.core.util.FileUtility;

public class EclipseLibraryHarStore extends EclipseLibraryStore{

	private static final long serialVersionUID = -6498485671412925871L;

	/** Returns the library for project creation */
	private File getHarFile(EclipseBaseProject library) {
		File location = FileUtility.convertIFolder2File(library.getDesignIFolder().getParent());
		String namString = library.getname() + ".har";
		File store = new File(location.getParent(),namString);
		return store;
	}
	
	public void createStorage(EclipseBaseProject library) {		
		super.createStorage(library);
		
		File location = FileUtility.convertIFolder2File(library.getDesignIFolder().getParent());
		File store = this.getHarFile(library);
		LibraryHarGenerator.createHarFile(store.toString(),location.toString() );
	}
	
	public EclipseLibraryStore loadStorage(EclipseHarProject library, LibraryHarInformation info) {
		FileInputStream fis;
		try {
			
			IFileStore fstore = info.getProjectStoreFile();
			InputStream stream = fstore.openInputStream(0, null);
			
			ObjectInputStream ois = new ObjectInputStream(stream);
			EclipseLibraryStore store = (EclipseLibraryStore) ois.readObject();
			ois.close();
			stream.close();
			return store;
		} catch (FileNotFoundException e) {
			HardwareLog.logError(e);
		} catch (IOException e) {
			HardwareLog.logError(e);
		} catch (ClassNotFoundException e) {
			//HardwareLog.logError(e);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		
		return null;

	}
	
}
