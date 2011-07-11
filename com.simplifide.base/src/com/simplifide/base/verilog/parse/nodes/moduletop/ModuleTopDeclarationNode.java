package com.simplifide.base.verilog.parse.nodes.moduletop;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


//interface_declaration : interface_declaration_normal | interface_declaration_external
public class ModuleTopDeclarationNode extends TopASTNode{

		
	

	
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	return this.getFirstASTChild().generateModule(context);
    }
    
   
    
    //interface_declaration_external :  "extern" interface_nonansi_header
    public static class Extern extends TopASTNode {
    	public boolean canFold() {return true;}
    	public TopObjectBase generateModuleSmallNew(ParseContext context) {
        	TopASTNode child = this.getFirstASTChild();
        	TopASTNode headerNode = child.getNextASTSibling();
        	
        	ReferenceItem<ModInstanceDefault> modR = (ReferenceItem<ModInstanceDefault>)headerNode.generateModule(context);
        	return null;
        }
    }
	
}
