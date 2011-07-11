package com.simplifide.base.verilog.parse.context;

import com.simplifide.base.core.reference.ReferenceHandler;
import com.simplifide.base.sourcefile.antlr.ParseDescriptor;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParseContextUsages;

public class VerilogParseContext extends ParseContext{

	public VerilogParseContext(ParseDescriptor desc, int mode) {
		super(desc,mode);
	}
	
	 protected ReferenceHandler createReferenceHandler() {
	    	return new VerilogReferenceHandler();
	 }
	
	public static class Usages extends ParseContextUsages {
		public Usages(ParseDescriptor desc,int mode) {
			super(desc,mode);
		}
	}
	
}
