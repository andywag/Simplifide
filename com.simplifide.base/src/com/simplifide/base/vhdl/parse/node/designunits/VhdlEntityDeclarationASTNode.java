/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.designunits;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.instance.Entity;
import com.simplifide.base.core.instance.ModInstanceDefault;
import com.simplifide.base.core.module.InstanceModule;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.IdentASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.sourcefile.antlr.parse.ParseContextUsages;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

//entity_declaration
// : ENTITY identifier IS entity_header entity_declarative_part ent_dec1 ent_dec2

public class VhdlEntityDeclarationASTNode extends VhdlTopDeclarationASTNode {
    
    
    
    private static final long serialVersionUID = 1L;

    public VhdlEntityDeclarationASTNode() {super();}
    
    public TopASTNode formatBase() {return this;}
    
    public void resolveContext(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        ModuleObjectBaseItem item = new ModuleObjectBaseItem(child.getRealText());
        ReferenceItem<InstanceModule> instRef = context.getRefHandler().findProjectObject(item,ReferenceUtilities.REF_INSTANCE_MODULE);
        ReferenceItem<Entity> entRef = item.findRealReferenceItem(instRef,ReferenceUtilities.REF_ENTITY);  
        
        context.setActiveReference(entRef);
        
    }
    
    
    public boolean canFold() {return false;}
    public String getFoldName() {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
        return "entity " + child.getRealText();
    }
    
    
    private ReferenceLocation getInstanceLocation(ParseContext context) {
    	// This is really kind of a kludge because this node contains other elements as well
        // ModInstanceDefault will contain a pointer to the entity header
        ReferenceLocation loc = context.createReferenceLocation(this);
        TopASTNode child = this.getFirstASTChild().getNextASTSibling().getNextASTSibling().getNextASTSibling().getNextASTSibling();
        NodePosition pos = child.getPosition();
        if (pos != null) {
        	int len = child.getPosition().getLength();
            len += child.getNextASTSibling().getPosition().getLength();
            len += child.getNextASTSibling().getNextASTSibling().getPosition().getLength();
            loc.setLength(loc.getLength() - len);
        }
        
        return loc;

    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {

            TopASTNode node = this.getFirstASTChild(); // entitity
            TopASTNode nameNode = node.getNextASTSibling(); // name
            node = nameNode.getNextASTSibling(); // is
            VhdlEntityHeaderASTNode headerNode = (VhdlEntityHeaderASTNode) node.getNextASTSibling(); // Header (Ports) ...
            TopASTNode decNode = headerNode.getNextASTSibling();
            TopASTNode decNode1 = decNode.getNextASTSibling();
            TopASTNode decNode2 = decNode1.getNextASTSibling();
            
            Entity ent = new Entity(nameNode.getRealText());
            ReferenceItem entityRef = this.addReferenceObject(context,ent,nameNode);
            
            // Used for Architecture to add to context (Should be Changed */
            ent.setSearchReference(context.getRefHandler().getSearchReference());
            
            
                      
            // Used to find the location of the end points for parameters and ports
            //ent.setLastPortLocation(headerNode.getLastPortLocation(context));
            //ent.setLastGenericLocation(headerNode.getLastGenericLocation(context));
            
            // Don't build internal entity if it is just a file scan
            //if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
            ReferenceItem<ModInstanceDefault> def = (ReferenceItem) headerNode.generateModule(context);
            def.setLocation(context.createReferenceLocation(this));

            ent.setConnectRef(def);
            def.getObject().setname(ent.getname());
            //}
            ReferenceItem<InstanceModule> imodref = context.getRefHandler().getProjectReference().findReference(ent.getname(), ReferenceUtilities.REF_INSTANCE_MODULE);
            ent.setInstanceModRef(imodref);
            
            context.setActiveReference(ent.createReferenceItem());
            
            
            // Don't Build Declaration Section if it is a file scan
            if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
                context.getRefHandler().setSecondaryReference(ent.getDecReference());
                decNode.generateModule(context); // Entity Declaration Section
            }
            // Used to Find the End Name of the Function
            if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
               decNode1.generateModule(context);
               decNode2.generateModule(context);
            }
            return ent;
    }
    
    protected InstanceModuleTop createNewInstanceModule(String name, ParseContext context) {
        return new InstanceModule(name, context.getRefHandler().getProjectReference());
    }
    
    
    //END ( ENTITY )? ( identifier )? SEMI
    /** This class is used only to find the entdec2 item */
    public static class EntDec2 extends TopASTNode {
        
        
        
        public TopObjectBase generateModuleSmallNew(ParseContext context) {
            TopASTNode child = this.getFirstASTChild();
            child = child.getNextASTSibling();
            if (child instanceof IdentASTNode) {
                this.checkNodeForFindUsage((ParseContextUsages) context,child, ReferenceUtilities.REF_ENTITY);
            }
            else {
                child = child.getNextASTSibling();
                if (child instanceof IdentASTNode) {
                    this.checkNodeForFindUsage((ParseContextUsages) context,child, ReferenceUtilities.REF_ENTITY);
                }
            }
            return null;
        }
    }
    
    
    
    
    
    
    
}
