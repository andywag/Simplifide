/*
 * TypeDefNode.java
 *
 * Created on April 23, 2007, 1:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.types;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;





/**
 *
 * @author Andy
 */
public class TypeDefNode extends TopASTNode{
    
    /** Creates a new instance of TypeDefNode */
    public TypeDefNode() {}

    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
        return child.generateModule(context);
    }

    
}
