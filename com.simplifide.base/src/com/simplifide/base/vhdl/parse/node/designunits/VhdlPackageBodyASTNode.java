/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.designunits;

import com.simplifide.base.basic.struct.ModuleObjectContextHolder;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.module.PackageModuleBody;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.namedec.IdentASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;



/**
 * Created by IntelliJ IDEA.
 * User: Andy Wagner
 * Date: Apr 25, 2004
 * Time: 7:13:49 PM
 * To change this template use File | Settings | File Templates.
 */

// package_body : PACKAGE BODY identifier IS package_body_declarative_part END 
//                (PACKAGE BODY)? ( identifier )? SEMI

public class VhdlPackageBodyASTNode extends VhdlTopDeclarationASTNode {
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VhdlPackageBodyASTNode() {super();}
  
    
    private void init() {}
    
    public void resolveContext(ParseContext context) {
        TopASTNode child = this.getFirstASTChild(); // package
        child = child.getNextASTSibling(); // body
        TopASTNode nameNode = child.getNextASTSibling(); // name
        
        ModuleObjectBaseItem packName = new ModuleObjectBaseItem(nameNode.getRealText());
        
        ReferenceItem<InstancePackage> instRef = context.getRefHandler().findProjectObject(packName,ReferenceUtilities.REF_INSTANCE_PACKAGE);
        ReferenceItem<PackageModule> modRef = packName.findRealReferenceItem(instRef,ReferenceUtilities.REF_PACKAGE_MODULE);
        ReferenceItem<PackageModuleBody> bodyRef = packName.findRealReferenceItem(instRef,ReferenceUtilities.REF_PACKAGE_MODULE_BODY);
        
        context.setActiveReference(bodyRef);
        
        this.updateSearchReference(context, packName.getname());
        /*
        ReferenceItem localRef = this.createCompositeReferenceItem(bodyRef,modRef);
        context.getRefHandler().setLocalReference(localRef);
        if (modRef != null) {
            ReferenceItem searchRef = this.createCompositeReferenceItem(context.getRefHandler().getSearchReference(),
            		modRef.getObject().getSearchReference());
            context.getRefHandler().setSearchReference(searchRef);
        }
		*/
        
        
        
        
        
    }
    
   
    
    private void updateSearchReference(ParseContext context, String modName)
    {
         ModuleObjectBaseItem modBase = new ModuleObjectBaseItem(modName);
         ReferenceItem<InstancePackage> instRef = modBase.findRealReferenceItem(context.getRefHandler().getProjectReference(),ReferenceUtilities.REF_INSTANCE_PACKAGE);
         ReferenceItem<PackageModule> packRef = modBase.findRealReferenceItem(instRef,ReferenceUtilities.REF_PACKAGE_MODULE);
         if (packRef != null)
         {
             PackageModule entity = (PackageModule) packRef.getObject();
             if (entity != null)
             {
                ReferenceItem<ModuleObjectContextHolder> search = (ReferenceItem) context.getRefHandler().getSearchReference();
                if (search != null && search.getObject() != null && 
                	entity != null && entity.getSearchReference() != null) {
                	
                	ModuleObjectContextHolder nsearch = search.getObject().copy();
                	ReferenceItem<ModuleObjectContextHolder> entsR = entity.getSearchReference();
                	nsearch.appendObject(entsR.getObject().getInternal().createReferenceItem());
                    context.getRefHandler().setSearchReference(nsearch.createReferenceItem());
                }

                ReferenceItem local = this.createCompositeReferenceItem(packRef,context.getActiveReference());
                context.getRefHandler().setLocalReference(local);
             }
             else
                com.simplifide.base.basic.util.IDEout.printlnOver("Error Finding Entity modName");
        }
    }   
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context)
    {
        
            TopASTNode node     = this.getFirstASTChild(); // package
            node                = node.getNextASTSibling(); // body
            TopASTNode nameNode = node.getNextASTSibling(); // package name
            node                = nameNode.getNextASTSibling(); // is
            TopASTNode decNode  = node.getNextASTSibling(); // Declarative Part
            
            // Create the Package Module and Add it (Not Sure about the Adding)
            String packName = nameNode.getRealText();
            PackageModuleBody newMod = new PackageModuleBody(packName);
            this.addReferenceObject(context,newMod,node);
            
            ReferenceItem cref = context.getRefHandler().getSearchReference();
            ReferenceItem lref = context.getRefHandler().getLocalReference();
            
            this.updateSearchReference(context,packName);
            
            if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT){
                decNode.generateModule(context);
            }
            ModuleObjectBaseItem findItem = new ModuleObjectBaseItem(newMod.getname());
            ReferenceItem<InstancePackage> instRef = context.getRefHandler().findProjectObject(findItem,ReferenceUtilities.REF_INSTANCE_PACKAGE);
            newMod.setInstancePackage(instRef);
            instRef.getObject().setPackageBodyReference(newMod.createReferenceItem());
            
            if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
                this.handleFindUsages(context, node);
            }
            
            context.getRefHandler().setSearchReference(cref);
            context.getRefHandler().setLocalReference(lref);
            
            newMod.setLastObjectLocation(context.createReferenceLocation(decNode));
            
            return newMod;
        
    }
    
    private void handleFindUsages(ParseContext context, TopASTNode inNode) {
        TopASTNode node = inNode.getNextASTSibling(); // end
        node = node.getNextASTSibling(); // identifier or package
        if (node == null) return;
        
        if (node instanceof IdentASTNode) {
            this.checkNodeForFindUsage(context, node, ReferenceUtilities.REF_PACKAGE_MODULE_BODY);
        }
        else {
            node = node.getNextASTSibling(); // body
            if (node == null) return;
            node = node.getNextASTSibling(); // identifier or semi
            if (node instanceof IdentASTNode) {
                this.checkNodeForFindUsage(context, node, ReferenceUtilities.REF_PACKAGE_MODULE_BODY);
            }
            
        }
        
    }
    
    
    protected InstanceModuleTop createNewInstanceModule(String name, ParseContext context) {
        return new InstancePackage(name, context.getRefHandler().getProjectReference());
    }
    

    
   
    
    
    
    
    
    
    
}
