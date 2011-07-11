/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.doc;

import org.eclipse.core.resources.IFile;

import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.core.project.EclipseBaseProject;

public class InstancePackageDocHandler extends TopDocHandler{

    private InstancePackage instPackage;
    private EclipseBaseProject project;
    
    public InstancePackageDocHandler(InstancePackage instMod, EclipseBaseProject project) {
        this.instPackage = instMod;
        this.project = project;
    }
    
    @Override
    public void generateDoc() {
       
        IFile file = project.getDocFolder().getFile(this.instPackage.getname() + ".html");
        this.createPage(file, instPackage.getBaseDocTemplateFolder() + "/doc/package_page", this.instPackage);
    }

}
