package com.simplifide.base.verilog.parse.nodes.pack;

import com.simplifide.base.basic.struct.ModuleObjectContextHolder;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.reference.ReferenceUtilities;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

// "import" package_import_item (COMMA  package_import_item )* SEMI
public class PackageImportNode extends TopASTNode{

	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		TopASTNode child = this.getFirstASTChild(); // import
		child = child.getNextASTSibling(); // import_item
		ReferenceItem<ModuleObjectContextHolder> contR = context.getRefHandler().getSearchReference();
		ModuleObjectContextHolder cont = null;
		if (contR != null) cont = contR.getObject();
		while (child != null) {
			ReferenceItem ref = (ReferenceItem) child.generateModule(context);
			if (ref != null) {
				cont.appendObject(ref);
			}
			if (child != null) child = child.getNextASTSibling(); // COMMA
			if (child != null) child = child.getNextASTSibling();
		}
		return null;
	}
	
	// package_import_item : package_identifier DOUBLECOLON (identifier | STAR)

	public static class Item extends TopASTNode {
		public TopObjectBase generateModuleSmallNew(ParseContext context) {
			TopASTNode packIdentNode = this.getFirstASTChild();
			TopASTNode child = packIdentNode.getNextASTSibling(); // COLON
			TopASTNode itemNode = child.getNextASTSibling();
			
			ReferenceItem ref = (ReferenceItem) packIdentNode.generateSearchTypeNew(context, ParseContext.SEARCHREFERENCEPROJECTANDLIBRARY, ReferenceUtilities.REF_INSTANCE_PACKAGE);
			return ref;
		}
	}
	
}
