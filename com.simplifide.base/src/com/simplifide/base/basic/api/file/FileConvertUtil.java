/*
 * FileHandleUtil.java
 *
 * Created on May 17, 2006, 6:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.file;

import java.io.File;


/**
 *
 * @author awagner
 */
public class FileConvertUtil {

    private FileGenerator generator;
    private static FileConvertUtil instance;

    /** Creates a new instance of FileHandleUtil */
    public FileConvertUtil() {}

    public static FileConvertUtil getDefault()
    {
    	
        if (instance == null) instance = new FileConvertUtil();
        return instance;
    }

    public static FileHolder getFile(File fin)
    {
        return getDefault().generator.createFileHolder(fin);
    }
    
    /** This is not the desired way to deal with this operation and is temporary */
    public FileGenerator getGenerator() {
        if (generator == null) {
 //           this.generator = NetBeanFileGenerator.getInstance();
        }
        return generator;
    }

    public void setGenerator(FileGenerator generator) {
        this.generator = generator;
    }

    /*public static FileHolder createFileHolder(File fin)
    {
        return this.getDefault().getGenerator().createFileHolder(fin);
    }
    */
    
}
