/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.vars.types;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.EnumVar;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.types.EnumHolder;
import com.simplifide.base.core.var.types.EnumTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;




/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 16, 2004
 * Time: 11:28:12 AM
 * To change this template use File | Settings | File Templates.
 */

public class EnumerationTypeASTNode extends TopASTNode {
    
    // LCurly
    // Sys_var_dec *
    // RCurly
    
    public EnumerationTypeASTNode() {}
    public EnumerationTypeASTNode(Token tok) {super(tok);}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        
        TopASTNode child = this.getFirstASTChild(); // (
        child = child.getNextASTSibling(); // Identifier
        
        ReferenceLocation enumLocation = context.createReferenceLocation(child);
        
        EnumTypeVar enumVar = new EnumTypeVar("TempEnumTypeASTNode");
        ReferenceItem<TypeVar> enumRef = enumVar.createReferenceItemWithLocation(enumLocation);
        
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
        while (child != null) {
            enumLocation = context.createReferenceLocation(child);
            EnumVar tvar = new EnumVar(child.getRealText(),enumRef,new OperatingTypeVar.ConstantVar());
            ReferenceItem tvarRef = tvar.createReferenceItemWithLocation(enumLocation);
            enumRef.addReferenceItem(tvarRef);
            //tvarRef.setHidden(true);
            // Kludge to put the enum values in context
            /** Check for the existance of a function holder (If it doesn't exist create it, and add it to the active reference */
            ModuleObjectBaseItem baseFunc = new ModuleObjectBaseItem(tvar.getname());
            ReferenceItem<EnumHolder> holdRef = context.getRefHandler().findLocalObject(baseFunc,ReferenceUtilities.REF_ENUM_HOLDER);
            if (holdRef == null) {
                EnumHolder holder = new EnumHolder(baseFunc.getname());
                holdRef = holder.createReferenceItemWithLocation(tvarRef.getLocation());
                context.getActiveReference().addReferenceItem(holdRef);
                holdRef.setHidden(true);
            }
            holdRef.addReferenceItem(tvarRef);
            //context.getActiveReference().addReferenceItem(tvarRef);
            
            child = child.getNextASTSibling();
            if (child != null) child = child.getNextASTSibling();
        }
        return enumRef;
    }
    
    
}
