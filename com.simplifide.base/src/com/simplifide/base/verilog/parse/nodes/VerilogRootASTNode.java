/*
 * VerilogRootASTNode.java
 *
 * Created on April 21, 2007, 11:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.simplifide.base.verilog.parse.nodes;

import com.simplifide.base.basic.struct.ModuleObjectContextHolder;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.project.CoreProjectBasic;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.RootASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.project.VerilogStandardPackage;



/**
 *
 * @author Andy
 */
public class VerilogRootASTNode extends RootASTNode {
    
	public static String CONTEXT_NAME = "_Context";
	
    /** Creates a new instance of VerilogRootASTNode */
    public VerilogRootASTNode() {}
    
  
    public void resolveContext(ParseContext context) {
    	SuperModule sup = (SuperModule) context.getRefHandler().getSuperModuleReference().getObject();
    	context.getRefHandler().setSearchReference(sup.getContextRef());
    	context.getRefHandler().setFileReference(sup.getFileContextRef());
    	this.createFileContext(context);
    }
    
    private void createFileContext(ParseContext context) {
    	SuperModule sup = context.getRefHandler().getSuperModuleReference().getObject();
    	
    	ModuleObjectContextHolder holder = new ModuleObjectContextHolder(sup.getNameWithoutExtension() + "inc",
    			context.getRefHandler().getGlobalReference());
    	holder.setStandardReference(VerilogStandardPackage.getInstance().createReferenceItem());
    	
    	ReferenceItem<CoreProjectBasic> projR = context.getRefHandler().getProjectReference();
    	if (projR != null && projR.getObject() != null) {
    		InstancePackage pack = projR.getObject().getProjectContext();
        	
        	ReferenceItem packR = pack.getPackageModuleReference();
        	context.getRefHandler().setFileReference(packR);
        	context.getRefHandler().setActiveReference(packR);
    	}
    	
    	//ModuleObjectCompositeHolder comp = ModuleObjectCompositeHolder.dualHolder("", holder.createReferenceItem(), packR);
    	
    	context.getRefHandler().setSearchReference(holder.createReferenceItem());
    	sup.setContextRef(holder.createReferenceItem());
    	

    	/*
    	String packName = sup.getNameWithoutExtension() + CONTEXT_NAME;
    	ReferenceItem<InstancePackage> imodref = context.getRefHandler().getProjectReference().
    	findReference(packName, ReferenceUtilities.REF_INSTANCE_PACKAGE);
    	 
        if (imodref == null) {
            InstancePackage instanceModule = new VerilogInstancePackage(packName,context.getRefHandler().getProjectReference());
            imodref = context.getRefHandler().getProjectReference().addModuleObject(instanceModule, context.createReferenceLocation(this));
        }
    	
    	PackageModule module = new PackageModule(packName);
    	ReferenceItem<PackageModule> moduleRef = module.createReferenceItemWithLocation(context.createReferenceLocation(this));
    	imodref.getObject().setPackageModuleReference(moduleRef);
    	sup.addReferenceItem(moduleRef);
    	context.getRefHandler().setFileReference(moduleRef);
    	sup.setFileContextRef(moduleRef);
    	context.setActiveReference(moduleRef);
    	*/
    	
    	
    }
    
    public TopObjectBase generateModuleSmallNew(ParseContext context) {
        TopASTNode child = this.getFirstASTChild();
        context.setSearchMode(ParseContext.SEARCHREFERENCECONTEXT);
        this.createFileContext(context);
        while (child != null) {
            child.generateModule(context);
            child = child.getNextASTSibling();
        }
        
        return null;
    }
}
