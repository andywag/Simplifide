/*
 * VariableDeclarationNode.java
 *
 * Created on April 23, 2007, 9:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.ModuleObjectRangeListInitial;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.class1.ClassEntity;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.interfac.InterfaceTopModule;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.ClassTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;
import com.simplifide.base.verilog.core.types.VerilogArrayType;
import com.simplifide.base.verilog.core.var.VerilogVar;
import com.simplifide.base.verilog.parse.nodes.types.TypeDecNode;



/**
 *   variable_declaration ::= data_type list_of_variable_decl_assignments ;
 *   variable_declaration : constQ lifetime data_type list_of_variable_identifiers SEMI
 * @author Andy
 */


public class VariableDeclarationNode extends TopASTNodeNew{
    
  
	private static final long serialVersionUID = 1L;


	/** Creates a new instance of VariableDeclarationNode */
    public VariableDeclarationNode() {}

    
    private void handlePostDoc(TopASTNode child, NoSortList varList) {
		if (child != null) {
    		HdlDoc doc = child.getToken().getDoc();
    		if (doc != null) {
    			if (varList.getObject(0) != null) {
    				ReferenceItem uvar = (ReferenceItem) varList.getObject(0);
                	ModuleObject tvar = uvar.getObject();
                	tvar.setDoc(doc);
    			}
    		}
    	}
	}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	
    	
    	// Node Handling
    	TopASTNode child = this.getFirstASTChild(); // constQ
    	child = child.getNextASTSibling(); // lifetime
    	TopASTNode typeNode = child.getNextASTSibling(); // data_type
    	VariableListNode listNode = (VariableListNode) typeNode.getNextASTSibling(); // list_of_identifiers
    	TopASTNode semiNode = listNode.getNextASTSibling();
    	
    	// Type Calcuation
        ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
 
        OperatingTypeVar opVar = OperatingTypeVar.TYPE_SIGNAL;
        if (typeNode instanceof TypeDecNode.IO) { // Handle Ports (This seems like a kludge)
        	TypeDecNode.IO tnode = (TypeDecNode.IO) typeNode;
        	opVar = OperatingTypeVar.createIOVar(tnode.getIOTypeVar());
        }
        
        // Check to see if this is an interface as well
        // Interfaces keep variables in context
    	if (context.getPass() == BuildInterface.BUILD_FILE_CLOSED &&
    		!(opVar instanceof OperatingTypeVar.IOVar) &&
    		!(context.getActiveReference().getObject() instanceof InterfaceTopModule)) return null;

               
        NoSortList outList = new NoSortList("holder");
        NoSortList<ModuleObjectRangeListInitial> list = (NoSortList) listNode.createObject(context); // Create a list of variable values
        
        for (ReferenceItem<? extends ModuleObjectRangeListInitial> item : list.getGenericSelfList()) {
            //ReferenceItem<ModuleObjectRangeInitial> item = (ReferenceItem) list.getObject(i);
            ModuleObjectRangeListInitial init = item.getObject();
            
            for (ReferenceItem<VarRange> rangeR : init.getRanges()) { // Handle the MultiDimensional Array
            	VerilogArrayType type = new VerilogArrayType(rangeR,typeRef);
            	typeRef = type.createReferenceItem();
            }
            ReferenceItem utype = typeRef;
            if (utype != null && utype.getObject() != null && utype.getObject() instanceof ClassEntity) {
            	ClassEntity ent = (ClassEntity) utype.getObject();
            	ClassTypeVar cl = new ClassTypeVar(ent.getInstanceModRef());
            	typeRef = cl.createReferenceItemWithLocation(ent.createReferenceItem().getLocation());
            }
            // Create Variable
            SystemVar tvar = new VerilogVar(init.getname(),typeRef,opVar);
            tvar.setDefaultValue(init.getExpressionR());    
            ReferenceItem tvarR = tvar.createReferenceItemWithLocation(item.getLocation());
            // Handle Existing Ports
            ReferenceItem<VerilogPortDefault> portRef = context.getRefHandler().findLocalObject(new ModuleObjectBaseItem(item.getname()), ReferenceUtilities.REF_PORT_TOP);
            if (portRef == null || portRef.getObject() == null) {
            	outList.addObject(tvarR);
            }
            else {
            	ModuleObject obj = portRef.getObject();
            	if (obj instanceof PortDefault) {
            		// The 2 cases are to handle out,reg types
                	if (portRef.getObject().getLocalVarReference() == null)
                		portRef.getObject().setLocalVarReference(tvarR);
                	else if (portRef.getObject().getDeclarationRef() == null)
                		portRef.getObject().setDeclarationRef(tvarR);
                	else
                		portRef.getObject().setSecondRef(tvarR);
                	// Import to keep all of the variables with the proper operating type and type
                	portRef.getObject().getLocalVar().setTypeReference(typeRef);
                	if (opVar instanceof OperatingTypeVar.IOVar) {
                		portRef.getObject().getLocalVar().setOpTypeVar(opVar);
                	}
            	}
            }
        }
        ReferenceLocation last = context.createReferenceLocation(this);
        last.setDocPosition(last.getDocPosition() + last.getLength());
        last.setLength(0);
        
        if (context.getRefHandler().getModuleReference() != null) {
            context.getRefHandler().getModuleReference().getObject().setLastSignalLocation(last);

        }
        if (outList.getGenericSelfList().size()== 1) {
        	ReferenceItem<SystemVar> sysRef= (ReferenceItem<SystemVar>) outList.getGenericSelfList().get(0);
        	SystemVar var = sysRef.getObject();
        	ReferenceLocation loc= context.createReferenceLocation(this);
        	var.setDeclarationLocation(loc);
        }
        this.handlePostDoc(semiNode, outList);
        return outList;
    }

}
