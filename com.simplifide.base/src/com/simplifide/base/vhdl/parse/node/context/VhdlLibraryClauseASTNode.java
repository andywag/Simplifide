/*
 * VhdlArchitectureBodyASTNode.java
 *
 * Created on July 6, 2005, 8:36 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.simplifide.base.vhdl.parse.node.context;

import antlr.Token;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;






/**
 *
 * @author awagner
 */
public class VhdlLibraryClauseASTNode extends TopASTNode
{

    // library
    // library list or identifier;
    // semicolon

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of VhdlArchitectureBodyASTNode */

   public VhdlLibraryClauseASTNode() {super();}
   public VhdlLibraryClauseASTNode(Token tok)
   {
      super(tok);
   }

   /** @todo : Do Nothing for now */
   public TopObjectBase generateModuleSmallNew(ParseContext context) {   
        return null;
    }
   
 
   
    
}
