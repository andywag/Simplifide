package com.simplifide.base.vhdl.parse.node.function;

import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.newfunction.FunctionDeclaration;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.tok.TopASTToken;

public class FunctionDeclarationTopNode extends TopASTNodeGeneric<ReferenceItem<FunctionDeclaration>>{

	protected void handleDoc(FunctionDeclaration dec) {
	    TopASTToken tok = getFirstLeafNode().getToken();
	    HdlDoc doc = tok.getDoc();
		dec.setDoc(doc);
		dec.updateHdlDoc(dec);
	}
	
}
