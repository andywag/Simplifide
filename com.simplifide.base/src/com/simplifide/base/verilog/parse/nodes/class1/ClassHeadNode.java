package com.simplifide.base.verilog.parse.nodes.class1;

import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

//class_head :  virtualQ "class" lifetime identifier parameter_port_list class_extends SEMI

public class ClassHeadNode extends TopASTNodeGeneric<ReferenceItem<ClassEntity>>{

	public String getClassName() {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		child = child.getNextASTSibling();
		return child.getRealText();
	}
	
	public ReferenceItem<ClassEntity> createObjectSmall(ParseContext context) {
		// Node Handling
		TopASTNode virtualNode = this.getFirstASTChild();
		TopASTNode classNode = virtualNode.getNextASTSibling();
		TopASTNode lifetimeNode = classNode.getNextASTSibling();
		TopASTNode nameNode     = lifetimeNode.getNextASTSibling();
		TopASTNode paramNode    = nameNode.getNextASTSibling();
		ClassExtendsNode extendsNode  = (ClassExtendsNode) paramNode.getNextASTSibling(); 

		String className = nameNode.getRealText();
		ReferenceItem<PortList> portR = (ReferenceItem<PortList> ) paramNode.generateModule(context);
		ReferenceItem<ClassInstanceModule> superR = (ReferenceItem<ClassInstanceModule>) extendsNode.generateModule(context);
		
		ReferenceItem ref = superR;
		if (ref != null && ref.getObject() != null && ref.getObject() instanceof ClassEntity) {
			ClassEntity ent = (ClassEntity) ref.getObject();
			superR = ent.getInstanceModRef();
		}
		
		ClassEntity nclass = new ClassEntity(className,portR);
		nclass.setSuperR(superR);
		
		
		
		return nclass.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
	}
	
	
}
