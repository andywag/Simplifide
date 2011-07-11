/*
 * BasicIconManager.java
 *
 * Created on September 28, 2006, 10:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.resources;


import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.simplifide.core.CoreActivator;
import com.simplifide.core.HardwareLog;


/**
 *
 * @author Andy Wagner
 */
public class BasicIconManager {
    
    //public static int ICON_TOP = 0;
    ///public static int ICON_LIST = 1;
    
  
    
    public static final String MAIN_NONE   = "none";
    public static final String MAIN_SUITE = "suite";
    public static final String MAIN_PROJECT = "module";
    public static final String MAIN_FOLDER = "folder";
    public static final String MAIN_SUPERMODULE = "folder";
    public static final String MAIN_ENTITY = "interface";
    public static final String MAIN_COMPONENT = "interface";
    public static final String MAIN_CONNECTION = "interface";
    public static final String MAIN_INTERFACE_CONNECTION = "call_hierarchy";
    public static final String MAIN_MODULE = "class";
    public static final String MAIN_PORT_LIST = "variables";
    //public static final String MAIN_PORT = "paramInOut";
    public static final String MAIN_PORT_IN = "variableStProtected";
    public static final String MAIN_PORT_OUT = "variableStPublic";
    public static final String MAIN_PACKAGE = "packagePublic";
    public static final String MAIN_PACKAGE_BODY = "packagePrivate";
    public static final String MAIN_SIGNAL   = "variablePublic";
    public static final String MAIN_VARIABLE = "variableProtected";
    public static final String MAIN_CONSTANT = "variablePrivate";
    public static final String MAIN_ENUM     = "signal";
    public static final String MAIN_TYPE = "property";
    public static final String MAIN_TYPE_RECORD = "paramsNode";
    public static final String MAIN_TYPE_ARRAY = "propertyIndexedRW";
    public static final String MAIN_TYPE_ARRAY_UNCONSTRAINED = "propertyRW";
    public static final String MAIN_TYPE_ENUM = "paramsNode";
    public static final String MAIN_FUNCTION_HOLDER   = "methods";
    public static final String MAIN_INSTANCE_FUNCTION = "methodStPublic";
    public static final String MAIN_FUNCTION_DECLARATION = "methodPublic";
    public static final String MAIN_FUNCTION_BODY = "methodPrivate";
    public static final String MAIN_FIND = "find";
    public static final String MAIN_VHDL = "vhdl";
    public static final String MAIN_VERILOG = "verilog";
    public static final String MAIN_PROCESS = "constructorProtected";
    public static final String MAIN_ASSIGN  = "constructorPublic";
    public static final String MAIN_FORM = "form";
    public static final String MAIN_INTERFACE = "call_hierarchy";
    public static final String MAIN_PROGRAM = "procedure";
    
    public static final String MAIN_UP =   "propertyIndexedRO";
    public static final String MAIN_DOWN = "propertyIndexedWO";
    public static final String MAIN_FLAT = "propertyIndexedRW";
    public static final String MAIN_CLASS = "library";
    public static final String MAIN_GENERATE = "LocalVariable";
    public static final String MAIN_MODPORT = "interface";
 
    private static String PREFIX_OBJ16 = "full/obj16/";
    
    public static final String MAIN_PROPERTY = PREFIX_OBJ16 +"annotation_obj";
    public static final String MAIN_SEQUENCE = PREFIX_OBJ16 +"occ_write";
    public static final String MAIN_CLOCKING = PREFIX_OBJ16 + "thread_obj";
    public static final String MAIN_ASSERTION = PREFIX_OBJ16 + "testerr";
    
    public static final String BADGE_MODULE = "module-badge";
    public static final String BADGE_CONFIG = "config-badge";
    public static final String BADGE_GENERIC = "generic-badge";
    
    
  
    
    /** Creates a new instance of BasicIconManager */
    public BasicIconManager() {}
    
    public static ImageDescriptor getIcon(String name){
        return getImage(name);
    }
    
    public static Image getIconWithBadge(String mainname, String badgename, int x, int y)
    {
        
        return null;
    }
    

    
   
    
    protected static ImageDescriptor getImage(String filename) {
    	if (filename.equals("folder")) {
    		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER);
    	}
    	URL url = null;
        try {
        	
        url = new URL(CoreActivator.getDefault().getBundle().getEntry("icons/"),
                       filename + ".gif");
        } catch (MalformedURLException e) {
        }
        return ImageDescriptor.createFromURL(url);
      }

    	
    public static Image getRealImage(String filename) {
    	Image desc = CoreActivator.getDefault().getImageRegistry().get(filename);
    	if (desc == null) {
    		CoreActivator.getDefault().getImageRegistry().put(filename, getImage(filename).createImage());
    		desc = CoreActivator.getDefault().getImageRegistry().get(filename);
    	}
    	if (desc.isDisposed()) {
    		desc = getImage(filename).createImage();
    		try {
        		CoreActivator.getDefault().getImageRegistry().remove(filename);
        		CoreActivator.getDefault().getImageRegistry().put(filename,desc);
    		}
    		catch (Exception e) {
    			//HardwareLog.logError(e);
    		}
    	}
    	if (desc == null || desc.isDisposed()) {
    		HardwareLog.logInfo("Issue with Image Generation");
    		if (desc.isDisposed()) return null;
    	}
    	return desc;
    }
    
  
    
   
    
}
