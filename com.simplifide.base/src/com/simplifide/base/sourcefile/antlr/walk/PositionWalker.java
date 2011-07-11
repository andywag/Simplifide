/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.walk;
import java.util.ArrayList;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 5, 2004
 * Time: 3:09:05 PM
 * To change this template use File | Settings | File Templates.
 */

/** Contains Position Walking Plus Error Addition for Efficiency */

public class PositionWalker
{
    /** @todo : Need Node Trimming to remove redundant dummy nodes */
    public static void walkIndex(TopASTNode node)
    {
        pruneTree(node);
        //walkSmall(node,NodePosition.ZERO);
    }

    public static void pruneTree(TopASTNode node)
    {
        if (node == null) return;
        TopASTNode child = (TopASTNode) node.getFirstChild();
        
        TopASTNode last = null;
        while (child != null)
        {
            if (last == null)
            {
                if (child.canPrune())
                {
                    TopASTNode newval = child.prunedNode();
                    newval.setNextSibling(child.getNextASTSibling());
                    node.setFirstChild(newval);
                    last = newval;
                }
                else
                {
                    last = child;
                }
            }
            else
            {
                if (child.canPrune())
                {
                    TopASTNode newval = child.prunedNode();
                    newval.setNextSibling(child.getNextASTSibling());
                    last.setNextSibling(newval);
                    last = newval;
                }
                else
                {
                    last = child;
                }
            }
            pruneTree(last);
            child = child.getNextASTSibling();
        }
    }
    
    /** Deprecated and should be deleted. Do not try to reuse*/
    public static NodePosition walkSmall(TopASTNode node,NodePosition start)
    {
        if (node == null) return NodePosition.ZERO;
        TopASTNode child = (TopASTNode) node.getFirstChild();

        if (child == null)
        {
            TopASTToken tok = node.getToken();
            if (tok != null) 
            {
                NodePosition pos = tok.getNodePosition();
            //    node.setPosition(pos);
                return pos;
            }
            //node.setPosition(start.copy()); // May want to keep it null
            return null;
        }
        while (child != null)
        {
            NodePosition upos = (node.getPosition() == null) ? start : node.getPosition();
            NodePosition cpos = walkSmall(child,upos);
            //if (node.getPosition() == null && cpos != null) node.setPosition(cpos.copy());
            //else if (cpos != null) node.getPosition().setEndPos(cpos.getEndPos());
            child = (TopASTNode) child.getNextSibling();
        }
        //if (node.getPosition() == null) node.setPosition(start.copy());
        return node.getPosition();
    }
    

    
    public static java.util.ArrayList<TopASTNode> getPathTo(TopASTNode node, int offset)
    {
        java.util.ArrayList list = new java.util.ArrayList();
        if (node == null) return list;
        
        list.add(node);
        
        TopASTNode child = node.getFirstASTChild();
        while (child != null)
        {
            NodePosition pos = child.getPosition();
            if (pos != null)
            {
                int st = pos.getStartPos();
                int et = pos.getEndPos();
            
                if ((offset >= st) && (offset <= et)) 
                {
                    list.addAll(getPathTo(child,offset));
                    return list;
                }
            }
            child = child.getNextASTSibling();
        }
        
        return list;
    }
    
    public static void updatePathContext(TopASTNode node, int offset, ParseContext context)
    {
    	context.setDocPosition(offset);
        ArrayList list = getPathTo(node,offset);  
        for (int i=0;i<list.size();i++)
        {
            TopASTNode node1 = (TopASTNode) list.get(i);
            node1.resolveContext(context);
        }
    }

  



}
