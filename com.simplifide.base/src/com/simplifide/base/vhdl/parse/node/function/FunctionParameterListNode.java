/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.function;

import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

// ( LPAREN formal_parameter_list RPAREN )?

public class FunctionParameterListNode extends TopASTNodeGeneric<ReferenceItem<FunctionPortList>>
{

    public FunctionParameterListNode() {super();}
   
   

    public ReferenceItem<FunctionPortList> createObjectSmall(ParseContext context)
    {
        
        if (this.getNumberOfChildren() == 0) return new FunctionPortList().createReferenceItem();
        
        TopASTNode child = this.getFirstASTChild(); 
        child = child.getNextASTSibling(); // parameter_list
      
        ReferenceItem<PortList> portListRef = (ReferenceItem) child.generateModule(context);
        FunctionPortList fports = new FunctionPortList(portListRef.getObject());
        
        return fports.createReferenceItem();
                

    }
    
   
   




}
