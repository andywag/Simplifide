/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.vars.signal;

import java.util.List;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

public abstract class VarASTNode extends TopASTNode
{

    // VarType
    // VarList
    // Range Top
    // SEMI

    public VarASTNode() {}
    public VarASTNode(Token tok) {super(tok);}
   
 
    private static void handleUsed(ReferenceItem assignedRef) {
    	TopASTNodeNew node = new TopASTNodeNew(); // kludge but ...
    	node.generateUsedList((ModuleObjectWithList)assignedRef.getDependants().getObject());
    	
    }
    
    public static NoSortList<SystemVar> createNewVar(NoSortList refList,
    		ReferenceItem<? extends TypeVar> type, 
    		OperatingTypeVar op,
    		ReferenceItem assignRef,
    		TopASTNode assignNode)
    {
        NoSortList<SystemVar> holder = new NoSortList("Holder");
        List<ReferenceItem> list = refList.getGenericSelfList();
        for (ReferenceItem base : list)
        {
            SystemVar tvar = new SystemVar(base.getname(),type,op);
            tvar.setDefaultValue(assignRef);
            ReferenceItem varRef = tvar.createReferenceItemWithLocation(base.getLocation());
            holder.addObject(varRef);
            if (assignNode != null) {
            	if (assignNode.getFirstASTChild() != null) {
            		tvar.setAssigned(true);
            	}
            }
            if (assignRef != null) {
            	
            	handleUsed(assignRef);
            }
        }
        return holder;
    }
    
   
    
   
    public abstract OperatingTypeVar createOperation();



}
