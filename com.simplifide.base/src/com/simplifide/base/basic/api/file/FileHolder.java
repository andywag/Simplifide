/*
 * FileHolder.java
 *
 * Created on May 17, 2006, 6:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author awagner
 */

/** This class will abstract out the file objects inside netbeans or any other file usage */
/*  This will make standard I/O operations general */
public interface FileHolder {

    public Object getFileObject();
    public String getPath();

    public FileHolder[] getChildren();
    public FileHolder getFileHolder(String name);
    public FileHolder getFileHolder(String name, String ext);
    public FileHolder copy(FileHolder holder, String name, String ext);
    // File Writing Functionallity
    public OutputStream getOutputStream();
    public void closeOutputStream();
    // File Reading
    public InputStream getInputStream();

    public String getName();
    public String getExtension();
    public FileHolder getParent();
    public FileHolder createData(String name, String ext);
    public FileHolder createFolder(String name);
    
    public File getFile();
    
    public void addFileChangeListener(BasicFileChangeListener listen);
    public void removeFileChangeListener(BasicFileChangeListener listen);

    
}
