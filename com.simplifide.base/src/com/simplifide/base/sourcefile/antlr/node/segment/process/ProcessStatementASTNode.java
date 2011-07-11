/*
 * ProcessStatementASTNode.java
 *
 * Created on December 6, 2005, 5:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.sourcefile.antlr.node.segment.process;

import java.util.ArrayList;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.error.warnings.LatchWarning;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.segment.basic.process.ProcessStatement;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;


/**
 *
 * @author Andy Wagner
 */

// Process Head

public class ProcessStatementASTNode extends TopASTNodeGeneric<ReferenceItem<ProcessStatement>>
{
    
    
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of ProcessStatementASTNode */
    public ProcessStatementASTNode() {}
    
    public TopASTNode formatBase() {return this;}
    
    protected ModuleObjectWithList<ModuleObject> filterDepList(ModuleObjectWithList<ModuleObject> list) {
    	ModuleObjectWithList<ModuleObject> nlist = new ModuleObjectWithList<ModuleObject>();
    	for (ReferenceItem item : list.getGenericSelfList()) {
    		ModuleObject obj = item.getObject();
    		if (obj instanceof SystemVar) {
    			SystemVar tvar = (SystemVar) obj;
    			if (tvar.getSearchType() == ReferenceUtilities.REF_SIGNAL) {
    				nlist.addObject(item);
    			}
    		}
    		else nlist.addObject(item);
    	}
    	return nlist;
    }
    
    protected void checkLatchWarning(ProcessStatement process, ModuleObjectWithList<ModuleObject> depList, 
            ParseContext context, ReferenceLocation location) {
        ArrayList<ModuleObject> diffList = process.checkDependencyList(this.filterDepList(depList));
        if (diffList.size() > 0 && location != null) {
            LatchWarning warn = new LatchWarning(location,diffList, depList);
            context.getErrorList().add(warn);
        }
    }
    
    protected void handleVariables(ParseContext context, ProcessStatement process, ReferenceLocation loc) {
    	ReferenceItem<ModuleObjectWithList> depList = process.getDependants();
        ReferenceItem<ModuleObjectWithList> outList = process.getOutputs();
        TopASTToken tok = getFirstLeafNode().getToken();
        HdlDoc doc = tok.getDoc();
        if (context.getErrorEnableHolder().warningLatch) {
        	if (doc == null) this.checkLatchWarning(process, depList.getObject(), context, loc);
        	else if (!doc.isLatch()) this.checkLatchWarning(process, depList.getObject(), context, loc);
        			
        
        }
        this.generateAssignedList(outList.getObject());
        this.generateUsedList(depList.getObject());
    }
    
    
}
