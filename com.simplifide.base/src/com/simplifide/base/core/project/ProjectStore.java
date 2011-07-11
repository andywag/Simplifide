/*
 * ProjectStore.java
 *
 * Created on April 3, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.project;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.simplifide.base.BaseLog;

/**
 *
 * @author Andy
 */
public class ProjectStore {
    
    /** Creates a new instance of ProjectStore */
    public ProjectStore() {}
    
    public void writeObject(File xmlFile, Object obj) {
        try {
            XMLEncoder encode = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlFile)));
            encode.writeObject(obj);
            encode.flush();
            encode.close();
        } catch (FileNotFoundException ex) {
            BaseLog.logError(ex);
        } 
    }
    
    public static Object readObject(File xmlFile) {
         try {
            XMLDecoder decode = new XMLDecoder(new BufferedInputStream(new FileInputStream(xmlFile)));
            return decode.readObject();
        } catch (FileNotFoundException ex) {
            BaseLog.logError(ex);
        }
         return null;
    }
    
}
