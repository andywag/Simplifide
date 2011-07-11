/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.core.var.types;

public class FileTypeVar extends TypeVar{

    private String filename;
    
    public FileTypeVar(String name, String filename) {
        super(name);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
    
}
