/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.segment.process;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.vars.signal.VarListASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlSensitivityListASTNode extends VarListASTNode
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlSensitivityListASTNode() {}
    public VhdlSensitivityListASTNode(antlr.Token tok)
    {
        super(tok);
    }
   
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        NoSortList<ModuleObject> ulist = new  NoSortList<ModuleObject>("Holder");
        TopASTNode node = this.getFirstASTChild();
        while (node != null)
        {
            ModuleObject ob = (ModuleObject) node.generateModule(context); 
            if (ob != null)
                ulist.addObject(ob.createReferenceItem());
            node = node.getNextASTSibling();
            if (node != null) node = node.getNextASTSibling();
        }
        
        return ulist;
    }
    
    
}
