/*
 * IoDeclarationNode.java
 *
 * Created on April 24, 2007, 1:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.vars;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;
import com.simplifide.base.verilog.core.var.VerilogVar;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;



/**
 *
 * @author Andy
 */
// ("input" | "inout" | "output") port_type list_of_variable_identifiers
public class IoDeclarationNode extends TopASTNode{
    
    
	private static final long serialVersionUID = 1L;
	/** Creates a new instance of IoDeclarationNode */
    public IoDeclarationNode() {}

    private int decodeIoType(int type) {
        if (type == SystemVerilogTokenTypes.LITERAL_input) return OperatingTypeVar.IOVar.INPUT;
        if (type == SystemVerilogTokenTypes.LITERAL_output) return OperatingTypeVar.IOVar.OUTPUT;
        return OperatingTypeVar.IOVar.INOUT;
    }
    
    private PortList getPortList(ParseContext context) {
    	ReferenceItem item = context.getRefHandler().getActiveReference();
    	ModuleObject obj = item.getObject();
    	Entity entity;
    	if (obj instanceof HardwareModule) {
    		HardwareModule mod = (HardwareModule) obj;
    		InstanceModule inst = (InstanceModule) mod.getInstModRef().getObject();
    		entity = inst.getEntity();
    	}
    	else if (obj instanceof Entity) {
    		entity = (Entity) obj;
    	}
    	else {
    		return null;
    	}
    	ModInstanceDefault def = (ModInstanceDefault) entity.getConnectRef().getObject();
    	return (PortList) def.getPortListRef().getObject();
    }
    
    @Override
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	
    	TopASTNode ioNode = this.getFirstASTChild();
    	TopASTNode typeNode = ioNode.getNextASTSibling();
    	TopASTNode listNode = typeNode.getNextASTSibling();
    	
        NoSortList outList = new NoSortList("portlist");
        int ioType1 = ioNode.getToken().getType();
        int ioType = this.decodeIoType(ioType1);
        OperatingTypeVar opVar = new OperatingTypeVar.IOVar(ioType);
        ReferenceItem<TypeVar> typeRef = (ReferenceItem<TypeVar>) typeNode.generateModule(context);
        NoSortList varList = (NoSortList) listNode.generateModule(context);
        
        PortList portList = this.getPortList(context);
        
        for (int i=0;i<varList.getnumber();i++) {
            ReferenceItem item = (ReferenceItem) varList.getObject(i);
            SystemVar tvar = new VerilogVar(item.getname(),typeRef,opVar);
            ReferenceItem<PortTop> portRef = context.getRefHandler().findLocalObject(new ModuleObjectBaseItem(tvar.getname()), ReferenceUtilities.REF_PORT_TOP);
           
            if (portRef == null) {
            	VerilogPortDefault port = new VerilogPortDefault(tvar.createReferenceItemWithLocation(item.getLocation()));
            	port.setDeclarationRef(tvar.createReferenceItem());
            	portRef = port.createReferenceItemWithLocation(item.getLocation());
            	
            	// I commented this out to fix the ports but it will undoubtedly have an effect elsewhere
            	// I'll deal with this another day
            	
            	//portList.addObject(portRef);
            	
            	// Moved this to always to handle comment additions
            	//outList.addObject(portRef);
            	
            	
            }
            else { 	
            	VerilogPortDefault port = (VerilogPortDefault) portRef.getObject();
            	port.setDeclarationRef(tvar.createReferenceItem());
            	port.setLocalVarReference(tvar.createReferenceItemWithLocation(item.getLocation()));
            	portRef.setType(portRef.getObject().getSearchType());
            	
            }
            outList.addObject(portRef); 
            if (typeRef != null) {
        		this.generateUsedList(typeRef.getDependants().getObject());
        	}
            
        }
        return outList;
    }

    
}
