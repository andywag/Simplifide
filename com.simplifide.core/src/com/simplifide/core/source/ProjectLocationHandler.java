/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.source;


import java.util.HashMap;

import com.simplifide.core.project.EclipseBaseProject;

public class ProjectLocationHandler {
	 
		private static ProjectLocationHandler instance;
	    private int currentIndex = 0;
	    private HashMap<Integer,EclipseBaseProject> locationMap = new HashMap();
	    /** Creates a new instance of LocationHandler */
	    public ProjectLocationHandler() {}
	    
	    /**
	     * Return the instance of the location handling object
	     * @return 
	     */
	    public static ProjectLocationHandler getInstance() {
	        if (instance == null) instance = new ProjectLocationHandler();
	        return instance;
	    }
	    
	    /**
	     * This registers a source file with this location
	     * @param sfile 
	     * @return currentIndex
	     */
	    public int registerLocation(EclipseBaseProject sfile) {
	        Integer index = Integer.valueOf(currentIndex);
	        this.locationMap.put(index, sfile);
	        this.currentIndex = currentIndex + 1;
	        return currentIndex - 1;
	    }
	    
	    /**
	     * Remove this source file from the hashmap
	     * @param index 
	     */
	    public void removeLocation(int index) {
	        Integer ind = Integer.valueOf(index);
	        this.locationMap.remove(ind);
	    }
	    
	    /**
	     * Returns the SourceFile stored at this location
	     * @param index 
	     * @return 
	     */
	    public EclipseBaseProject getLocation(int index) {
	        Integer ind = Integer.valueOf(index);
	        return this.locationMap.get(ind);
	    }
}
