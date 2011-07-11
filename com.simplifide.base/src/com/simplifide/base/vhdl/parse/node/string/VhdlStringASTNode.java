/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.string;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.general.StringObject;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: May 18, 2004
 * Time: 5:27:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlStringASTNode extends TopASTNodeNew
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
     * 
     */
    public VhdlStringASTNode() {}
    
    
   public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        String text = this.getRealText();
        String value = text.substring(1,text.length()-1);
        String uvalue = "\"" + value + "\"";       
        
        StringObject str = new StringObject(uvalue);
        return str.createReferenceItem();
    }


}
