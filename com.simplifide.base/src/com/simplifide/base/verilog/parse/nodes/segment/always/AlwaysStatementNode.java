/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.always;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.project.BuildInterface;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.process.ProcessBody;
import com.simplifide.base.core.segment.basic.process.ProcessStatement;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.segment.process.ProcessStatementASTNode;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.core.segments.AlwaysBlock;


// always_statement : "always" always_head statement
public class AlwaysStatementNode extends ProcessStatementASTNode{

	
	public boolean canFold() {return true;}
	
	
	
	private ReferenceItem<ProcessBody> createBody(ParseContext context, TopASTNode node) {
		ProcessBody body = new ProcessBody("");
		ReferenceItem item = (ReferenceItem) node.generateModule(context);
		if (item == null) return null;
		
		if (item.getObject() instanceof NoSortList) {
			if (item.getname() != null) body.setname(item.getname());
			NoSortList<ModuleObject> list = (NoSortList<ModuleObject>) item.getObject();
			for (ReferenceItem nitem : list.getGenericSelfList()) {
				body.addObject(nitem);
			}
		}
		else {
			body.addObject(item);
		}
		return body.createReferenceItem();
		
	}
	
	public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();  // Always
		TopASTNode head = child.getNextASTSibling(); // Head
		TopASTNode body = head.getNextASTSibling();  // Body
		
		NodePosition pos = body.getPosition();
		FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
		newPosition.setIndent(FormatSupport.getInstance().getIndent());
		body.format(newPosition);
	}
	
	public  ReferenceItem<ProcessStatement> createObjectSmall(ParseContext context) {
		
    	if (context.getPass() == BuildInterface.BUILD_FILE_CLOSED) return null;

		TopASTNode child = this.getFirstASTChild();  // Always
		TopASTNode head = child.getNextASTSibling(); // Head
		TopASTNode body = head.getNextASTSibling();  // Body
		
		ReferenceItem<NoSortList> headRef = (ReferenceItem<NoSortList>) head.generateModule(context);
		ReferenceItem<ProcessBody> bodyRef = this.createBody(context, body);
		
		
		AlwaysBlock process = new AlwaysBlock("always",headRef,bodyRef);
		String outName = process.getOutputListName();
		process.setname("always" + outName);
		if (bodyRef != null && bodyRef.getname() != null && !(bodyRef.getname().equalsIgnoreCase(""))) {
			process.setBlockName(bodyRef.getname());
		}
		
		this.handleVariables(context, process, null);
		
	    //ReferenceItem<ModuleObjectWithList> depList = process.getDependants();
	    //ReferenceItem<ModuleObjectWithList> outList = process.getOutputs();
		
		return process.createReferenceItemWithLocation(context.createReferenceLocation(child));
		
	}
	
}
