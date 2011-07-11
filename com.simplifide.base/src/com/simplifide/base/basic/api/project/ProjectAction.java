/*
 * ProjectAction.java
 *
 * Created on January 4, 2007, 8:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.basic.api.project;

import java.io.File;

import com.simplifide.base.basic.api.file.FileHolder;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;


/**
 *
 * @author awagner
 */
public interface ProjectAction {
    
    /** Creates a new File Action which is project sensitive */
    public void newFileAction(File folder);
    
    /** Creates a new Project Action which is suite sensitive */
    public void newProjectAction(File projectDirectory);
    
    /** Open Project */
    public void openProject(FileHolder projectDirectory, CoreProjectBasic basic);
    
    /** Add Basic Project */
    public void addBasicProject(Object basic);
    
    /** Close Project */
    public void closeProject(ReferenceItem<? extends CoreProjectBasic> basic);
    
    /** Create a file dialog and return the selected files */
    public File[] createProjectChooser();
    
    
    
    
}
