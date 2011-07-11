/*
 * FileHolderTop.java
 *
 * Created on February 13, 2007, 10:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.module;

import com.simplifide.base.basic.api.progress.ProgressInt;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.ModuleObjectWithList;

/**
 *
 * @author awagner
 */
public class SuperModuleTop<T extends ModuleObjectNew> extends ModuleObjectWithList<T> {
    
    
    /** Creates a new instance of FileHolderTop */
    public SuperModuleTop() {}
    public SuperModuleTop(String name) {super(name);}
    
    
    /** Build the project */
    public void build(int pass, ProgressInt prog) {
        
    }
    
    /** Close the editor window */
    public void close() {
        
    }
    
    
    
    
    
    
    
}
