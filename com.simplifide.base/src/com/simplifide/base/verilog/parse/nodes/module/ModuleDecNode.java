/*
 * ModuleDecNode.java
 *
 * Created on April 22, 2007, 12:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.module;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.verilog.core.module.VerilogModInstanceDefault;
import com.simplifide.base.verilog.parse.nodes.instance.ParameterPortListNode;
import com.simplifide.base.verilog.parse.nodes.portnew.ModulePortListNode;



/**
 *
 * @author Andy
 */

// module_head : ("extern")? ("module" | "macromodule") lifetime IDENTIFIER parameter_port_list port_list SEMI

public class ModuleDecNode extends TopASTNode{
    
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ModuleDecNode */
    public ModuleDecNode() {}


    public void resolveContext(ParseContext context) {
        ModuleObject obj = context.getActiveReference().getObject();
        if (obj instanceof HardwareModule) {
        	HardwareModule mod = (HardwareModule) obj;
        	InstanceModule imod = (InstanceModule) mod.getInstModRef().getObject();
        	context.setActiveReference(imod.getEntityReference());
        }
    }
    
    public TopASTNode formatBase() {return this;}
    
	public void format(FormatPosition position) {
		NodePosition pos = this.getPosition();
		FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
		newPosition.setIndent(0);
		super.format(newPosition);
	}
    
    public TopASTNode getPortListNode() {
    	TopASTNode ch = this.getFirstASTChild(); // extern
    	ch = ch.getNextASTSibling(); // mod
    	if (ch != null) ch = ch.getNextASTSibling(); // lif
    	if (ch != null) ch = ch.getNextASTSibling(); // id
    	if (ch != null) ch = ch.getNextASTSibling(); // paramlist
    	if (ch != null) ch = ch.getNextASTSibling(); // portlist
    	if (ch != null) ch = ch.getNextASTSibling(); // (
    	return ch;
    }
    
    public String getEntName() {
        TopASTNode node = this.getFirstASTChild(); // externQ
        node = node.getNextASTSibling(); // module
        node = node.getNextASTSibling(); // lifetime
        node = node.getNextASTSibling(); // Identifier
        String text = node.getRealText(); 
        return text;
    }

    private void appendHdlDoc(Entity entity) {
    	TopASTToken tok = this.getFirstLeafNode().getToken();
        if (tok.getDoc() != null) {
            entity.changeDoc(tok.getDoc());
        }
    }

    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	TopASTNode child = this.getFirstASTChild(); // extern
    	child = child.getNextASTSibling(); // (module | macromodule)
    	child = child.getNextASTSibling(); // lifetime
    	TopASTNode nameNode = child.getNextASTSibling(); 
    	
    	ParameterPortListNode parListNode = (ParameterPortListNode) nameNode.getNextASTSibling(); // Parameter List Node
    	ModulePortListNode.Top portListNode = (ModulePortListNode.Top) parListNode.getNextASTSibling(); // Port List Node
    	
        String entName = nameNode.getRealText();
        ReferenceLocation loc = context.createReferenceLocation(nameNode);
        Entity entity = new Entity(entName);
        this.appendHdlDoc(entity);
        
        // Attach Locations to the entity to store the positions where ports
        // Can be added
        entity.setLastPortLocation(portListNode.getLastPortLocation(context));
        entity.setLastGenericLocation(parListNode.getLastPortLocation(context));
        
        //context.setActiveReference(entity.createReferenceItemWithLocation(loc));
        
        ReferenceItem<PortList> paramListRef = (ReferenceItem<PortList>) parListNode.generateModule(context);
        if (paramListRef != null) {
        	paramListRef.setname("Parameters");
        	if (paramListRef.getObject() != null) paramListRef.getObject().setname("Parameters");
        }
        
        ReferenceItem<PortList> portListRef = (ReferenceItem<PortList>) portListNode.generateModule(context);
        ModInstanceDefault def = new VerilogModInstanceDefault(entName,paramListRef,portListRef);
        ReferenceItem<ModInstanceDefault> modRef = def.createReferenceItemWithLocation(context.createReferenceLocation(this));
        entity.setConnectRef(modRef);
        return entity.createReferenceItemWithLocation(loc);
    }

    
}
