package com.simplifide.core.project.library.zip;

import java.net.URI;
import java.util.HashMap;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;

public class ZipFileStorageMap {

	private static ZipFileStorageMap instance ;
	private ZipFileStorageMap() {}
	
	private HashMap<URI,ZipFileStorage> stores = new HashMap<URI,ZipFileStorage>();
	
	public static ZipFileStorageMap getInstance() {
		if (instance == null) instance = new ZipFileStorageMap();
		return instance;
	}
	
	public ZipFileStorage getStore(URI uri) {
		ZipFileStorage store = stores.get(uri);
		if (store == null) {
			try {
				store = new ZipFileStorage(EFS.getStore(uri));
				stores.put(uri, store);
			} catch (CoreException e) {
				HardwareLog.logError(e);
			}
		}
		return store;
	}
	
	public void addStore(ZipFileStorage store) {
		stores.put(store.toURI(), store);
	}
	
	public void removeStore(ZipFileStorage store) {
		stores.remove(store.toURI());
	}
	
}
