package com.simplifide.base.sourcefile.antlr.tok;

import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.doc.HdlDoc;
import com.simplifide.base.core.project.define.DefineObject;
import com.simplifide.base.core.reference.ReferenceItem;


public class TopASTTokenCopy extends TopASTToken{

	private TopASTToken token;
	private TopASTToken realtok;
	private ReferenceItem<DefineObject> defineR;
	
	
	public TopASTTokenCopy(TopASTToken token, TopASTToken realtok) {
		super(token.getType(), token.getText());
		this.token = token;
		this.realtok = realtok;
	}
	
	 public TopASTToken getRealToken() {
	    	return this.realtok;
	 }
	
	 // Questionable Operation
	 public int getPosition() {
		 return this.realtok.getPosition();
	 }
	 
	 public NodePosition getNodePosition() {
	        return new NodePosition(this.getPosition(),this.getPosition() + this.realtok.getLength(),this.getLine(),this.getColumn());
	 }

	 public String getText() {
		 return this.token.getText();
	 }

	public void setRealtok(TopASTToken realtok) {
		this.realtok = realtok;
	}

	public TopASTToken getRealtok() {
		return realtok;
	}
	
	public void setDoc(HdlDoc doc) {
		super.setDoc(doc);
	}

	public void setDefineR(ReferenceItem<DefineObject> defineR) {
		this.defineR = defineR;
	}

	public ReferenceItem<DefineObject> getDefineR() {
		return defineR;
	}


	
	 
	
}
