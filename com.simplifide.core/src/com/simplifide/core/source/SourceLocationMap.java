package com.simplifide.core.source;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.source.design.DesignFile;

/** Contains a HashMap of Design Files used for Har Files */
public class SourceLocationMap {

	private static SourceLocationMap instance;
	private HashMap<URI,SourceFile> designMap = new HashMap<URI,SourceFile>();
	
	private SourceLocationMap() {}
	
	public static SourceLocationMap getInstance() {
		if (instance == null) instance = new SourceLocationMap();
		return instance;
	}
	
	public void addDesignFile(URI uri, SourceFile dfile) {
		if (!dfile.isAloneFile()) designMap.put(uri, dfile);
	}
	
	public void removeDesignFile(URI uri) {
		designMap.remove(uri);
	}
	
	public SourceFile getSourceFile(URI uri) {
		return designMap.get(uri);
	}
	
	public DesignFile getDesignFile(URI uri) {
		SourceFile sfile = this.getSourceFile(uri);
		if (sfile instanceof DesignFile) return (DesignFile) sfile;
		return null;
	}
	
	public SourceFile findPartialFile(String name) {
		Set<URI> uris = designMap.keySet();
		for (URI uri : uris) {
			URL url;
			try {
				if (uri.getScheme().equalsIgnoreCase("file")) {
					url = uri.toURL();
					String file = url.getFile();
					File afile = new File(file);
					if (afile.getName().equals(name)) {
						return designMap.get(uri);
					}
				}
			} catch (MalformedURLException e) {
				HardwareLog.logError(e);
			}
		}
		return null;
	}
	
	
}
