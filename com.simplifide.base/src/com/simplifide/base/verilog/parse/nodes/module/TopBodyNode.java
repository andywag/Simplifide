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
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;


/**
 *
 * @author Andy
 */
public class TopBodyNode extends TopASTNode{
    
    /** Creates a new instance of ModuleBodyNode */
    public TopBodyNode() {}

    
    protected void appendHdlDoc(ReferenceItem objRef, TopASTNode node) {
    	TopASTToken tok = node.getFirstLeafNode().getToken();
        if (tok.getDoc() != null && objRef.getObject() != null) {
            objRef.getObject().changeDoc(tok.getDoc());
        }
    }

  
    protected void addNoSortItem(ReferenceItem uitem, ModuleObjectNew module) {
    	 module.addReferenceItem(uitem);
    }
    
    protected void addNoSortList(NoSortList list, ModuleObjectNew module, TopASTNode node) {
        for (int i=0;i<list.getnumber();i++) {
            ReferenceItem uitem = (ReferenceItem) list.getObject(i);
            this.addNoSortItem(uitem, module);
            this.appendHdlDoc(uitem, node);
        }
    }
    
    protected void addItem(ModuleObjectNew module, ModuleObject item, TopASTNode node ) {
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

    
   

    
}
