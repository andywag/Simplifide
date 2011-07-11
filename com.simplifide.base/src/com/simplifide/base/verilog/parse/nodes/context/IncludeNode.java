/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.context;

import com.simplifide.base.basic.struct.ModuleObjectNew;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectBaseItem;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.project.CoreProjectSuite;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.nodes.VerilogRootASTNode;

public class IncludeNode extends TopASTNode{

	private static final long serialVersionUID = 1L;

	
	private String returnFileName(String filename) {
		String[] ustr = filename.split("\"");
		return ustr[1];
	}
	/** @todo : This only handles .v extensions */
	private String parseFileName(String filename) {
		
		String[] astr = filename.split("\"");
		String cont = astr[0];
		if (astr.length > 1) {
			String[] ustr = astr[1].split("\\."); //astr.split("\"");
			cont = ustr[0];
		}
		
		
		return cont + VerilogRootASTNode.CONTEXT_NAME;
		
	}
	
	 /** Method used to find the context of the node, used for completion and navigation operations */
    public ReferenceItem findItemResolveContext(ParseContext context, int pos) {
    	TopASTNode child = this.getFirstASTChild();
    	child = child.getNextASTSibling();
    	String rname = child.getRealText();
    	String fname = this.returnFileName(rname);
    	ReferenceItem<? extends CoreProjectSuite> loc = context.getRefHandler().getGlobalReference();
    	ReferenceLocation rloc = loc.getObject().getFileLocation(fname);
    	if (rloc != null) {
    		ModuleObjectNew oo = new ModuleObjectNew(fname);
    		return oo.createReferenceItemWithLocation(rloc);
    	}
    	return null;
    	
    }
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		
		String filename = child.getRealText();
		String rname = this.parseFileName(filename);
		
		 
		SuperModule smod = (SuperModule) context.getRefHandler().getSuperModuleReference().getObject();
		ModuleObjectBaseItem item = new ModuleObjectBaseItem(rname);
		smod.addParentList(item.createReferenceItem());
		
		 if (context.getPass() != BuildInterface.BUILD_FILE_CONTEXT) {
			ReferenceItem<InstancePackage> item2 = context.getRefHandler().getProjectReference().findReference(rname, ReferenceUtilities.REF_INSTANCE_PACKAGE);
			if (item2 != null) 
				context.getRefHandler().getSearchReference().addReferenceItem(item2);
		}
		
		return null;
	}
}
