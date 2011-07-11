package com.simplifide.base.verilog.parse.nodes.moduletop;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.interfac.InterfaceEntity;
import com.simplifide.base.core.interfac.ProgramEntity;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.verilog.core.module.VerilogModInstanceDefault;


// interface_nonansi_header : "interface" lifetime identifier (interface_header_dot_star | interface_header_normal) SEMI

public abstract class ModuleTopNoAnsiHeaderNode extends TopASTNodeGeneric<ReferenceItem<Entity>>{

		public abstract Entity createEntity(String name);
	
		public String getEntityName() {
			TopASTNode child = this.getFirstASTChild();
			child = child.getNextASTSibling();
			child = child.getNextASTSibling();
			return child.getRealText();
		}
		
	    private void appendHdlDoc(Entity entity) {
	    	TopASTToken tok = this.getFirstLeafNode().getToken();
	        if (tok.getDoc() != null) {
	            entity.changeDoc(tok.getDoc());
	        }
	    }
		
		public ReferenceItem<Entity> createObjectSmall(ParseContext context) {
			TopASTNode intNode = this.getFirstASTChild(); // "interface"
        	TopASTNode lifeNode = intNode.getNextASTSibling(); // lifetime
        	TopASTNode nameNode = lifeNode.getNextASTSibling();
        	TopASTNode portNode = nameNode.getNextASTSibling();
        	
        	String intName = nameNode.getRealText();
        	
        	ReferenceItem<ModInstanceDefault> modR = (ReferenceItem<ModInstanceDefault>) portNode.generateModule(context);
        	Entity ent = this.createEntity(intName);
        	ent.setConnectRef(modR);
        	this.appendHdlDoc(ent);
        	return ent.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
		}
	
	
	//interface_header_dot_star : LPAREN DOT STAR RPAREN
    public static class DotStar extends TopASTNode {
    	
    	public boolean canFold() {return true;}
    	public TopObjectBase generateModuleSmallNew(ParseContext context) {
        	
        	VerilogModInstanceDefault.DotStar dot = new VerilogModInstanceDefault.DotStar();
        	return dot.createReferenceItemWithLocation(context.createReferenceLocation(this));
        }
    }
    
    public static class Interface extends ModuleTopNoAnsiHeaderNode {
    	public Entity createEntity(String name) {
    		return new InterfaceEntity(name);
    	}
    }
    public static class Program extends ModuleTopNoAnsiHeaderNode {
    	public Entity createEntity(String name) {
    		return new ProgramEntity(name);
    	}
    }
    
	
}
