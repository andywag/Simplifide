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
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.process.ProcessBody;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlProcessStatementPartASTNode extends TopASTNodeNew<ReferenceItem<ProcessBody>> {
    
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlProcessStatementPartASTNode() {}
    
    public ReferenceItem<ProcessBody> generateModuleSmallNew(ParseContext context) {
        ProcessBody list = new ProcessBody("ProcessStatementPart");
        ReferenceItem<ProcessBody> listRef = list.createReferenceItem();
        TopASTNode child =  this.getFirstASTChild(); // process head
        while (child != null) {
            ModuleObject obj = (ModuleObject) child.generateModule(context);
            if (obj != null) {
                ReferenceItem robj = this.convertModuleObject(obj);
                listRef.addReferenceItem(robj);
            }
            child = child.getNextASTSibling();
        }
        return listRef;
        
    }
    
}
