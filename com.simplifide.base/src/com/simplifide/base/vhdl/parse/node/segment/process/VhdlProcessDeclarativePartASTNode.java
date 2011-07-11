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
import com.simplifide.base.basic.struct.ModuleObjectCompositeHolder;
import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/**
 *
 * @author Andy Wagner
 */

// Process Head

public class VhdlProcessDeclarativePartASTNode extends TopASTNodeNew<ReferenceItem<ModuleObjectWithList>> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of ProcessStatementASTNode */
    public VhdlProcessDeclarativePartASTNode() {}
  
    
   
    
    
    public ReferenceItem<ModuleObjectWithList> generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        
        ReferenceItem currentLocalReference = context.getRefHandler().returnLocalReference();
        
        // This is a slightly confusing operation. This context is created and used for it's own internal
        // searching and then removed, from the local context and then added back at the main process level
        // This is done to minimize potential mistakes of changing one place...
        ModuleObjectWithList procList = new ModuleObjectWithList("ProcessContent");
        ReferenceItem procListRef = procList.createReferenceItem();
        
        ModuleObjectCompositeHolder holder = ModuleObjectCompositeHolder.dualHolder("ProcessComposite",procListRef,currentLocalReference);
        context.getRefHandler().setLocalReference(holder.createReferenceItem());
        
        while (child != null) {
            ModuleObject obj = (ModuleObject) child.generateModule(context);
            // Hack 
            if (obj instanceof NoSortList) {
                NoSortList<ModuleObject> list = (NoSortList<ModuleObject>) obj;
                for (ReferenceItem item : list.getGenericSelfList()) {
                    procList.addReferenceItem(item);
                }
            }
            else if (obj instanceof ReferenceItem) {
                procList.addReferenceItem((ReferenceItem)obj);
            }
            child = child.getNextASTSibling();
        }
        context.getRefHandler().setLocalReference(currentLocalReference);
        
        return procListRef;
           

    }

     public boolean canFold() {return true;}
     public String getFoldName() {
        
        return "Process Declaration";
    }
    
    
    
}
