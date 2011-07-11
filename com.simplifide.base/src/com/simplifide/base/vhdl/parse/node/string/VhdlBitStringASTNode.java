/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.string;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.var.realvars.SystemParameter;
import com.simplifide.base.sourcefile.antlr.node.NumberASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 18, 2004
 * Time: 5:27:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlBitStringASTNode extends NumberASTNode
{
    public VhdlBitStringASTNode() {}
    public VhdlBitStringASTNode(Token tok)
    {
        super(tok);
    }
    
    private int decodeBase(String base) {
        
        int lvalue;
        if (base.equalsIgnoreCase("b")) lvalue = SystemParameter.RADIX_BINARY;
        else if (base.equalsIgnoreCase("d"))  lvalue = SystemParameter.RADIX_DECIMAL;
        else if (base.equalsIgnoreCase("o"))  lvalue = SystemParameter.RADIX_OCTAL;
        else   lvalue = SystemParameter.RADIX_HEX;
        return lvalue;
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        String text = this.getRealText();
        String base = text.substring(0,1);
        String value = text.substring(2,text.length()-1);
               
        SystemParameter par = new SystemParameter(value,this.decodeBase(base));
        return par.createReferenceItem();
    }
    
    

    
    


}
