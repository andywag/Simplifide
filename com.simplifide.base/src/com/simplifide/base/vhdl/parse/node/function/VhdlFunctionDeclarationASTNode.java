/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.function;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.newfunction.FunctionPortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlFunctionDeclarationASTNode extends FunctionDeclarationTopNode
{

	public VhdlFunctionDeclarationASTNode() {super();}
   

    public boolean canFold() {
    	return false;
    }
    
    /** Method used to find the context of the node, used for completion and navigation operations */
    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        this.resolveContext(context);
        TopASTNode node = this.getFirstASTChild();
        node = node.getNextASTSibling();
        node = node.getNextASTSibling();
        NodePosition npos = node.getPosition();
        if (pos > npos.getStartPos() && pos < npos.getEndPos()) return context.getActiveReference();
        return null;
    }
     
    public ReferenceItem<FunctionDeclaration> createObjectSmall(ParseContext context)
    {
        // Node Handling
    	TopASTNode child = this.getFirstASTChild(); // pure/impure
        child = child.getNextASTSibling(); // function (0)
        TopASTNode nameNode = child.getNextASTSibling(); // function name (1)
        FunctionParameterListNode paramNode = (FunctionParameterListNode)nameNode.getNextASTSibling();
        child = paramNode.getNextASTSibling(); // return (3)
        TopASTNode typeNode = child.getNextASTSibling(); // type (4)
        
        // Object Generation
        ReferenceLocation loc = context.createReferenceLocation(nameNode);
        String funcName = nameNode.getRealText();
        ReferenceItem<FunctionPortList> portsR = paramNode.createObject(context);
        
        ReferenceItem<TypeVar> type = (ReferenceItem) typeNode.generateSearchTypeNew(context,
        		ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_TYPEVAR);
        FunctionDeclaration functionDec = new FunctionDeclaration(funcName,type,portsR);
        //functionDec.setVerDoc(comment);
        this.handleDoc(functionDec);

        return functionDec.createReferenceItemWithLocation(loc);

    }
    
   
   




}
