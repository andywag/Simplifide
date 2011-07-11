/*
 * PortTypeNode.java
 *
 * Created on April 24, 2007, 1:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.port;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.var.range.VarRange;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeNew;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.types.VerilogArrayType;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;


/**
 *
 * @author Andy
 */
  
public class PortTypeNode extends TopASTNodeNew{
    
    /** Creates a new instance of PortTypeNode */
    public PortTypeNode() {}

   
    protected int getSignedNode() {return 1;}
    protected int getRangeNode()  {return 2;}
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        ReferenceItem<TypeVar> utype = VerilogBaseTypes.BIT.createReferenceItem();
        ReferenceItem<VarRange> range = (ReferenceItem<VarRange>) this.getNode(this.getRangeNode()).generateModule(context);
        if (range == null) {
            return utype;
        }
        return new VerilogArrayType("auto",range,utype).createReferenceItem();
    }

    
    /**
     * 
     */
    public static class TriReg extends PortTypeNode{
        /**
         * 
         */
        public TriReg() {}
    }
    
    /**
     * 
     */
    public static class Net extends PortTypeNode{
        /**
         * 
         */
        public Net() {}
    }

    public static class Empty extends PortTypeNode{
        public Empty() {}
        protected int getSignedNode() {return 0;}
        protected int getRangeNode()  {return 1;}
    }
    
}
