/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/

package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.ModuleObjectRangeListInitial;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/*
 * VariableListNode.java
 *
 * Created on April 23, 2007, 4:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */




/**
 *
 * @author Andy
 * @todo : Fix the No Sort List Return
 */

// list_of_variable_identifiers : variable_ident ( options{greedy=true;}: COMMA variable_ident)*
public class VariableListNode extends TopASTNodeGeneric<NoSortList<ModuleObjectRangeListInitial>>{

   
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of VariableListNode */
    public VariableListNode() {}


    public NoSortList<ModuleObjectRangeListInitial> createObjectSmall(ParseContext context) {
        VariableIdentNode child = (VariableIdentNode) this.getFirstASTChild();
        NoSortList list = new NoSortList("Holder");
        while (child != null) {
        	ReferenceItem<ModuleObjectRangeListInitial> ranR = child.createObject(context);
            list.addObject(ranR);
            TopASTNode child2 = child.getNextASTSibling();
            if (child2 == null) break;
            child = (VariableIdentNode) child2.getNextASTSibling();
        }
        return list;
    }
    


}
