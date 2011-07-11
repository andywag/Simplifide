package com.simplifide.base.verilog.parse.nodes;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.var.realvars.SystemParameter;
import com.simplifide.base.sourcefile.antlr.node.NumberASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class VerilogNumberNode extends NumberASTNode{


	 
	
	 public TopObjectBase generateModuleSmallNew(ParseContext context)
	 {
		 String text = this.getText();
		 String[] ustr = text.split("'");
		 
		 String type;
		 String number;
		 int width = -1;
		 if (ustr.length == 1) { // Either number or `function
			 SystemParameter par = new SystemParameter(this.getText(),SystemParameter.RADIX_DECIMAL);
			 return par.createReferenceItem();
		 }
		 else {
			if (ustr[0].length() == 0) width = -1;
			else width = Integer.parseInt(ustr[0]);
			type = ustr[1].substring(0,1);
			number = ustr[1].substring(1);
			
			int rad;
			if (type.equalsIgnoreCase("s")) {
				type = ustr[1].substring(1,2);
				number = ustr[1].substring(2);
			}
			if (type.equalsIgnoreCase("b")) {
				rad = SystemParameter.RADIX_BINARY;
			}
			else if (type.equalsIgnoreCase("o")) {
				rad = SystemParameter.RADIX_OCTAL;
			}
			else if (type.equalsIgnoreCase("d")) {
				rad = SystemParameter.RADIX_DECIMAL;
			}
			else {
				rad = SystemParameter.RADIX_HEX;
			}
			SystemParameter par;
			if (width == -1) {
				par = new SystemParameter(number,rad);
			}
			else {
				par = new SystemParameter.Width(number,rad,width);
			}
			return par.createReferenceItem();
		 }
		 

	 }
	 
	 
	
}
