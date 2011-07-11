/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.doc;

import org.eclipse.core.resources.IFile;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.core.baseeditor.source.SourceFile;
import com.simplifide.core.project.EclipseBaseProject;
import com.simplifide.core.source.LocationOperations;
import com.simplifide.core.source.design.DesignFile;

public class InstanceModuleDocHandler extends TopDocHandler{

    private InstanceModule instMod;
    private EclipseBaseProject project;
    
    public InstanceModuleDocHandler(InstanceModule instMod, EclipseBaseProject project) {
        this.instMod = instMod;
        this.project = project;
    }
    
    @Override
    public void generateDoc() {
        ReferenceLocation loc = instMod.getEntityReference().getLocation();

        SourceFile sfile = LocationOperations.getSourceFile(loc);
        if (sfile instanceof DesignFile) {
            DesignFile dfile = (DesignFile) sfile;
        	dfile.getBuilder().build(BuildInterface.BUILD_HDLDOC);
        	 IFile file = project.getDocFolder().getFile(instMod.getname() + ".html");
             this.createPage(file, instMod.getBaseDocTemplateFolder() + "/doc/entity_page", instMod);
             
             dfile.getBuilder().build(BuildInterface.BUILD_CLOSE_HDLDOC);

        }
        
       
    }

}
