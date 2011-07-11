/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import antlr.Token;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

public class RootASTNode extends TopASTNode {
    
    
    public RootASTNode() {super();}
    public RootASTNode(Token tok) {
        super(tok);
    }
    
   
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
       
        while (child != null) {
            child.generateModule(context);
            child = child.getNextASTSibling();
        }
        
        return null;
    }
    
    
    /** This is used only for name modules --- The root is a fake node */
    public ModuleObject generateNameModule(ParseContext context, int type) {
        TopASTNode child = this.getFirstASTChild();
        if (child != null) {
            ModuleObject obj = (ModuleObject) child.generateSearchTypeNew(context,ParseContext.SEARCHFINDITEM,ReferenceUtilities.REF_MODULEOBJECT); 
            return obj;
        }
        return null;
    }
    
    
    
    
}
