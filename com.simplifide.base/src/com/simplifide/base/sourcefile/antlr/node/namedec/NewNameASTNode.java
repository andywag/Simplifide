/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.namedec;

import antlr.Token;

import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.finder.ModuleObjectFindItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;




/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 17, 2004
 * Time: 11:47:24 AM
 * To change this template use File | Settings | File Templates.
 */

public class NewNameASTNode extends IdentTopASTNode {
    
   
    private static final long serialVersionUID = 1L;

    public NewNameASTNode() {}
    public NewNameASTNode(Token tok) {
        super(tok);
    }
    
    /** Returns the location of the last node of this child. Very very kludgey*/
    public ReferenceLocation getLastLocation(ParseContext context) {
    	if (this.getNumberOfChildren() == 1) {
    		return context.createReferenceLocation(this);
    	}
    	TopASTNode child = this.getFirstASTChild();
    	TopASTNode lastChild = child;
    	while (child != null) {
    		lastChild = child;
    		child = child.getNextASTSibling();
    	}
    	return context.createReferenceLocation(lastChild.getFirstASTChild().getNextASTSibling());
    }
    
    @Override
    protected ReferenceLocation resolveExactUsageLocation(ParseContext context, ReferenceItem item) {
        TopASTNode child = this.getFirstASTChild();
        String utext = this.getRealText().toLowerCase();
        String stext = item.getname().toLowerCase();
        int index = utext.indexOf(stext);
        
        ReferenceLocation loc = context.createReferenceLocation(this);
        loc.setDocPosition(loc.getDocPosition() + index);
        loc.setLength(stext.length());
        
        return loc;
    }
    
    protected ReferenceLocation checkInternalLocation(ParseContext context, ReferenceItem item) {
        TopASTNode child = this.getFirstASTChild();
        String utext = this.getRealText().toLowerCase();
        String stext = item.getname().toLowerCase() + ".";
        
        int index = utext.indexOf(stext);
        if (index >= 0) {
            ReferenceLocation loc = context.createReferenceLocation(this);
            loc.setDocPosition(loc.getDocPosition() + index);
            loc.setLength(stext.length()-1);
            return loc;
            
        }
        return null;
    }
    
    
    public  ModuleObjectFindItem createFindItem(ParseContext context, int pos) {
        TopASTNode child = this.getFirstASTChild();
        ModuleObjectFindItem first = null;
        ModuleObjectFindItem last = null;
        while (child != null) {
            int spos = child.getPosition().getStartPos();
            if (spos > pos && pos != -1) break;
            ModuleObjectFindItem item = (ModuleObjectFindItem) child.generateSearchTypeNew(context,ParseContext.SEARCHFINDITEM,
                    context.getType());

            if (item == null) item = new ModuleObjectBaseItem(child.getRealText());
            
            if (first == null) {
                first = item;
                last = item;
            } else {
                last.setNext(item);
                last = item;
            }
            child = child.getNextASTSibling();
        }
        return first;
    }
    
     
    
     public ReferenceItem findItemResolveContext(ParseContext context, int pos) {

        // This is tricky enough so I don't feel like being clever
         if (pos == -1) {
             return super.findItemResolveContext(context,pos);
         }
        int numberChildren = this.getNumberOfChildren();
        TopASTNode[] child = new TopASTNode[numberChildren];
        child[0] = this.getFirstASTChild();
        int index = this.getNumberOfChildren();
        for (int i=1;i<numberChildren;i++) {
            child[i] = child[i-1].getNextASTSibling();
            int spos = child[i].getPosition().getStartPos();
            if (spos >= pos) index = i;
        }
        if (child[index-1] instanceof NameExpressionASTNode) {
            return child[index-1].findItemResolveContext(context,pos);
        }
        if (index == numberChildren) {

            if (child[index-1] instanceof IdentASTNode || child[index-1] instanceof NameDotASTNode) {
                return super.findItemResolveContext(context,-1);
            }
            else {
            return null;
            }
        }
        if (child[index] instanceof NameExpressionASTNode) {
        	ReferenceItem uitem = super.findItemResolveContext(context, pos);
        	
        	if (uitem != null && uitem.getObject() != null && 
        		(uitem.getObject() instanceof SystemVar || uitem.getObject() instanceof TypeVar)) 
        		return uitem;
        	else
        		return super.findItemResolveContext(context,-1);
        }
        return super.findItemResolveContext(context,pos);
        
        
    }
    
    
     
    
    
}
