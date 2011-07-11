/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.namedec;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectExpressionItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;





/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class NameExpressionASTNode extends NameTopASTNode
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public NameExpressionASTNode() {}
    public NameExpressionASTNode(Token tok)
    {
        super(tok);
    }
 
    // Only Used To Specify Context Types for Nodes
    public void resolveContext(ParseContext context) {
    	
    	int pos = this.getChildPosition(context.getDocPosition());
    	//if (pos == 1) context.setSearchContext(new SearchContext.Signal());
    	if (pos == 3) context.setSearchContext(new SearchContext.Type());
   	
    }
    
    public String getRealText() {
    	TopASTNode child = this.getFirstASTChild();
    	String out = "";
    	while (child != null) {
    		out += child.getRealText() + " ";
    		child = child.getNextASTSibling();
    	}
    	return out;
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
       

        NoSortList list = new NoSortList("ExpressionList");
                
        TopASTNode child = this.getFirstASTChild(); // (
        child = child.getNextASTSibling();
        while (child != null)
        {
            ModuleObject obj = (ModuleObject) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT);    
            list.addObject(obj);
            child = child.getNextASTSibling(); // ,
            if (child == null) {
            	ModuleObjectExpressionItem outItem = new ModuleObjectExpressionItem("Expression", list.createReferenceItem());
                return outItem;
            }
            if (child.getRealText().equalsIgnoreCase("downto") || child.getRealText() == ("to")) {
            	TopASTNode child2 = child.getNextASTSibling();
            	ModuleObject obj2 = (ModuleObject) child2.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT);    
            	int dir = VarRange.DOWN;
                if (child.getRealText().equalsIgnoreCase("TO")) dir = VarRange.UP;
            	VarRange range = new VarRange(obj.createReferenceItem(),obj2.createReferenceItem(),dir);
                list = new NoSortList("ExpressionList");
                list.addReferenceItem(range.createReferenceItem());
                break;
            }
            
            if (child != null) child = child.getNextASTSibling();
        }
        ModuleObjectExpressionItem outItem = new ModuleObjectExpressionItem("Expression", list.createReferenceItem());
        return outItem;
    }
	@Override
	public NodePosition getNodeRange() {
		// TODO Auto-generated method stub
		return null;
	}

    

}
