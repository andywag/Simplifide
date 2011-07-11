package com.simplifide.core.project.library.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

/**
 * File store implementation representing a file or directory inside
 * a zip file.
 */
public class ZipFileStore extends FileStore {
	
	
	private String location;
	private ZipFileStorage storage;

	

	public ZipFileStore(ZipFileStorage storage, String location) {
		this.storage = storage;
		this.location = location;
	}
	
	private ZipEntry[] childEntries(IProgressMonitor monitor) throws CoreException {
		// No Support for Children
		return new ZipEntry[0];
		
	}

	public IFileInfo[] childInfos(int options, IProgressMonitor monitor) throws CoreException {
		/*ZipEntry[] entries = childEntries(monitor);
		int entryCount = entries.length;
		IFileInfo[] infos = new IFileInfo[entryCount];
		for (int i = 0; i < entryCount; i++)
			infos[i] = convertZipEntryToFileInfo(entries[i]);
		return infos;*/
		return new IFileInfo[0];
	}

	public String[] childNames(int options, IProgressMonitor monitor) throws CoreException {
		/*ZipEntry[] entries = childEntries(monitor);
		int entryCount = entries.length;
		String[] names = new String[entryCount];
		for (int i = 0; i < entryCount; i++)
			names[i] = computeName(entries[i]);
		return names;*/
		return new String[0];
	}

	/**
	 * Computes the simple file name for a given zip entry.
	 */
	private String computeName(ZipEntry entry) {
		//the entry name is a relative path, with an optional trailing separator
		//We need to strip off the trailing slash, and then take everything after the 
		//last separator as the name
		String name = entry.getName();
		int end = name.length() - 1;
		if (name.charAt(end) == '/')
			end--;
		return name.substring(name.lastIndexOf('/', end) + 1, end + 1);
	}

	/**
	 * Creates a file info object corresponding to a given zip entry
	 * 
	 * @param entry the zip entry
	 * @return The file info for a zip entry
	 */
	private IFileInfo convertZipEntryToFileInfo(ZipEntry entry) {
		FileInfo info = new FileInfo(computeName(entry));
		info.setLastModified(entry.getTime());
		info.setExists(true);
		info.setDirectory(entry.isDirectory());
		info.setLength(entry.getSize());
		return info;
	}

	private ZipEntry getZipEntry() {
		return this.storage.getEntry(this.location);
	}
	
	public IFileInfo fetchInfo(int options, IProgressMonitor monitor)  {
		ZipEntry current = this.getZipEntry();
		if (current != null) return convertZipEntryToFileInfo(current);
		return new FileInfo(getName());
	}

	/**
	 * @return A directory info for this file store
	 */
	private IFileInfo createDirectoryInfo(String name) {
		FileInfo result = new FileInfo(name);
		result.setExists(true);
		result.setDirectory(true);
		return result;
	}

	/**
	 * Finds the zip entry with the given name in this zip file.  Returns the
	 * entry and leaves the input stream open positioned at the beginning of
	 * the bytes of that entry.  Returns null if the entry could not be found.
	 */
	private ZipEntry findEntry(String name, ZipInputStream in) throws IOException {
		ZipEntry current;
		File ufile = new File(name);
		
		while ((current = in.getNextEntry()) != null) {
			File afile = new File(current.getName());
			if (ufile.equals(afile))
				return current;
			if (ufile.getName().equalsIgnoreCase(current.getName()))
				return current;
		}
		return null;
	}

	public IFileStore getChild(String name) {
		return null;
	}

	public String getName() {
		File ufile = new File(location);
		return ufile.getName();
	}

	public IFileStore getParent() {
		return null;
	}

	/**
	 * Returns whether ancestor is a parent of child.
	 * @param ancestor the potential ancestor
	 * @param child the potential child
	 * @return <code>true</code> or <code>false</code>
	 */
	private boolean isAncestor(String ancestor, String child) {
		//children will start with myName and have no child path
		int ancestorLength = ancestor.length();
		if (ancestorLength == 0)
			return true;
		return child.startsWith(ancestor) && child.length() > ancestorLength && child.charAt(ancestorLength) == '/';
	}

	private boolean isParent(String parent, String child) {
		return false;
	}

	public InputStream openInputStream(int options, IProgressMonitor monitor) throws CoreException {
		return this.storage.getEntryInputStream(this.location);
	}

	public URI toURI() {
		try {
			String path = storage.toURI().toString();
			Path upath = new Path(location);
			URI uri = new URI(ZipFileSystem.SCHEME_ZIP, null,upath.makeAbsolute().toString(), storage.toURI().toString(), null);
			//URI uri = new URI(ZipFileSystem.SCHEME_ZIP, null,storage.toURI().toString(),upath.toString() , null);

			return uri;
		} catch (URISyntaxException e) {
			//should not happen
			throw new RuntimeException(e);
		}
	}

	
	
	

	
}

