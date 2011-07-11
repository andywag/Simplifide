package com.simplifide.base.verilog.parse.nodes.interfac;

import com.simplifide.base.core.interfac.InterfaceInstanceModule;
import com.simplifide.base.core.interfac.InterfaceTopModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.TopModule;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopBodyNode;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopDeclarationExternNode;
import com.simplifide.base.verilog.parse.nodes.moduletop.ModuleTopDeclarationNormalNode;


//interface_declaration : interface_declaration_normal | interface_declaration_external
public class ProgramDeclarationNode extends TopASTNode{

		 
    //interface_declaration_normal : interface_nonansi_header (options{greedy=true;}: timeunits_declaration )? 
    //		interface_body "endinterface" colon_identifierq
    public static class Normal extends ModuleTopDeclarationNormalNode {

		@Override
		public InstanceModule createInstanceModule(String name,
				ParseContext context) {
			return new InterfaceInstanceModule(name, context.getRefHandler().getProjectReference());
		}
    	
    }
    
    //interface_declaration_external :  "extern" interface_nonansi_header
    public static class Extern extends ModuleTopDeclarationExternNode {
    	
    }
    
    public static class Body extends ModuleTopBodyNode {

		@Override
		public TopModule createModule() {
			return new InterfaceTopModule();
		}
    	
    }
	
}
