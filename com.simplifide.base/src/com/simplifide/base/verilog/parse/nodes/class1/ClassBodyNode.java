package com.simplifide.base.verilog.parse.nodes.class1;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.module.TopBodyNode;

// class_body : (class_item)* "endclass" ( COLON identifier)?

public class ClassBodyNode extends TopASTNodeGeneric<ReferenceItem<HardwareModule>>{

	public ReferenceItem<HardwareModule> createObjectSmall(ParseContext context) {
		
		ClassBodyListNode itemsNode = (ClassBodyListNode) this.getFirstASTChild();
		return (ReferenceItem<HardwareModule>) itemsNode.generateModule(context);
	}
	
	public void format(FormatPosition position) {
		 
		this.getFirstASTChild().addFormatIndent(position);
		
		 
	 }
	
	public static class ClassBodyListNode extends TopBodyNode{
		
		public ReferenceItem<HardwareModule> generateModuleSmallNew(ParseContext context) {
			
			ReferenceItem<HardwareModule> moduleR = context.getRefHandler().getModuleReference();
			ClassModule cmodule = (ClassModule) moduleR.getObject();
		 	//ModuleObjectCompositeHolder hold = ModuleObjectCompositeHolder.dualHolder("",context.getActiveReference(),moduleR);
		 	context.getRefHandler().setLocalReference(context.getActiveReference());
			context.getRefHandler().setModuleReference(moduleR);
			context.getRefHandler().setActiveReference(moduleR);
			
			// Scroll through the body and fild all the items
			TopASTNode child =  this.getFirstASTChild();
			while (child != null) {
				ModuleObject ritem = (ModuleObject) child.generateModule(context);
				this.addItem(cmodule, ritem, child);
				child = child.getNextASTSibling();
			}
			return moduleR;
		}
		
	}
	
	
}
