/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.port;

import java.util.List;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilitiesInterface;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.realvars.OperatingTypeVar.IOVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.port.PortIOASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.vars.VariableListNode;
import com.simplifide.base.vhdl.parse.grammar.VhdlTokenTypes;




/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

// ( VARIABLE )? identifier_list COLON
//        ( mode )? subtype_indication ( VARASGN expression )?

public class InterfaceVariableDeclarationNode extends PortIOASTNode {

	private static final long serialVersionUID = 1L;







	public InterfaceVariableDeclarationNode() {}
   
        private TopObjectBase generateModule1(ParseContext context) {
            TopASTNode child = this.getFirstASTChild();
            if (!(child instanceof VariableListNode)) {
                child = child.getNextASTSibling();
            }
            context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED); // Turn on the search for already defined objects
            NoSortList portObject = (NoSortList) child.generateModule(context);
            context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_ENABLED);
            
            child = child.getNextASTSibling(); // :
            child = child.getNextASTSibling(); // (mode || type)
            OperatingTypeVar ioVar = new OperatingTypeVar.IOVar(IOVar.INPUT);
            if (child instanceof ModeASTNode) {
                child = child.getNextASTSibling(); //type
                ioVar = (OperatingTypeVar) child.generateModule(context);
            }
            ReferenceItem<? extends TypeVar> utype = (ReferenceItem) child.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilitiesInterface.REF_TYPEVAR); 
            
            NoSortList<PortDefault> outList = new NoSortList<PortDefault>();
            List<ReferenceItem> portList = portObject.getGenericSelfList();
            for (ReferenceItem portItem : portList) {
                SystemVar tvar = new SystemVar(portItem.getname(),utype,ioVar);
                ReferenceItem<? extends SystemVar> varRef = tvar.createReferenceItemWithLocation(portItem.getLocation());
                PortDefault def = new PortDefault(varRef);
                outList.addObject(def.createReferenceItemWithLocation(portItem.getLocation()));
            } 
            return outList;
            
        }
        
        
    @Override
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
        int ioType =  OperatingTypeVar.IOVar.INPUT;
       
        // Temporary Hack (Need to Handle Dual Ports)
        TopASTNode node = this.getFirstASTChild(); // Constant-Signal Port Identifier
        node = node.getNextASTSibling(); // Identifier List
        ReferenceLocation loc = context.createReferenceLocation(node);
               
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED); // Turn on the search for already defined objects
        NoSortList portObject = (NoSortList) node.generateModule(context);
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_ENABLED);
                
        node = node.getNextASTSibling(); // :
        node = node.getNextASTSibling(); // iotype?
        if (this.checkIOToken(node)) {
            String utype = node.getText();
            if (utype.equalsIgnoreCase("in")) ioType = OperatingTypeVar.IOVar.INPUT;
            else if (utype.equalsIgnoreCase("out")) ioType = OperatingTypeVar.IOVar.OUTPUT;
            else if (utype.equalsIgnoreCase("buffer")) ioType = OperatingTypeVar.IOVar.BUFFER;
            else if (utype.equalsIgnoreCase("inout")) ioType = OperatingTypeVar.IOVar.INOUT;
            node = node.getNextASTSibling();
        }
        
       ReferenceItem<? extends TypeVar> utype = (ReferenceItem) node.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilitiesInterface.REF_TYPEVAR); 
       
       NoSortList<PortDefault> outList = new NoSortList<PortDefault>();
       List<ReferenceItem> portList = portObject.getGenericSelfList();
       for (ReferenceItem portItem : portList) {
           SystemVar tvar = new SystemVar(portItem.getname(),utype,new OperatingTypeVar.IOVar(ioType));
           ReferenceItem<? extends SystemVar> varRef = tvar.createReferenceItemWithLocation(portItem.getLocation());
           PortDefault def = new PortDefault(varRef);
           outList.addObject(def.createReferenceItemWithLocation(portItem.getLocation()));
       } 
       return outList;
               
    }
    
   
    
   
    
    
    
    private boolean checkIOToken(TopASTNode node) {
        
        int type = node.getType();
        if ((type == VhdlTokenTypes.IN) || (type == VhdlTokenTypes.OUT) || (type == VhdlTokenTypes.BUFFER) || (type == VhdlTokenTypes.INOUT))
            return true;
        else
            return false;
    }
    
}
