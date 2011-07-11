/*
 * TypeDefDataNode.java
 *
 * Created on April 23, 2007, 1:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.types;

import com.simplifide.base.BaseLog;
import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.class1.ClassInstanceModule;
import com.simplifide.base.core.class1.ClassModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.types.ClassTypeVar;
import com.simplifide.base.core.var.types.EnumTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


/**
 *
 * @author Andy
 */
public class TypeDefDataNode extends TopASTNode{
    
    /** Creates a new instance of TypeDefDataNode */
    public TypeDefDataNode() {}
 
    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {

    	TopASTNode typeNode = this.getFirstASTChild();
    	if (typeNode.getRealText().equalsIgnoreCase("class")) return null;
    	TopASTNode nameNode = typeNode.getNextASTSibling();
    	
        nameNode.generateModule(context);
        ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
        ModuleObject uobj = typeRef.getObject();
        if (uobj != null && 
        	(uobj instanceof ClassEntity ||
        	 uobj instanceof ClassTypeVar ||
        	 uobj instanceof ClassInstanceModule ||
        	 uobj instanceof ClassModule)) {
        	return null;
        }
        typeRef.changeName(nameNode.getRealText());
        typeRef.setLocation(context.createReferenceLocation(nameNode));
        
        if (context.getActiveReference().getname().equals("Global")) {
        	context.getRefHandler().getSuperModuleReference().addReferenceItem(typeRef);
        	if (typeRef.getObject() instanceof EnumTypeVar) {
        		
        		for (Object ref : typeRef.getObject().getGenericSelfList()) {
        			context.getActiveReference().addReferenceItem((ReferenceItem)ref);
        		}
        	}
        }
       
        
        return typeRef;
        
       
        
    }

    
}
