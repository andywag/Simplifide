package com.simplifide.base.verilog.parse.nodes.portnew;

import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.var.VerilogVar;

public class AnsiPortHeaderNode extends TopASTNodeGeneric<ReferenceItem<SystemVar>>{

	// ansi_port_headerQ : (options{greedy=true;} : ansi_port_header)?
	public static class Header extends AnsiPortHeaderNode {
		public ReferenceItem<SystemVar> createObjectSmall(ParseContext context) {
			AnsiPortHeaderNode child = (AnsiPortHeaderNode) this.getFirstASTChild();
			
			if (child != null) return child.createObject(context);
			return null;
		}
	}
	// ansi_port_header_normal    : port_directionQ data_type_or_implicit 
	public static class Normal extends AnsiPortHeaderNode {
		public ReferenceItem<SystemVar> createObjectSmall(ParseContext context) {
			Direction ioNode = (Direction) this.getFirstASTChild();
			TopASTNode typeNode = ioNode.getNextASTSibling();
			
			ReferenceItem<TypeVar> typeR = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
			OperatingTypeVar op = ioNode.createObject(context);
			
			VerilogVar var = new VerilogVar("",typeR,op);
			return var.createReferenceItem();
			
		}
	}
	
	// ansi_port_header_interface : "interface" (options{greedy=true;} : DOT modport_identifier)?
	/* TODO Not Supported */
	public static class Interface extends AnsiPortHeaderNode {
		public ReferenceItem<SystemVar> createObjectSmall(ParseContext context) {
			return null;
		}
	}
	
	// port_direction : ("input" | "inout" | "output" | ("const")? "ref"); 	

	public static class Direction extends TopASTNodeGeneric<OperatingTypeVar> {
		
		public OperatingTypeVar createObjectSmall(ParseContext context) {
			TopASTNode firstNode = this.getFirstASTChild();
			if (firstNode == null) return null;
			
			String text = firstNode.getRealText();
			if (text.equalsIgnoreCase("input")) return OperatingTypeVar.IOVar.TYPE_INPUT;
			else if (text.equalsIgnoreCase("output")) return OperatingTypeVar.IOVar.TYPE_OUTPUT;
			else if (text.equalsIgnoreCase("inout")) return OperatingTypeVar.IOVar.TYPE_INOUT;
			else return OperatingTypeVar.IOVar.TYPE_REF;
			
			
		}
	}
	
}
