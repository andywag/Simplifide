package com.simplifide.base.verilog.parse.nodes.class1;

import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


//class_extends : ( "extends" class_type  )?
public class ClassExtendsNode extends TopASTNodeGeneric<ReferenceItem<ClassInstanceModule>>{

	public ReferenceItem<ClassInstanceModule> createObjectSmall(ParseContext context) {
		
		TopASTNode node = this.getFirstASTChild();
		if (node == null) return null;
		node = node.getNextASTSibling();
		ReferenceItem<ClassInstanceModule> clRef = (ReferenceItem<ClassInstanceModule>)node.generateSearchTypeNew(context, 
				ParseContext.SEARCHREFERENCECONTEXTANDGLOBAL,
				ReferenceUtilitiesInterface.REF_INSTANCE_MODULE_TOP);
		if (clRef == null) {
			clRef = (ReferenceItem<ClassInstanceModule>)node.generateSearchTypeNew(context, 
					ParseContext.SEARCHREFERENCEPROJECTANDLIBRARY,
					ReferenceUtilitiesInterface.REF_MODULEOBJECT);
		}

		
		return clRef;
	}
	
}
