/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.describer;

import java.net.URI;

import com.simplifide.base.core.error.err.TopError;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.verilog.parse.context.VerilogParseDescriptor;
import com.simplifide.core.error.ErrorWrapper;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileBuilder;
import com.simplifide.core.source.design.FileTemplateHandler;
import com.simplifide.core.ui.preference.PreferenceConstants;
import com.simplifide.core.verilog.error.VerilogErrorWrapper;

public class VerilogFileCompile extends DesignFileBuilder{

    public VerilogFileCompile(DesignFile designFile) {
        super(designFile);
    }

    @Override
    protected ErrorWrapper createErrorWrapper(TopError error) {
        return new VerilogErrorWrapper(error);
    }

    protected String getPostFix() {
    	return PreferenceConstants.POSTFIX_VERILOG;
    }

	@Override
	public FileTemplateHandler createTemplateHandler(DesignFile dfile) {
		return new VerilogFileTemplateHandler(dfile);
	}

	@Override
	public ParseDescriptor createParseDescriptor(String name, URI uri) {
		return new VerilogParseDescriptor(name,uri); 
	}
    
   
}
