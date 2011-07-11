/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogArrayType;


/**net_declaration14 ::= net_type_or_trireg [ drive_strength | charge_strength ] [ vectored | scalared ]
  [ signing ] { packed_dimension } [ delay3 ] list_of_net_decl_assignments ;
  
net_declaration  : net_type_or_trireg signing packed_dimension list_of_net_decl_assignments SEMI
        {#net_declaration = #([NETDECLARATION, "net_declaration"], #net_declaration);}; */

public class NetDeclarationNode extends TopASTNode{

  
	private static final long serialVersionUID = 1L;

	public NetDeclarationNode() {}
    
	private void handlePostDoc(TopASTNode child, NoSortList<ModuleObject> varList) {
		if (child != null) {
    		HdlDoc doc = child.getToken().getDoc();
    		if (doc != null) {
            	ModuleObject tvar = varList.getObject(0).getObject();
            	tvar.setDoc(doc);
    		}
    		
    	}
	}
	
    public TopObjectBase generateModuleSmallNew(ParseContext context)  {
    	TopASTNode typeNode = this.getFirstASTChild();
    	TopASTNode signNode = typeNode.getNextASTSibling();
    	TopASTNode dimNode  = signNode.getNextASTSibling();
    	TopASTNode delNode  = dimNode.getNextASTSibling();
    	TopASTNode listNode = delNode.getNextASTSibling();
    	
        String typeText = typeNode.getRealText();
        TypeVar type = new TypeVar(typeText);
        ReferenceItem<TypeVar> typeRef = type.createReferenceItem();
        
        ReferenceItem<VarRange> arrRef = (ReferenceItem<VarRange> ) dimNode.generateModule(context);
        if (arrRef != null) {
        	VerilogArrayType array = new VerilogArrayType("",arrRef,typeRef);
        	typeRef = array.createReferenceItem();
        }
        
        
        
        
        NoSortList outList = new NoSortList("holder");
        NoSortList list = (NoSortList) listNode.generateModule(context);
        for (int i=0;i<list.getnumber();i++) {
            ReferenceItem<SystemVar> tvarRef = (ReferenceItem) list.getObject(i);
            SystemVar tvar = tvarRef.getObject();
            tvar.setTypeReference(typeRef);
            tvar.setOpTypeVar(new OperatingTypeVar.VariableVar());
            
            ReferenceItem<PortTop> portRef = context.getRefHandler().findLocalObject(new ModuleObjectBaseItem(tvar.getname()), ReferenceUtilities.REF_PORT_TOP);
            if (portRef == null) {
            	outList.addObject(tvarRef);
            }
        }
        ReferenceLocation last = context.createReferenceLocation(this);
        last.setDocPosition(last.getDocPosition() + last.getLength());
        last.setLength(0);
        context.getRefHandler().getModuleReference().getObject().setLastSignalLocation(last);
        if (outList.getGenericSelfList().size()== 1) {
        	ReferenceItem<SystemVar> sysRef= (ReferenceItem<SystemVar>) outList.getGenericSelfList().get(0);
        	SystemVar var = sysRef.getObject();
        	ReferenceLocation loc= context.createReferenceLocation(this);
        	var.setDeclarationLocation(loc);
        }
        this.handlePostDoc(listNode.getNextASTSibling(), list);
        
        return outList;
    }
}
