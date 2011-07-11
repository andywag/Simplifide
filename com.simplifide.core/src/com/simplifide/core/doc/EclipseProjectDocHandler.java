/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.doc;

import java.util.List;

import org.eclipse.core.resources.IFile;

import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.core.project.EclipseBaseProject;

public class EclipseProjectDocHandler extends TopDocHandler{

    private EclipseBaseProject project;
    
    public EclipseProjectDocHandler(EclipseBaseProject project) {
        this.project = project;
    }

    public void setProject(EclipseBaseProject project) {
        this.project = project;
    }

    public EclipseBaseProject getProject() {
        return project;
    }

    @Override
    public void generateDoc() {
        IFile file = project.getDocFolder().getFile("project_index.html");
        this.createPage(file, "doc/project_page", project);
        
        List<InstanceModule> instList = project.getModuleList();
        for (InstanceModule instMod : instList) {
            InstanceModuleDocHandler handler = new InstanceModuleDocHandler(instMod,this.project);
            handler.generateDoc();
        }
        List<InstancePackage> packList = project.getPackageList();
        for (InstancePackage packMod : packList) {
            InstancePackageDocHandler handler = new InstancePackageDocHandler(packMod,this.project);
            handler.generateDoc();
        }
        
    }
    
}
