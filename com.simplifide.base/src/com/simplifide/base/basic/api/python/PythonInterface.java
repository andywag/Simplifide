/*
 * PythonInterface.java
 *
 * Created on August 23, 2006, 8:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.python;

/**
 *
 * @author awagner
 */
public class PythonInterface {
    
    private static PythonInterface instance;
    
    private Object suiteProject;
    private Object parseDescriptor;
    /** Creates a new instance of PythonInterface */
    public PythonInterface() {}
    
    public static PythonInterface getInstance()
    {
        if (instance == null) instance = new PythonInterface();
        return instance;
    }

    public Object getSuiteProject() {
        return suiteProject;
    }

    public void setSuiteProject(Object suiteProject) {
        this.suiteProject = suiteProject;
    }

    public Object getParseDescriptor() {
        return parseDescriptor;
    }

    public void setParseDescriptor(Object parseDescriptor) {
        this.parseDescriptor = parseDescriptor;
    }
    
    
    
}
