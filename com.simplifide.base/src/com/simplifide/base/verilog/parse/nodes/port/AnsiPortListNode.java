/*
 * AnsiPortList.java
 *
 * Created on April 24, 2007, 1:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.port;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 *
 * @author Andy
 */
public class AnsiPortListNode extends TopASTNode{
     
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of AnsiPortList */
    public AnsiPortListNode() {}

    
    
    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        PortList ports = new PortList("Ports");
        TopASTNode child = this.getFirstASTChild();
        int i = 0;
        
        while (child != null) {
            NoSortList outList = (NoSortList) child.generateModule(context);
            for (int j=0;j<outList.getnumber();j++) {
                ReferenceItem item = (ReferenceItem) outList.getObject(j);
                ports.addObject(item);
            }
            child = child.getNextASTSibling();
            if (child != null) child = child.getNextASTSibling();
            i = i + 2;
        }
        return ports.createReferenceItem();
    }

    
}
