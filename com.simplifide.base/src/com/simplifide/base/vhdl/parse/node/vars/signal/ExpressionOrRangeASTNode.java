package com.simplifide.base.vhdl.parse.node.vars.signal;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

public class ExpressionOrRangeASTNode extends TopASTNode{

	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		// Regular Expression Case
		if (this.getNumberOfChildren() == 1) return this.getFirstASTChild().generateModule(context);
		// Var ASTNOde
		return this.createRage(context);
			
	}
	
	 private int decodeDirection(String dir) {
	        if (dir.equalsIgnoreCase("downto")) return VarRange.DOWN;
	        else return VarRange.UP;
	    }
	
	public TopObjectBase createRage(ParseContext context)
    {
        TopASTNode child = this.getFirstASTChild(); // Top

        ReferenceItem top = (ReferenceItem) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT); 
        if (child.getNextASTSibling() == null) return top;
        
        ReferenceItem<ModuleObjectWithList> topDep = top.getObject().getDependants();
        this.generateUsedList(topDep.getObject());
        
        child = child.getNextASTSibling(); // Direction (Ignore)
        String dirText = child.getRealText();
        int dir = this.decodeDirection(dirText);
        child = child.getNextASTSibling(); // Bottom
        ReferenceItem bot = (ReferenceItem) (ReferenceItem) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT); 
        ReferenceItem<ModuleObjectWithList> botDep = bot.getObject().getDependants();
        this.generateUsedList(botDep.getObject());
        
        VarRange range = new VarRange(top,bot,dir);
        return range.createReferenceItem();
    }
}
