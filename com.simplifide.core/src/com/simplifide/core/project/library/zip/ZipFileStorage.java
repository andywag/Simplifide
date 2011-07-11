package com.simplifide.core.project.library.zip;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;

import com.simplifide.core.HardwareLog;

public class ZipFileStorage {

	private IFileStore baseLocation;
	private HashMap<String,ZipFileStore> files = new HashMap<String,ZipFileStore>();
	
	public ZipFileStorage(IFileStore store) {
		this.baseLocation = store;
		this.loadMap();
	}
	
	public URI toURI() {
		return baseLocation.toURI();
	}
	
	
	
	private void loadMap()  {
		try {
			ZipInputStream in = new ZipInputStream(baseLocation.openInputStream(EFS.NONE, null));
			ZipEntry current;
			while ((current = in.getNextEntry()) != null) {
				String entry = current.getName();
				ZipFileStore store = new ZipFileStore(this,entry);
				files.put(entry, store);
			}
		}
		catch(CoreException e) {
			HardwareLog.logError(e);
		}
		catch (IOException e) {
			HardwareLog.logError(e);
		}
	}
	
	public ZipFileStore getFileStore(String location) {
		if (location.indexOf('\\') == 0) location = location.substring(1);
		if (location.indexOf('/') == 0) location = location.substring(1);

		ZipFileStore store = files.get(location);
		if (store == null) {
			if (location.indexOf('/') > 0) {
				String uloc = location.replace('/', '\\');
				store = files.get(uloc);
			}
			else if (location.indexOf('\\') > 0) {
				String uloc = location.replace('\\','/');
				store = files.get(uloc);
			}
		}
		return store;
	}
	
	public ZipEntry getEntry(String path) {
		try {
			return getEntryInternal(path);
		} catch (IOException e) {
			HardwareLog.logError(e);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	public ZipEntry getEntryInternal(String path) throws IOException, CoreException{
		ZipInputStream in = new ZipInputStream(baseLocation.openInputStream(EFS.NONE, null));
		ZipEntry current;
		while ((current = in.getNextEntry()) != null) {
			if (current.getName().equals(path)) {
				return current;
			}
		}
		return null;
	}
	
	public ZipInputStream getEntryInputStream(String path) {
		try {
			return getEntryInputStreamInternal(path);
		} catch (IOException e) {
			HardwareLog.logError(e);
		} catch (CoreException e) {
			HardwareLog.logError(e);
		}
		return null;
	}
	
	public ZipInputStream getEntryInputStreamInternal(String path) throws IOException, CoreException{
		ZipInputStream in = new ZipInputStream(baseLocation.openInputStream(EFS.NONE, null));
		ZipEntry current;
		while ((current = in.getNextEntry()) != null) {
			if (current.getName().equals(path)) {
				return in;
			}
		}
		return null;
	}
	
	
}
