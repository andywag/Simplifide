/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.vhdl.describer;


import java.io.File;
import java.net.URI;

import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.split.VhdlFileSplitter;
import com.simplifide.base.vhdl.parse.base.VhdlParserTop;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileBuilder;

public class VhdlFile extends DesignFile{

	public VhdlFile(URI location) {
		super(location);
	}
	
	public int getSearchType() {
		return ReferenceUtilities.REF_VHDL_FILE;
	}
	
	public ParserTop getParser() {
		return new VhdlParserTop();
	}

    @Override
    protected DesignFileBuilder createBuilder() {
        return new VhdlFileCompile(this);
    }
    
	public void splitFiles() {
		URI uri = this.getResource().getLocationURI();
		File nfile = new File(uri);
		VhdlFileSplitter split = new VhdlFileSplitter(nfile);
		boolean worked = split.split();
	}
	
	
	
}
