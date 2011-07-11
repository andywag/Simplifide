package com.simplifide.base.verilog.parse.nodes.interfac;

import com.simplifide.base.basic.struct.ModuleObjectRangeListInitial;
import com.simplifide.base.core.interfac.ModPort;
import com.simplifide.base.core.interfac.ModPortItem;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.var.VerilogVar;
import com.simplifide.base.verilog.parse.nodes.portnew.AnsiPortDeclarationNode;

// modport_declaration : "modport" modport_item (COMMA modport_item)* SEMI

public class ModPortDeclarationNode extends TopASTNodeGeneric<ReferenceItem<ModPort>>{

	public boolean canFold() {return true;}
	public ReferenceItem<ModPort> createObjectSmall(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		ReferenceItem ref = (ReferenceItem) child.generateModule(context);
		return ref;
	}
	// modport_item : identifier LPAREN modport_ports_declaration (COMMA modport_ports_declaration )* RPAREN
	public static class Item extends TopASTNodeGeneric<ReferenceItem<ModPort>> {
		
		public ReferenceItem<ModPort> createObjectSmall(ParseContext context) {
			PortList ports = new PortList();
			TopASTNode nameNode = this.getFirstASTChild();
			ModPort port = new ModPort(nameNode.getRealText());
			TopASTNode parenNode = nameNode.getNextASTSibling(); // (
			TopASTNode decNode = parenNode.getNextASTSibling();
			OperatingTypeVar last = null;
			while (decNode != null) {
				ReferenceItem<ModPortItem> portR = (ReferenceItem<ModPortItem>) decNode.generateModule(context);
				// Handle Last Operating Type Var
				OperatingTypeVar type = portR.getObject().getOperatingVar();
				if (type == null) portR.getObject().setOperatingVar(last);
				else last = type;
				port.addObject(portR);
				ports.addObject(portR);
				decNode = decNode.getNextASTSibling();
				if (decNode != null) decNode = decNode.getNextASTSibling();
			}
			port.setPortsR(ports.createReferenceItem());
			return port.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
			
		}
	}
	// modport_ports_declaration : modport_declaration_item;
	//modport_declaration_item : modport_declaration_prefix (modport_dot | modport_hier)
	public static class DecItem extends TopASTNodeGeneric<ReferenceItem<ModPortItem>> {
		public ReferenceItem<ModPortItem> createObjectSmall(ParseContext context) {
			DecPrefix prefixNode = (DecPrefix) this.getFirstASTChild();
			TopASTNode dataNode = prefixNode.getNextASTSibling();
			
			OperatingTypeVar op = prefixNode.createObject(context);
			ReferenceItem<ModuleObjectRangeListInitial> rang = (ReferenceItem<ModuleObjectRangeListInitial> )dataNode.generateModule(context);
			ReferenceItem<SystemVar> varR = context.getActiveReference().findReference(rang.getname(), ReferenceUtilitiesInterface.REF_SYSTEMVAR);
			
			
			
			
			if (varR == null) {
				SystemVar tvar = new VerilogVar(rang.getname(),null,op);
				varR = tvar.createReferenceItemWithLocation(rang.getLocation());
			}
			
			ModPortItem port = new ModPortItem(varR);
			port.setOperatingVar(op);
			ReferenceItem<ModPortItem> portR = port.createReferenceItemWithLocation(rang.getLocation());
			return portR;
		}
	}
	
	//modport_declaration_prefix : ("clocking" | "import" | "export" | port_direction)?						  
	public static class DecPrefix extends TopASTNodeGeneric<OperatingTypeVar> {
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
	//modport_hier : identifier (LBRACK (expression)?  RBRACK DOT identifier)?
	public static class DecHier extends TopASTNodeGeneric<ReferenceItem<ModuleObjectRangeListInitial>> {
		public ReferenceItem<ModuleObjectRangeListInitial> createObjectSmall(ParseContext context) {
			TopASTNode identNode = this.getFirstASTChild();
			
			if (context.getPass() == ParseContext.BUILD_FIND_USAGES) {
				identNode.generateModule(context);
			}
			
			ModuleObjectRangeListInitial m = new ModuleObjectRangeListInitial(identNode.getRealText(),null,null);
			return m.createReferenceItemWithLocation(context.createReferenceLocation(identNode));
		}
	}
	//modport_dot  : DOT identifier LPAREN (expression)? RPAREN
	public static class DecDot extends AnsiPortDeclarationNode.Dot {
		
	}
	
}
