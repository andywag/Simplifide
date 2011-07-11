/*******************************************************************************
 * Copyright (c) 1995/2004 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.core.verilog.editor;


import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.SingleLineRule;

import com.simplifide.core.baseeditor.color.ColorManager;
import com.simplifide.core.baseeditor.color.SourceScanner;

public class VerilogScanner extends SourceScanner{

	public static String[] keywords = new String[] {"module","endmodule","package",
	"endmodule",
    "module",
    "macromodule",
    "table",
    "endtable",
    "task",
    "endtask",
    "function",
    "endfunction",
    "integer",
    "real",
    "tri",
    "wand",
    "triand",
    "wor",
    "trior",
    "trireg",
    "scalared",
    "vectored",
    "assign",
    "defparam",
    "small",
    "medium",
    "large",
    "and",
    "nand",
    "or",
    "nor",
    "xor",
    "xnor",
    "buf",
    "not",	
    "pulldown",
    "pullup",
    "nmos",
    "rnmos",
    "pmos",
    "rpmos",
    "cmos",
    "rcmos",
    "tran",
    "rtran",
    "initial",
    "always",
    "if",
    "else",
    "endcase",
    "default",
    "case",
    "casez",
    "casex",
    "forever",
    "repeat",
    "while",
    "for",
    "foreach",
    "wait",
    "disable",
    "begin",
    "end",
    "deassign",
    "force",
    "release",
    "specify",
    "endspecify",
    "specparam",
    "posedge",
    "negedge",
    "edge",
    "package",
    "endpackage",
    "typedef",
    "enum",
    "const",
    "localparam",
    "input",
    "output",
    "inout",
    "bit",
    "byte",
    "int",
    "shortint",
    "longint",
    "reg",
    "wire",
    "structure",
    "union",
    "packed",
    "signed",
    "unsigned",
    "logic",
    "import",
    "this",
    "super",
    "parameter",
    "localparam",
    "param",
    "ifdef",
    "ifndef",
    "define",
    "endif",
    "include",
    "supply0",
    "supply1",
    "generate",
    "endgenerate",
    "genvar",
    "primitive",
    "endprimitive",
    "string",
    "struct",
    "union",
    "packed",
    "tagged",
    "enum",
    "event",
    "time", 
    "shortreal",
    "real",
    "realtime",
    "extends",
    "class",
    "endclass",
    "const",
    "extern",
    "function",
    "new",
    "static",
    "constraint",
    "protected",
    "local",
    "rand",
    "randc",
    "void",
    "super",
    "package",
    "endpackage",
    "program",
    "endprogram",
    "const",
    "ref",
    "always_comb",
    "always_latch",
    "always_ff",
    "final",
    "iff",
    "chandle",
    "event",
    "interface",
    "endinterface",
    "alias",
    "default",
    "bind",
    "inside",
    "while",
    "assert",
    "assume",
    "cover",
    "expect",
    "virtual",
    "fork",
    "join",
    "join_any",
    "modport",
    "timescale",
    "ns",
    "ps",
    "return",
    "null",
    "break",
    "with",
    "randomize",
    "bool",
    "dist",
    "$left",
    "$right",
    "$low",
    "$high", 
    "$increment", 
    "$size", 
    "$dimensions",
    "clocking",
    "endclocking",
    "property",
    "endproperty",
    "covergroup",
    "endgroup",
    "unique",
    "pure",
    "sequence",
    "endsequence"};
	
	public VerilogScanner(ColorManager provider) {
		super(provider);
	}
	
	@Override
	protected String[] getKeywords() {
		return keywords;
	}

	@Override
	public IPredicateRule getSingleLineCommentRule(IToken tok) {
		return new EndOfLineRule("//", tok);
	}

	@Override
	public IPredicateRule getStringRule(IToken tok) {
		return new SingleLineRule("\"", "\"", tok, '\\');
	}
	
	public IPredicateRule getStringRule2(IToken tok) {
		//return new SingleLineRule("'", "'", tok, '\\');
		return null;
	}

	

	@Override
	public IPredicateRule getSingleHdlRule(IToken tok) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IPredicateRule getDefinedRule(IToken tok) {
		return new DefineRule(tok);
	}

	@Override
	protected IPredicateRule getAltNumberRule(IToken tok) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
