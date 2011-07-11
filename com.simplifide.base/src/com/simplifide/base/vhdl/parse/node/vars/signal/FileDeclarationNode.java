/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.vars.signal;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

//FILE identifier_list COLON subtype_indication (file_open_information )? SEMI

public class FileDeclarationNode extends VhdlVarASTNode{

    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild(); //file
        child = child.getNextASTSibling(); // identifier_list
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
        NoSortList vlist = (NoSortList) child.generateModule(context);
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_ENABLED);
        child = child.getNextASTSibling(); // :
        child = child.getNextASTSibling(); // type
        ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) child.generateModule(context);
        NoSortList<SystemVar> outvar = VarASTNode.createNewVar(vlist,typeRef,this.createOperation(),null,null);
        
        
        return outvar;
    }

    @Override
    public OperatingTypeVar createOperation() {
        // TODO Auto-generated method stub
        return null;
    }
}
