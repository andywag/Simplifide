/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.describer;

import java.net.URI;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.core.error.ErrorWrapper;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileBuilder;
import com.simplifide.core.source.design.FileTemplateHandler;
import com.simplifide.core.vhdl.error.VhdlErrorWrapper;

public class VhdlFileCompile extends DesignFileBuilder{

    public VhdlFileCompile(DesignFile designFile) {
        super(designFile);
    }

    @Override
    protected ErrorWrapper createErrorWrapper(TopError error) {
        return new VhdlErrorWrapper(error);
    }
    
    protected String getPostFix() {
    	return ".vhdl";
    }

	@Override
	public FileTemplateHandler createTemplateHandler(DesignFile dfile) {
		return new VhdlFileTemplateHandler(dfile);
	}

	@Override
	public ParseDescriptor createParseDescriptor(String name, URI uri) {
		return new ParseDescriptor(name, uri);
	}

}
