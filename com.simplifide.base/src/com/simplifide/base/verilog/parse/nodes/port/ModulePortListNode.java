/*
 * AnsiPortList.java
 *
 * Created on April 24, 2007, 1:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.port;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;
import com.simplifide.base.verilog.core.var.VerilogVar;



/**
 *
 * @author Andy
 * @deprecated
 */
public abstract class ModulePortListNode extends TopASTNodeGeneric<ReferenceItem<PortList>>{
    
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of AnsiPortList */
    public ModulePortListNode() {}
    
    public abstract NoSortList generateNoSortListSmall(ParseContext context);
    
    /** Method used for tasks and functions */
    public NoSortList generateNoSortList(ParseContext context) {
    	try {
    		return this.generateNoSortListSmall(context);
    	} catch (Exception e) {
            this.handleError(e, context);
            return null;
        }
    }
    
    // port_list : (LPAREN ansi_port_list RPAREN)?
    public static class Top extends ModulePortListNode {
    	
    	
    	 public ReferenceItem<PortList> createObjectSmallNew(ParseContext context) {
    		 if (this.getNumberOfChildren() == 0) return null;
    		 TopASTNode child = this.getFirstASTChild(); // (
    		 ModulePortListNode portNode = (ModulePortListNode) child.getNextASTSibling(); // Port List
    		 return portNode.createObject(context);
    	 }
    	    
    	 	
			public NoSortList generateNoSortListSmall(ParseContext context) {
				if (this.getNumberOfChildren() == 0) return null; // No Port List
				TopASTNode child = this.getFirstASTChild(); // (
				ModulePortListNode portNode =  (ModulePortListNode) child.getNextASTSibling(); // Port List Node (ANSI)
				
				return portNode.generateNoSortList(context); // )
			}
    	 
    	    public ReferenceLocation getLastPortLocation(ParseContext context) {
    	    	TopASTNode child = this.getFirstASTChild();
    	    	if (child == null) return context.createReferenceLocation(this);
    	    	child = child.getNextASTSibling();
    	    	if (child == null) return context.createReferenceLocation(this);
    	    	child = child.getNextASTSibling();
    	    	if (child == null) return context.createReferenceLocation(this);
    	    	return context.createReferenceLocation(child);
    	    }

			
    }
  
   
    // ansi_port_list : (ansi_port_declaration_or_port_expression)? (COMMA ansi_port_declaration_or_port_expression)*
    public static class Ansi extends ModulePortListNode {
    	
    	public ReferenceItem<PortList> createObjectSmallNew(ParseContext context) {
            PortList ports = new PortList("Ports");
            TopASTNode child = this.getFirstASTChild();
            
            while (child != null) {
                NoSortList outList = (NoSortList) child.generateModule(context);
                for (int j=0;j<outList.getnumber();j++) {
                    ReferenceItem item = (ReferenceItem) outList.getObject(j);
                    ports.addObject(item);
                }
                child = child.getNextASTSibling();
                if (child != null) child = child.getNextASTSibling();
               
            }
            return ports.createReferenceItem();
        }

		@Override
		public NoSortList generateNoSortListSmall(ParseContext context) {
			NoSortList<PortDefault> portList = new NoSortList<PortDefault>();
			TopASTNode child = this.getFirstASTChild(); 
			while (child != null) {
                NoSortList outList = (NoSortList) child.generateModule(context);
                for (int j=0;j<outList.getnumber();j++) {
                    ReferenceItem item = (ReferenceItem) outList.getObject(j);
                    portList.addReferenceItem(item);
                }
                child = child.getNextASTSibling();
                if (child != null) child = child.getNextASTSibling();
            }
			return portList;
		}	
    }
    
    public static class NoAnsi extends ModulePortListNode {
    	 public TopObjectBase generateModuleSmallNew(ParseContext context) {
    	        PortList portList = new PortList("PortList");
    	        TopASTNode child = this.getFirstASTChild();
    	        
    	        
    	        int i = 0;
    	        while (child != null) {
    	        	ReferenceLocation loc = context.createReferenceLocation(child);
    	            String text = child.getRealText();
    	            SystemVar tvar = new VerilogVar(text,null,null);
    	            VerilogPortDefault def = new VerilogPortDefault(tvar.createReferenceItem());
    	            def.setPortNumber(i);
    	            portList.addObject(def.createReferenceItemWithLocation(loc));
    	            
    	            child = child.getNextASTSibling();
    	            if (child != null) child = child.getNextASTSibling();
    	            i = i + 1;
    	        }
    	        return portList.createReferenceItem();
    	    }

		@Override
		public NoSortList generateNoSortListSmall(ParseContext context) {
			NoSortList<PortDefault> portList = new NoSortList<PortDefault>();
			TopASTNode child = this.getFirstASTChild();
			int i = 0;
			while (child != null) {
				ReferenceLocation loc = context.createReferenceLocation(child);
	            String text = child.getRealText();
	            SystemVar tvar = new VerilogVar(text,null,null);
	            VerilogPortDefault def = new VerilogPortDefault(tvar.createReferenceItem());
	            def.setPortNumber(i);
	            portList.addObject(def.createReferenceItemWithLocation(loc));
                child = child.getNextASTSibling();
                if (child != null) child = child.getNextASTSibling();
                i++;
            }
			return portList;
		}
    }

    
}
