/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.instance;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.port.PortTop;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.core.var.realvars.SystemVar;
import com.simplifide.base.core.var.types.TypeVar;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.port.PortASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.SearchContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

// association_arrow : formal_part ARROW (actual_part)?
public class VhdlInstancePortDotASTNode extends PortASTNode
{


	private static final long serialVersionUID = 1L;

	   // Only Used To Specify Context Types for Nodes
    public void resolveContext(ParseContext context) {
    	  TopASTNode instanceNode = this.getFirstASTChild();
          TopASTNode child = instanceNode.getNextASTSibling();
          TopASTNode localNode = child.getNextASTSibling();
      
          if (context.getDocPosition() >= localNode.getPosition().getStartPos()) { // Local Node
              context.setSearchContext(new SearchContext.Signal());
          	 ReferenceItem<PortTop> portReference = (ReferenceItem<PortTop>) instanceNode.generateSearchTypeNew(context,ParseContext.SEARCH_REFERENCE_ACTIVE,ReferenceUtilities.REF_PORT_TOP);
               if (portReference != null) {
               	 TypeVar type = portReference.getObject().getType();
                   context.setSearchContext(new SearchContext.SignalOfType(type));
               }
              context.setActiveReference(context.getRefHandler().getSecondaryReference());
          }
          else { // Instance Node
              context.getRefHandler().setLocalReference(context.getRefHandler().getActiveReference());
              context.getRefHandler().setSearchReference(context.getRefHandler().getActiveReference());
              
          }
   	
    }
	
    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
        // Determine which context to search for the given node
        TopASTNode instanceNode = this.getFirstASTChild();
        TopASTNode child = instanceNode.getNextASTSibling();
        TopASTNode localNode = child.getNextASTSibling();
    
        if (pos >= localNode.getPosition().getStartPos()) { // Local Node
            context.setSearchContext(new SearchContext.Signal());
        	 ReferenceItem<PortTop> portReference = (ReferenceItem<PortTop>) instanceNode.generateSearchTypeNew(context,ParseContext.SEARCH_REFERENCE_ACTIVE,ReferenceUtilities.REF_PORT_TOP);
             if (portReference != null) {
             	 TypeVar type = portReference.getObject().getType();
                 context.setSearchContext(new SearchContext.SignalOfType(type));
             }
            context.setActiveReference(context.getRefHandler().getSecondaryReference());
        }
        else { // Instance Node
            context.getRefHandler().setLocalReference(context.getRefHandler().getActiveReference());
            context.getRefHandler().setSearchReference(context.getRefHandler().getActiveReference());
            
        }
        return null;
    }

    
    /** @todo : Need to figure out how references are going to work, which includes fixing the kludge when the reference is a constant 
     * 
     */
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        
    	
        ReferenceItem<ModInstanceConnect> connectReference = (ReferenceItem<ModInstanceConnect>) context.getActiveReference();
        ModInstanceConnect connect = connectReference.getObject();
        
        TopASTNode ch = this.getFirstASTChild(); // Internal Variable (Name Node)
        String portName = ch.getRealText();


        ReferenceItem<PortTop> portReference = null;
        if (connect.getConnectionReference() != null) {
            context.setActiveReference(connect.getConnectionReference());
            portReference = (ReferenceItem<PortTop>) ch.generateSearchTypeNew(context,ParseContext.SEARCH_REFERENCE_ACTIVE,ReferenceUtilities.REF_PORT_TOP);
            context.setActiveReference(context.getRefHandler().getSecondaryReference());
        }

        
        
        ReferenceItem<SystemVar> sysRef = null;
        if (portReference != null) {
            sysRef = portReference.getObject().getLocalVarReference();
        }
        
        ch = ch.getNextASTSibling(); // Arrow
        ch = ch.getNextASTSibling(); // Upper Variable
        
        ReferenceLocation loc = context.createReferenceLocation(ch);
        ReferenceItem<ModuleObject> externRef = (ReferenceItem<ModuleObject>) ch.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT);
        // Null extern ref won't work for port refactoring
        if (externRef == null) externRef = new ModuleObject(ch.getRealText()).createReferenceItem();
        externRef.getObject().setCompositeName(ch.getRealText());
        
        PortConnect port;
        if (sysRef != null) {
            port = new PortConnect(sysRef,externRef);   
        }
        else {
            port = new PortConnect(portName,externRef);
        }
        context.setActiveReference(connectReference);
        port.setConnectionLocation(context.createReferenceLocation(this.getFirstASTChild()));
        return port.createReferenceItemWithLocation(loc);
    }

  


    
   
                                                





}
