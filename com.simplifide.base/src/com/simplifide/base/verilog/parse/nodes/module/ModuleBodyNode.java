/*
 * ModuleBodyNode.java
 *
 * Created on April 23, 2007, 2:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.module;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 *
 * @author Andy
 */
public class ModuleBodyNode extends TopBodyNode{
    
    /** Creates a new instance of ModuleBodyNode */
    public ModuleBodyNode() {}

    /*
    private void appendHdlDoc(ReferenceItem objRef, TopASTNode node) {
    	TopASTToken tok = node.getFirstLeafNode().getToken();
        if (tok.getDoc() != null && objRef.getObject() != null) {
            objRef.getObject().changeDoc(tok.getDoc());
        }
    }

  
    private void addNoSortList(NoSortList list, HardwareModule module, TopASTNode node) {
        for (int i=0;i<list.getnumber();i++) {
            ReferenceItem uitem = (ReferenceItem) list.getObject(i);
            module.addReferenceItem(uitem);
            this.appendHdlDoc(uitem, node);
        }
    }
    
    private void addItem(HardwareModule module, ModuleObject item, TopASTNode node ) {
        if (item instanceof NoSortList) {
        	this.addNoSortList((NoSortList)item, module, node);
        }
       
        else if (item instanceof ReferenceItem) {
        	ReferenceItem itemRef = (ReferenceItem) item;
        	if (itemRef.getObject() instanceof NoSortList) {
        		this.addNoSortList((NoSortList)itemRef.getObject(), module, node);
        	}
        	else {
        		if (itemRef != null) {
        			this.appendHdlDoc(itemRef, node);
        			module.addReferenceItem(itemRef);
        		}
        		
        	}
        }

    }
	*/
    
    private void setStartLocation(ParseContext context) {
   	 	ReferenceLocation loc = context.createReferenceLocation(this);
        loc.setLength(0);
        context.getRefHandler().getModuleReference().getObject().setDeclarationStartLocation(loc);
    }
    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        HardwareModule module = (HardwareModule) context.getActiveReference().getObject();
        context.getRefHandler().setModuleReference(module.createReferenceItem());
        this.setStartLocation(context);
        //context.setActiveReference(module.createReferenceItem());
        TopASTNode node = this.getFirstASTChild();
        while (node != null) {
        	ModuleObject item = (ModuleObject) node.generateModule(context);
            this.addItem(module, item, node);
        	node = node.getNextASTSibling();
        	
        }
        
        
        ReferenceLocation loc = null;
        if (this.getNumberOfChildren() > 0) loc = context.createReferenceLocation(this.getFirstASTChild());
        return module.createReferenceItemWithLocation(loc);
    }

    
}
