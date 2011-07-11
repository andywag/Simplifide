package com.simplifide.base.verilog.parse.nodes.pack;

import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.module.InstancePackage;
import com.simplifide.base.core.module.PackageModule;
import com.simplifide.base.core.module.SuperModule;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceLocation;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// package_declaration : "package" package_identifier SEMI package_body "endpackage" ( COLON package_identifier)?

public class PackageDeclarationNode extends TopASTNode{

    public void resolveContext(ParseContext context) {
    	TopASTNode node = this.getFirstASTChild(); // package
		TopASTNode nameNode = node.getNextASTSibling(); // package_identifier
		
		String packName = nameNode.getRealText();
		ReferenceItem<InstancePackage> imodR = context.getRefHandler().getProjectReference().findReference(packName, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
		ReferenceItem<PackageModule> packR = imodR.getObject().getPackageModuleReference();
		
		context.setActiveReference(packR);
    }

	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode node = this.getFirstASTChild(); // package
		TopASTNode nameNode = node.getNextASTSibling(); // package_identifier
		node = nameNode.getNextASTSibling(); // semi
		TopASTNode bodyNode = node.getNextASTSibling();
	
		String packName = nameNode.getRealText();
		ReferenceLocation loc = context.createReferenceLocation(nameNode);
		
		ReferenceItem<InstancePackage> imodR = context.getRefHandler().getProjectReference().findReference(packName, ReferenceUtilities.REF_INSTANCE_MODULE_TOP);
		if (imodR == null) {
			InstancePackage instanceModule = new InstancePackage(packName,context.getRefHandler().getProjectReference());
            imodR = context.getRefHandler().getProjectReference().addModuleObject(instanceModule, loc);
		}
		
		PackageModule pack = new PackageModule(packName);
		ReferenceItem<PackageModule> packR = pack.createReferenceItemWithLocation(context.createReferenceLocation(nameNode));
		imodR.getObject().setPackageModuleReference(packR);

		context.setActiveReference(packR);
		
		bodyNode.generateModule(context);
		
		SuperModule smod = context.getRefHandler().getSuperModuleReference().getObject();
		smod.addReferenceItem(packR);
		
		this.handleDoc(pack);
		
		return null;
	}

	
	
}
