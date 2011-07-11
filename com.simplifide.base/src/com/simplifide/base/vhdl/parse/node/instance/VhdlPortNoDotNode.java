/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.instance;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.port.PortConnect;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.port.PortASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class VhdlPortNoDotNode extends PortASTNode
{


	private static final long serialVersionUID = 1L;
	public VhdlPortNoDotNode() {}
   
    
    
    /** @todo : Need to figure out how references are going to work, which includes fixing the kludge when the reference is a constant 
     * 
     */
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        TopASTNode ch = this.getFirstASTChild();
        ReferenceItem<ModuleObject> externRef = (ReferenceItem<ModuleObject>) ch.generateSearchTypeNew(context,ParseContext.SEARCHREFERENCECONTEXT,ReferenceUtilities.REF_MODULEOBJECT);
        
        if (externRef != null) {
            PortConnect port = new PortConnect(externRef.getname(),externRef);
            return port.createReferenceItem();
        }
        return null;
    }


    
   
                                                





}
