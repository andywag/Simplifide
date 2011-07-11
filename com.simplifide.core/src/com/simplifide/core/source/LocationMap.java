package com.simplifide.core.source;

import java.net.URI;
import java.util.HashMap;

import com.simplifide.core.source.design.DesignFile;

/** Contains a HashMap of Design Files used for Har Files */
public class LocationMap {

	private static LocationMap instance;
	private HashMap<URI,DesignFile> designMap = new HashMap<URI,DesignFile>();
	
	private LocationMap() {}
	
	public static LocationMap getInstance() {
		if (instance == null) instance = new LocationMap();
		return instance;
	}
	
	public void addDesignFile(URI uri, DesignFile dfile) {
		designMap.put(uri, dfile);
	}
	
	public void removeDesignFile(URI uri) {
		designMap.remove(uri);
	}
	
	public DesignFile getDesignFile(URI uri) {
		return designMap.get(uri);
	}
	
	
	
}
