/*
 * CoreProjectHolder.java
 *
 * Created on January 4, 2007, 1:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.core.project;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;


/**
 *
 * @author awagner
 *
 * Class used for gui to show projects inside the suite
 */
public class CoreProjectHolder extends ModuleObject{
    
    public static String COMMAND_REMOVE = "Remove";
    
    private ReferenceItem<? extends CoreProjectBasic> project;
    private CoreProjectSuite suite;
    /** Creates a new instance of CodeGenProjectHolder */
    public CoreProjectHolder() {}
    public CoreProjectHolder(ReferenceItem<? extends CoreProjectBasic> project, CoreProjectSuite suite) {
        super("Holder");
        this.project = project;
        this.setSuite(suite);
    }
    
    public String getDisplayName() {return project.getDisplayName();}
    
    
    public int getSearchType() {
        return ReferenceUtilities.REF_CORE_PROJECT_HOLDER_ITEM;
    }
    
   
    
    
    public ReferenceItem<? extends CoreProjectBasic> getProject() {
        return project;
    }
    
    public void setProject(ReferenceItem<? extends CoreProjectBasic> project) {
        this.project = project;
    }
    
    
    public CoreProjectSuite getSuite() {
        return suite;
    }
    
    public void setSuite(CoreProjectSuite suite) {
        this.suite = suite;
    }
    
}
