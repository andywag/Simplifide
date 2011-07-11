/*
 * LocationHandler.java
 *
 * Created on April 9, 2007, 1:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.source;


import java.util.HashMap;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.SourceNameFactory;
import com.simplifide.core.HardwareLog;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.editors.SourceEditor;
import com.simplifide.core.source.design.DesignFile;

/**
 * This class will break after the maximum integer number of files have been used, but I can probably live
 * with this for now
 * @author Andy
 */
public class SourceLocationHandler {
    
    
    private static SourceLocationHandler instance;
    private int currentIndex = 1;
    private HashMap<Integer,SourceFile> locationMap = new HashMap();
    private HashMap<String,Integer> inverseMap = new HashMap();
    private HashMap<String,Integer> simpleMap = new HashMap();

    /** Creates a new instance of LocationHandler */
    public SourceLocationHandler() {}
    
    /**
     * Return the instance of the location handling object
     * @return 
     */
    public static SourceLocationHandler getInstance() {
        if (instance == null) instance = new SourceLocationHandler();
        return instance;
    }
    
    private String stripPrefix(String uname) {
    	String[] us = uname.split("\\.");
    	if (us.length == 0) return uname;
    	return us[0];
    }
    
    /**
     * This registers a source file with this location
     * @param sfile 
     * @return currentIndex
     */
    public int registerLocation(SourceFile sfile) {
    	
        Integer index = Integer.valueOf(currentIndex);
        
        if (sfile.getResource().exists()) {
        	String sname = sfile.getFullPathName();
        	this.inverseMap.put(sname, index);
        	this.locationMap.put(index, sfile);
        	this.simpleMap.put(this.stripPrefix(sfile.getname()), index);
        
        	SourceNameFactory.getDefault().addEntry(index, sfile.getname());
        	this.currentIndex = currentIndex + 1;
        	return currentIndex - 1;
        }
        return -1;
    }
    
    /**
     * Remove this source file from the hashmap
     * @param index 
     */
    public void removeLocation(int index) {
        Integer ind = Integer.valueOf(index);
        SourceFile sfile = this.locationMap.get(ind);
        if (sfile == null) return;
        String pname = sfile.getFullPathName();
        if (pname != null) {
        	inverseMap.remove(pname);
        }
        else {
        	HardwareLog.logInfo("Issue Removing File");
        }
        
        this.locationMap.remove(ind);
        
    }
    
    public SourceFile getSimpleFile(String fname) {
    	Integer a = this.simpleMap.get(fname);
    	if (a == null) return null;
    	return this.getLocation(a);
    }
    
    public SourceFile getFile(String path) {
    	int u = this.getLocation(path);
    	return this.getLocation(u);
    }
    
    public int getLocation(String path) {
    	try {
    		HashMap a = this.inverseMap;
    		Integer b = (Integer) a.get(path);
    		return b;
    	}
    	catch (Exception e) {
    		HardwareLog.logError(e);
    	}
    	return -1;
    }
    
    /**
     * Returns the SourceFile stored at this location
     * @param index 
     * @return 
     */
    public SourceFile getLocation(int index) {
        Integer ind = Integer.valueOf(index);
        return this.locationMap.get(ind);
    }
    
    /**
     * 
     * @param loc 
     */
    
    private void goToLocalPosition(ReferenceLocation loc) {
    	IWorkbench wb = PlatformUI.getWorkbench();
    	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
    	IWorkbenchPage page = win.getActivePage();
    	IEditorPart part = page.getActiveEditor();
    	if (part instanceof SourceEditor) {
    		SourceEditor edit = (SourceEditor) part;
    		edit.goToPosition(loc.getDocPosition(),loc.getLength());
    	}
    }
    
    public void goToPosition(ReferenceLocation loc) {
    	LocationOperations.goToPosition(loc);
    	/*
        Integer ind = loc.getModIndex();
        if (ind.intValue() == 0) {
        	this.goToLocalPosition(loc);
        	return;
        }
        SourceFile source = this.locationMap.get(ind);
        if (source != null) {
        	IWorkbench wb = PlatformUI.getWorkbench();
        	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
        	IWorkbenchPage page = win.getActivePage();
        
        	IFile file = (IFile) source.getResource();
        	IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
        	
        	try {
        		IEditorPart part = page.openEditor(new FileEditorInput(file), desc.getId());
        		
        		if (part instanceof SourceEditor) {
        			SourceEditor edit = (SourceEditor) part;
        			edit.goToPosition(loc.getDocPosition());
        			
        		}
        		
        	} catch (PartInitException e) {
				HardwareLog.logError(e);
			}
        }*/
    }
    
    public void replaceText(ReferenceLocation loc, String text) {
    	DesignFile source = LocationOperations.getDesignFile(loc);
        source.replaceText(loc, text);
    }
    
}
