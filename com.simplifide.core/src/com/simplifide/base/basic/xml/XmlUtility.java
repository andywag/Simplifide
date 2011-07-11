/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.basic.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.simplifide.base.BaseLog;

public class XmlUtility {
	
	public static void writeObject(File xmlFile, Object obj) {
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
