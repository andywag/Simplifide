package com.simplifide.core.vhdl.describer;

import java.net.URI;

import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.vhdl.parse.base.VhdlParserTop;
import com.simplifide.core.source.design.DesignFileAlone;
import com.simplifide.core.source.design.DesignFileBuilder;

public class VhdlFileAlone extends DesignFileAlone{

	public VhdlFileAlone(URI fileStore) {
		super(fileStore);
	}

	public int getSearchType() {
		return ReferenceUtilities.REF_VERILOG_FILE;
	}

	public ParserTop getParser() {
		return new VhdlParserTop();
	}

	@Override
	protected DesignFileBuilder createBuilder() {
		return new VhdlFileCompile(this);
	}
	
}
