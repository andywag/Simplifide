/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.segment.wrap;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.basic.struct.TopObjectBase;
import com.simplifide.base.core.finder.ModuleObjectExpressionItem;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.core.segment.basic.assign.SequenceStatements;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;


// seq_block : pre_block_nameQ "begin" seq_block_nameQ state_list "end"

public class BeginEndNode extends TopASTNode{

	
	public BeginEndNode() {}
	
	public boolean canFold() {return true;}
	
	public void format(FormatPosition position) {
		TopASTNode child = this.getFirstASTChild();  // Prefix
		TopASTNode head = child.getNextASTSibling(); // Begin
		TopASTNode seq = head.getNextASTSibling();  // Seq
		TopASTNode body = seq.getNextASTSibling();  // Seq
		
		NodePosition pos = this.getPosition();
		if (position.getStart() == pos.getStartPos() &&
			position.getStop()  == pos.getEndPos()) {
			NodePosition bpos = body.getPosition();
			position.setStart(bpos.getStartPos());
			position.setStop(bpos.getEndPos());
			body.format(position);
		}
		else {
			NodePosition bpos = body.getPosition();
			FormatPosition upos = position.addNewPosition(bpos.getStartPos(), bpos.getEndPos());
			upos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
			body.format(upos);
		}
	}
	
	public TopObjectBase generateModuleSmallNew(ParseContext context) {
		
		NameBlockPrefixNode preNode = (NameBlockPrefixNode) this.getFirstASTChild(); // Pre Block Name Q
		TopASTNode child = preNode.getNextASTSibling(); // "begin" 
		NameBlockPrefixNode postNode = (NameBlockPrefixNode) child.getNextASTSibling(); // "seq block name Q"
		StateList segmentNode = (StateList) postNode.getNextASTSibling(); // State List
		
		String preName = preNode.getBlockName();
		if (preName == null) preName = postNode.getBlockName();
		
		ReferenceItem<SequenceStatements> statementsR = segmentNode.createObject(context);
		if (preName != null) statementsR.changeName(preName);
	
		return statementsR;
	}
	
	/** TODO Function Calls Ignored */
	public static class StateList extends TopASTNodeGeneric<ReferenceItem<SequenceStatements>> {
		
		private void convertReturnObject(SequenceStatements list, ModuleObject object) {
			if (object instanceof ReferenceItem) {
				list.addObject(object);
			}
			else if (object instanceof NoSortList) {
				NoSortList<ModuleObject> l = (NoSortList<ModuleObject>) object;
				for (ReferenceItem ref : l.getGenericSelfList()) {
					list.addObject(ref);
				}
			}
			else if (object instanceof ModuleObjectExpressionItem) { // Function Call Ignore for Now
				
			}
		}
		
		public ReferenceItem<SequenceStatements> createObjectSmall(ParseContext context) {
			SequenceStatements<ModuleObject> list = new SequenceStatements<ModuleObject>();
			TopASTNode child = this.getFirstASTChild();
			while (child != null) {
				ModuleObject item = (ModuleObject) child.generateModule(context);
				this.convertReturnObject(list, item);
				//list.addObject(item);
				child = child.getNextASTSibling();
			}
			return list.createReferenceItem();
		}
	}
	
}
