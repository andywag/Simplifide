package com.simplifide.base.verilog.parse.context;

import java.net.URI;

import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParseContextUsages;

public class VerilogParseDescriptor extends ParseDescriptor{

	public VerilogParseDescriptor(String name, URI uri) {
		super(name, uri);
	}
	
    protected ParseContext createParseContext(int mode) {
    	return new VerilogParseContext(this,mode);
    }
    protected ParseContextUsages createParseContextUsages(int mode) {
    	return new VerilogParseContext.Usages(this,mode);
    }

}
