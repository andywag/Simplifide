/*
 * VhdlArchitectureBodyASTNode.java
 *
 * Created on July 6, 2005, 8:36 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.simplifide.base.vhdl.parse.node.alias;

import antlr.Token;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.alias.Alias;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;





/**
 *
 * @author awagner
 */

// ALIAS alias_designator alias_type IS name ( signature )? SEMI
public class AliasDeclarationNode extends TopASTNode
{
    
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public AliasDeclarationNode() {super();}
   public AliasDeclarationNode(Token tok)
   {
      super(tok);
   }
   
   public TopObjectBase generateModuleSmallNew(ParseContext context)
   {
        TopASTNode node = this.getFirstASTChild();
        node = node.getNextASTSibling(); // Alias Name
        ReferenceLocation loc = context.createReferenceLocation(node);
        TopASTNode nameNode = node;
        node = node.getNextASTSibling(); // Alias Type
        node = node.getNextASTSibling(); // Is
        node = node.getNextASTSibling(); // Name
        ReferenceItem item = (ReferenceItem) node.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT);
        Alias<ModuleObjectNew> alias = new Alias<ModuleObjectNew>(nameNode.getRealText(),item);
        ReferenceItem aliasRef = alias.createReferenceItemWithLocation(loc);
        
        if (item != null) aliasRef.setType(item.getType());
        return aliasRef;
        
   }
   
   
    
}
