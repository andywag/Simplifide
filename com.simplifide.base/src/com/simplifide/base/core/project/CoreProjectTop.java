/*
 * CoreProjectTop.java
 *
 * Created on May 12, 2006, 1:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.project;

import com.simplifide.base.basic.api.file.FileHolder;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;

/**
 *
 * @author awagner
 */
public abstract class CoreProjectTop<T extends ModuleObject> extends ModuleObjectWithList<T>{

    
    
    //private SourceHolder<TopFolder, CoreProjectTop> scriptFolder;
    /** Creates a new instance of CoreProjectTop */
    public CoreProjectTop() {}
    public CoreProjectTop(String name) {super(name);}
    public CoreProjectTop(FileHolder dir) {
        super(dir.getName());
    }   

}
