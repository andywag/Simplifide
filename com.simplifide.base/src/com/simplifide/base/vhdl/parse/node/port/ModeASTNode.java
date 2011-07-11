/*
 * ModeASTNode.java
 *
 * Created on April 12, 2007, 9:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.port;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;

/**
 *
 * @author Andy
 */
public class ModeASTNode extends TopASTNodeNew{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ModeASTNode */
    public ModeASTNode() {}
    
    @Override
    public TopObjectBase generateModuleNewer(ParseContext context) {
        TopASTToken tok = this.getToken();
        int intype;
        switch(tok.getType()) {
            case(VhdlTokenTypes.IN) : intype = OperatingTypeVar.IOVar.INPUT; break;
            case(VhdlTokenTypes.OUT) : intype = OperatingTypeVar.IOVar.OUTPUT; break;
            case(VhdlTokenTypes.BUFFER) : intype = OperatingTypeVar.IOVar.BUFFER; break;
            case(VhdlTokenTypes.INOUT) : intype = OperatingTypeVar.IOVar.INOUT; break;
            default :intype = OperatingTypeVar.IOVar.INPUT; break;
        }
        return new OperatingTypeVar.IOVar(intype);
    }
    
}
