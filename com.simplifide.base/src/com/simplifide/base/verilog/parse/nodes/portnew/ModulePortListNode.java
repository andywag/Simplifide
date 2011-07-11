/*
 * AnsiPortList.java
 *
 * Created on April 24, 2007, 1:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes.portnew;

import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.port.PortDefault;
import com.simplifide.base.core.port.PortList;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.var.realvars.OperatingTypeVar;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.port.VerilogPortDefault;
import com.simplifide.base.verilog.core.types.VerilogBaseTypes;



/**
 *
 * @author Andy
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
    	
    	 public void format(FormatPosition position) {
    			TopASTNode child = this.getFirstASTChild();  // (
    			NodePosition upos = child.getPosition();
    			int start = upos.getStartPos();
    			int stop = this.getPosition().getEndPos();
    			
    			int indent = FormatSupport.getInstance().getModuleIndent();
    			FormatPosition npos = position.addNewPosition(start, start+1);
    			npos.setMinimum(indent);
    			npos.setIndent(indent);
    			npos = position.addNewPosition(start+1, stop);
    			npos.setMinimum(indent+1);
    			npos.setIndent(indent+1);
    		}
    	
    	 public ReferenceItem<PortList> createObjectSmall(ParseContext context) {
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
    	
    	// Stores the last type information in the variable
    	private void convertLastPortType(ReferenceItem<VerilogPortDefault> current, ReferenceItem<VerilogPortDefault> last) {
    		SystemVar currentVar = current.getObject().getLocalVar();
    		
    		if (currentVar.getOpTypeVar() == null ) { // Handle Operating Type
    			if (last == null) currentVar.setOpTypeVar(OperatingTypeVar.TYPE_SIGNAL);
    			else {
    				SystemVar lastVar = last.getObject().getLocalVar();
    				if (lastVar.getOpTypeVar() == null) currentVar.setOpTypeVar(OperatingTypeVar.TYPE_SIGNAL);
    				else currentVar.setOpTypeVar(lastVar.getOpTypeVar());
    			}
    			if (currentVar.getTypeReference() == null) { // Handle Types
        			if (last == null) currentVar.setTypeReference(VerilogBaseTypes.BIT.createReferenceItem());
        			else {
        				SystemVar lastVar = last.getObject().getLocalVar();
        				if (lastVar.getTypeReference() == null) currentVar.setTypeReference(VerilogBaseTypes.BIT.createReferenceItem());
        				else currentVar.setTypeReference(lastVar.getTypeReference());
        			}
        		}
    		}
    		if (currentVar.getTypeReference() == null) { // Condition that falls through the cracks
    			currentVar.setTypeReference(VerilogBaseTypes.BIT.createReferenceItem());
    		}
    		
    	}
    	
    	public ReferenceItem<PortList> createObjectSmall(ParseContext context) {
            PortList ports = new PortList("Ports");
            AnsiPortTopNode child = (AnsiPortTopNode) this.getFirstASTChild();
            int index = 0;
            ReferenceItem<VerilogPortDefault> lastPort = null;
            while (child != null) {
                ReferenceItem<VerilogPortDefault> defR = child.createObject(context);
            	ports.addObject(defR);
            	defR.getObject().setPortNumber(index);
            	this.convertLastPortType(defR, lastPort);
            	lastPort = defR;
            	
                TopASTNode child2 = child.getNextASTSibling();
                if (child2 == null) break;
                child = (AnsiPortTopNode) child2.getNextASTSibling();
                index++;
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
    
    
    
}
