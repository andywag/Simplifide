/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.var.realvars.SystemParameter;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 18, 2004
 * Time: 5:27:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class CharacterLitASTNode extends TopASTNode
{
   
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CharacterLitASTNode() {}
    public CharacterLitASTNode(Token tok)
    {
        super(tok);
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        String value = this.getRealText();
        SystemParameter par = new SystemParameter(this.getRealText(),SystemParameter.RADIX_BINARY);
        
        
        return par.createReferenceItem();
    }
    
  
    
   
    
  

    

    


}
