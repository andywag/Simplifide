/*
 * VhdlPortNode.java
 *
 * Created on April 12, 2007, 9:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.vhdl.parse.node.port;

import java.util.List;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.NotFoundTypeVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.port.PortIOASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;

/**
 *
 * @author Andy
 */

// Signal
// signal_port_dec(0) identifier_list(1) COLON(2) signal_port_modeQ(3) subtype_indication(4) signal_port_busQ(5) port_assignment(6)
// Constant
//  constant_port_dec(0) identifier_list(1) COLON(2)  constant_port_inq(3) subtype_indication(4) port_assignment(5)

public abstract class VhdlPortNode extends PortIOASTNode{
    
    /** Creates a new instance of VhdlPortNode */
    public VhdlPortNode() {}
    
   
    public void resolveContext(ParseContext context) {
    	int pos = this.getChildPosition(context.getDocPosition());
   
    	if (pos < 5) context.setSearchContext(new SearchContext.Type());
    	else if (pos >= 5) {
            context.setSearchContext(new SearchContext.Signal());
            TopASTNode child = this.getFirstASTChild();
            child = child.getNextASTSibling();
            child = child.getNextASTSibling(); // :
            child = child.getNextASTSibling(); // Type
            ReferenceItem<TypeVar> typeR = (ReferenceItem<TypeVar>) child.generateModule(context);
            
            if (typeR != null && typeR.getObject() != null) {
            	SearchContext cont = new SearchContext.SignalOfType(typeR.getObject());
            	context.setSearchContext(cont);
            }
    	}
    	
    }
    
    protected abstract OperatingTypeVar returnIOTypeVar(ParseContext context);
   
    protected abstract TopASTNode returnAssignNode();
    
   
    protected NoSortList getPortList(ParseContext context) {
        TopASTNode node = this.getNode(1);
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_DISABLED);
        NoSortList list = (NoSortList) node.generateModule(context);
        context.setDefinedMode(ParseContext.ERROR_NOT_DEFINED_ENABLED);
        return list;
    }
    
   
    protected ReferenceItem<? extends TypeVar> getPortType(ParseContext context) {
        TopASTNode node = this.getNode(4);
        ReferenceItem<? extends TypeVar> typeVar;
        typeVar = (ReferenceItem<? extends TypeVar>) node.generateSearchTypeNew(context,
                ParseContext.SEARCHREFERENCECONTEXT,
                ReferenceUtilities.REF_TYPEVAR);
        
        if (typeVar == null) {
        	NotFoundTypeVar ty = new NotFoundTypeVar(node.getRealText());
        	typeVar = ty.createReferenceItem();
        }
        
        return typeVar;
    }
    
  
    protected ReferenceItem getDefaultValue(ParseContext context) {
        TopASTNode assNode = this.returnAssignNode();
        ReferenceItem uitem = (ReferenceItem) assNode.generateModule(context);
        return uitem;
    }
    protected String getDefaultString(ParseContext context) {
        PortAssignmentNode assNode = (PortAssignmentNode) this.returnAssignNode();
        return assNode.getDefaultString();
    }
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        NoSortList plist = this.getPortList(context);
        ReferenceItem<? extends TypeVar> type = this.getPortType(context);
        
        OperatingTypeVar opVar = this.returnIOTypeVar(context);
        ReferenceItem defaultValue = this.getDefaultValue(context);
        String defaultString = this.getDefaultString(context);
        
        NoSortList<PortDefault> outList = new NoSortList<PortDefault>();
        List<ReferenceItem> portList = plist.getGenericSelfList();
        for (ReferenceItem portItem : portList) {
           SystemVar tvar = new SystemVar(portItem.getname(),type,opVar);
           ReferenceItem<? extends SystemVar> varRef = tvar.createReferenceItemWithLocation(portItem.getLocation());
           PortDefault def = new PortDefault(varRef);
           tvar.setDefaultString(defaultString);
           def.setDefaultValue(defaultValue);
           tvar.setDefaultValue(defaultValue);
           outList.addObject(def.createReferenceItemWithLocation(portItem.getLocation()));
       } 
       return outList;
    }
    
    
    public static class Signal extends VhdlPortNode {
       
        public Signal() {}

        protected OperatingTypeVar returnIOTypeVar(ParseContext context) {
            TopASTNode node = this.getNode(3); 
            String text = node.getRealText();
            
            if (text.equalsIgnoreCase("in")) return new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.INPUT);
            else if (text.equalsIgnoreCase("out")) return new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.OUTPUT);
            else if (text.equalsIgnoreCase("inout")) return new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.INOUT);
            else if (text.equalsIgnoreCase("buffer")) return new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.BUFFER);
            else return new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.INPUT);
        }

        
        protected TopASTNode returnAssignNode() {
            return this.getNode(6);
        }
        
    }
    
   
    public static class Constant extends VhdlPortNode {
       
	private static final long serialVersionUID = 1L;
        
        public Constant() {}

       
        protected OperatingTypeVar returnIOTypeVar(ParseContext context) {
            return new OperatingTypeVar.IOVar(OperatingTypeVar.IOVar.INPUT);
        }

        protected TopASTNode returnAssignNode() {
            return this.getNode(5);
        }
    }
    
}
