/*******************************************************************************
 * Copyright (c) 2007/2008 Simplifide, LLC.
 * All Right Reserved.
 ******************************************************************************/
package com.simplifide.base.verilog.parse.nodes.generate;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.generate.GenerateStatement;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;

/* label_colon generation_scheme
 GENERATE
 generate_declarative_part
 generate_statement_part
 END GENERATE ( identifier )? SEMI
 */
public class GenerateNode extends TopASTNodeGeneric<ReferenceItem<NoSortList>> {

	public GenerateNode() {}

	 public TopASTNode formatBase() {return this;}
		public void format(FormatPosition position) {
			position.setIndent(0);
			TopASTNode child = this.getFirstASTChild();  // Generate
			TopASTNode head = child.getNextASTSibling(); // Head
			TopASTNode body = head.getNextASTSibling();  // Body
		
			NodePosition pos = head.getPosition();
			FormatPosition newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
			newPosition.setIndent(FormatSupport.getInstance().getIndent());
			head.format(newPosition);
		
			if (this.getNumberOfChildren() > 3) {
				pos = body.getPosition();
				newPosition = position.addNewPosition(pos.getStartPos(), pos.getEndPos());
				newPosition.setIndent(FormatSupport.getInstance().getIndent());
				body.format(newPosition);
			}
			
			
		}
	
	public ReferenceItem<NoSortList> createObjectSmall(ParseContext context) {
		 TopASTNode child = this.getFirstASTChild();
		child = child.getNextASTSibling();
		//NoSortList retList = new NoSortList();
		while (child != null) {
			if (child.getNextASTSibling() == null) break;
			ModuleObject ref = (ModuleObject) child.generateModule(context);
			this.addObjects(context.getActiveReference(), ref);
			
			child = child.getNextASTSibling();
		}
		//return retList.createReferenceItem(); 
		return null;
	}
	
	
	public boolean canFold() {
		return true;
	}

}
