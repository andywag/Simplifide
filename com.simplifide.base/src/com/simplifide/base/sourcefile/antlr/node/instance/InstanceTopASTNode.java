/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.sourcefile.antlr.node.instance;

import com.simplifide.base.basic.struct.ModuleObjectWithList;
import com.simplifide.base.core.generate.GenerateStatement;
import com.simplifide.base.core.hierarchy.ConnectorHolder;
import com.simplifide.base.core.hierarchy.ConnectorModule;
import com.simplifide.base.core.hierarchy.GenerateConnectorHolder;
import com.simplifide.base.core.instance.EntityHolder;
import com.simplifide.base.core.instance.ModInstanceConnect;
import com.simplifide.base.core.instance.ModInstanceConnectNotFound;
import com.simplifide.base.core.module.HardwareModule;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Mar 18, 2004
 * Time: 9:24:40 AM
 * To change this template use File | Settings | File Templates.
 */

public class InstanceTopASTNode extends TopASTNode
{

    private static final long serialVersionUID = 1L;

    public InstanceTopASTNode() {}
    
    public boolean canFold() {
        return true;
    }

    protected void handleVariables(ModInstanceConnect connect) {
        if (connect != null) {
            this.generateAssignedList((ModuleObjectWithList)connect.getOutputs().getObject());
            this.generateUsedList((ModuleObjectWithList)connect.getDependants().getObject());
        }
    }
    
    
    protected void attachConnectorModule(ReferenceItem<EntityHolder> entityRef,
            ReferenceItem<HardwareModule> moduleRef,
            ReferenceItem<ModInstanceConnect> connectReference,
            ReferenceItem activeReference,
            ParseContext context) {
        
        if (entityRef != null) {
            if (entityRef.hasObject()) { 
           
            // This instance module
            InstanceModule thisMod = (InstanceModule) moduleRef.getObject().getInstModRef().getObject();
            // Instantiated Instance Module
            ReferenceItem<InstanceModule> childModRef = entityRef.getObject().getInstanceModRef();
            InstanceModule childMod = childModRef.getObject();
            
            // This blocks connector Module and childs connector module 
            ReferenceItem<ConnectorModule> thisConnectRef = thisMod.getConnectReference();
            ReferenceItem<ConnectorModule> childConnectRef =  childMod.getConnectReference();
            
            // create a connection holder between the child and this connection (ModInstanceConnect)
            ConnectorHolder holder = new ConnectorHolder(childConnectRef,connectReference);
            ReferenceItem holderR = holder.createReferenceItemWithLocation(context.createReferenceLocation(this));
            
            if (activeReference.getObject() instanceof GenerateStatement) {
                GenerateStatement state = (GenerateStatement) activeReference.getObject();
                ReferenceItem<GenerateConnectorHolder> hold = state.getConnectHolderRef();
                hold.addReferenceItem(holderR);
                thisConnectRef.addReferenceItem(hold);
            }
            else {
                thisConnectRef.addReferenceItem(holderR);
            }
            
            
            childConnectRef.getObject().getParentRef().addReferenceItem(thisConnectRef);
            // Add the connection information for refactoring (This is incredibly screwed up)
            ConnectorHolder nhold = new ConnectorHolder(thisConnectRef,connectReference);
            childConnectRef.getObject().getConnectionList().addReferenceItem(nhold.createReferenceItem());
            }
        }
        else {
        	InstanceModule thisMod = (InstanceModule) moduleRef.getObject().getInstModRef().getObject();
        	ReferenceItem<ConnectorModule> thisConnectRef = thisMod.getConnectReference();
        	ModInstanceConnectNotFound nf = (ModInstanceConnectNotFound) connectReference.getObject();
        	ConnectorHolder.NotFound holder = new ConnectorHolder.NotFound(nf.getname(), nf.getBaseModuleName());
        	thisConnectRef.addReferenceItem(holder.createReferenceItem());
        }
    }



}
