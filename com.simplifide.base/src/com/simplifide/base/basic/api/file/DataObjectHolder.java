/*
 * DataObjectHolder.java
 *
 * Created on May 18, 2006, 12:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.file;

import com.simplifide.base.basic.api.progress.ProgressInt;


/**
 *
 * @author awagner
 */

/** Interface to handle all general files which allows netbeans to be removed from the core and
 *  simplifies the interfaces */

public interface DataObjectHolder
{
    
  
    

    public Object getDataObject();
    public FileHolder getFile();

    /** Build the File */
    public void build(int pass, ProgressInt prog);
    /** Open the document in the editor */
    public void open();
    /** Delete the Document */
    public void delete();
    /** Close the Document */
    public void close();
    /** Opens the File at the Position */
    public void openAtPosition(int pos);
    /** Removes the object from memory */
    public void deleteObject();
   
    
}
