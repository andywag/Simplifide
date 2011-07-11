/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.vhdl.parse.node.designunits;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.module.InstanceModuleTop;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.PackageModule;
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

public class VhdlPackageDeclarationASTNode extends VhdlTopDeclarationASTNode {
    
    
    
	private static final long serialVersionUID = 1L;

	public VhdlPackageDeclarationASTNode() {super();}
   
    
    private void init() {}
    
    public void resolveContext(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        child = child.getNextASTSibling();
        ModuleObjectBaseItem baseMod = new ModuleObjectBaseItem(child.getRealText());
        
        ReferenceItem instRef = baseMod.findRealReferenceItem(context.getRefHandler().getProjectReference(),ReferenceUtilities.REF_INSTANCE_PACKAGE); 
        ReferenceItem item = baseMod.findRealReferenceItem(instRef,ReferenceUtilities.REF_PACKAGE_MODULE); 
        context.setActiveReference(item);
       // context.getRefHandler().getSearchReference().addObject(item);
    }
    
    
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        

            TopASTNode node = this.getFirstASTChild(); // package
            node = node.getNextASTSibling(); // name
            String packName = node.getRealText();
            
            PackageModule newMod = new PackageModule(packName);
            newMod.setSearchReference(context.getRefHandler().getSearchReference());
            
            
            ReferenceItem item = this.addReferenceObject(context,newMod,node);
            
           
            
            if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
            	if (context.getRefHandler().getSearchReference() != null) {
                    context.getRefHandler().getSearchReference().addObject(item);
            	}
                node = node.getNextASTSibling(); // is
                node = node.getNextASTSibling(); // Declarative Part
                node.generateModule(context);
            }
            // Put the location of the last node for refactoring additions
            newMod.setLastObjectLocation(context.createReferenceLocation(node));
            // Attach the instance module pointer to the package module
            ReferenceItem<InstancePackage> imodref = context.getRefHandler().getProjectReference().findReference(newMod.getname(), ReferenceUtilities.REF_INSTANCE_PACKAGE);
            newMod.setInstancePackage(imodref);
            imodref.getObject().setPackageModuleReference(newMod.createReferenceItem());
            
            if (context.getPass() == BuildInterface.BUILD_FIND_USAGES) {
                node = node.getNextASTSibling(); // end
                node = node.getNextASTSibling(); // package or ident
                if (node instanceof IdentASTNode) {
                    this.checkNodeForFindUsage(context, node, ReferenceUtilities.REF_PACKAGE_MODULE);
                }
                else {
                    node = node.getNextASTSibling();
                    if (node instanceof IdentASTNode) {
                        this.checkNodeForFindUsage(context, node, ReferenceUtilities.REF_PACKAGE_MODULE);
                    }
                }
            }
            
            return newMod;
       
        
        
    }
    
    protected InstanceModuleTop createNewInstanceModule(String name, ParseContext context) {
        return new InstancePackage(name, context.getRefHandler().getProjectReference());
    }
    
    
    
    
    
    
    
    
    
    
}
