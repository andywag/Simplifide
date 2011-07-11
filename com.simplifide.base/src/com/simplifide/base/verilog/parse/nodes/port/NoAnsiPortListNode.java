/*
 * NoAnsiPortList.java
 *
 * Created on April 23, 2007, 9:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.port;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;
import com.simplifide.base.verilog.core.var.VerilogVar;



/**
 *
 * @author Andy
 */
public class NoAnsiPortListNode extends TopASTNode{
    
   
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of NoAnsiPortList */
    public NoAnsiPortListNode() {}

   
    
    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        PortList portList = new PortList("PortList");
        TopASTNode child = this.getFirstASTChild();
        
        
        int i = 0;
        while (child != null) {
        	ReferenceLocation loc = context.createReferenceLocation(child);
            String text = child.getRealText();
            SystemVar tvar = new VerilogVar(text,null,null);
            VerilogPortDefault def = new VerilogPortDefault(tvar.createReferenceItem());
            def.setPortNumber(i);
            portList.addObject(def.createReferenceItemWithLocation(loc));
            
            child = child.getNextASTSibling();
            if (child != null) child = child.getNextASTSibling();
            i = i + 2;
        }
        return portList.createReferenceItem();
    }

    
}
