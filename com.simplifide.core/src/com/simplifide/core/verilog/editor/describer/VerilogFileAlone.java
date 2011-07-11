package com.simplifide.core.verilog.editor.describer;

import java.net.URI;

import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.ParserTop;
import com.simplifide.base.verilog.parse.base.VerilogParserTop;
import com.simplifide.core.source.design.DesignFileAlone;
import com.simplifide.core.source.design.DesignFileBuilder;

public class VerilogFileAlone extends DesignFileAlone{

	public VerilogFileAlone(URI fileStore) {
		super(fileStore);
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
	
}
