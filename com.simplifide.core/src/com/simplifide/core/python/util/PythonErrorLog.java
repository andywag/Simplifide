/*
 * PythonUtilities.java
 *
 * Created on July 27, 2006, 10:11 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.core.python.util;

import com.simplifide.core.HardwareLog;




/**
 *
 * @author awagner
 */
public class PythonErrorLog {
    
    public static PythonErrorLog instance;
    /** Creates a new instance of PythonUtilities */
    public PythonErrorLog() {}
 
    public static PythonErrorLog getInstance()
    {
        if (instance == null) instance = new PythonErrorLog();
        return instance;
    }
    
    public static void printLog(String value) {
    	HardwareLog.logInfo("Jython Message -- " + value);
    }
    
   
    
}
