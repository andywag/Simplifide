/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor.describer;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;

import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.split.VerilogFileSplitter;
import com.simplifide.base.verilog.parse.base.VerilogParserTop;
import com.simplifide.core.source.design.DesignFile;
import com.simplifide.core.source.design.DesignFileBuilder;

public class VerilogFile extends DesignFile {

	public VerilogFile(URI location) {
		super(location);
	}

	public int getSearchType() {
		return ReferenceUtilities.REF_VERILOG_FILE;
	}

	public ParserTop getParser() {
		return new VerilogParserTop();
	}

	@Override
	protected DesignFileBuilder createBuilder() {
		return new VerilogFileCompile(this);
	}
	
	public String expandMacros() {
		Reader read;
		if (this.getEditor() != null) { // If File is Open
			String str = this.getEditor().getDocument().get();
			read = new StringReader(str);
		}
		else { // Closed File
			read = new InputStreamReader(this.getFileContents());
		}
		String macro = this.getParser().expandMacros(read, this.getParseDescriptor());
		return macro;
	}

	public void splitFiles() {
		URI uri = this.getResource().getLocationURI();
		File nfile = new File(uri);
		VerilogFileSplitter split = new VerilogFileSplitter(nfile);
		boolean worked = split.split();
	}

}
