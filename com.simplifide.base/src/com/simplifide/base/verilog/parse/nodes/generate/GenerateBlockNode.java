package com.simplifide.base.verilog.parse.nodes.generate;

import com.simplifide.base.basic.struct.ModuleObject;
import com.simplifide.base.basic.struct.NoSortList;
import com.simplifide.base.basic.struct.NodePosition;
import com.simplifide.base.core.generate.GenerateBlock;
import com.simplifide.base.core.reference.ReferenceItem;
import com.simplifide.base.sourcefile.antlr.FormatSupport;
import com.simplifide.base.sourcefile.antlr.node.FormatPosition;
import com.simplifide.base.sourcefile.antlr.node.TopASTNode;
import com.simplifide.base.sourcefile.antlr.node.TopASTNodeGeneric;
import com.simplifide.base.sourcefile.antlr.parse.ParseContext;
import com.simplifide.base.verilog.parse.grammar.system.SystemVerilogTokenTypes;
import com.simplifide.base.verilog.parse.nodes.generate.GenerateItemNode.ForHead;

// generate_module_block : "begin" colon_identifierq (generate_module_item)* "end" colon_identifierq


public class GenerateBlockNode extends TopASTNodeGeneric<ReferenceItem>{

	
	public String getBlockName() {
		return "generate";
	}
	public static class Block extends GenerateBlockNode {
		
		public void format(FormatPosition position) {
			TopASTNode begin = this.getFirstASTChild();   // begin
			TopASTNode cid   = begin.getNextASTSibling(); // cid
			TopASTNode first  = cid.getNextASTSibling();  
			TopASTNode safe = first;
			int start = first.getPosition().getStartPos();
			int stop = 0;
			while (first != null && first.getType() != SystemVerilogTokenTypes.LITERAL_end) {
				stop = first.getPosition().getEndPos();
				first = first.getNextASTSibling();
			}
			
			FormatPosition pos = position.addNewPosition(start,stop);
			pos.setIndent(position.getIndent() + FormatSupport.getInstance().getIndent());
			first = safe;
			while (first != null && first.getType() != SystemVerilogTokenTypes.LITERAL_end) {
				first.format(pos);
				first = first.getNextASTSibling();
			}
			
		}
		
		public String getBlockName() {
			TopASTNode child = this.getFirstASTChild();
			ColonIdentifierQNode col = (ColonIdentifierQNode) child.getNextASTSibling();
			
			return col.getName();
		}
		
		public ReferenceItem createObjectSmall(ParseContext context) {
			TopASTNode child = this.getFirstASTChild(); // begin
			TopASTNode identNode = child.getNextASTSibling(); // colon_identifierq
			ReferenceItem ref = (ReferenceItem) identNode.generateModule(context);
			String refName = "generate";
			if (ref != null) refName = ref.getname();
			//NoSortList nlist = new NoSortList(refName);
			//ReferenceItem nlistRef = nlist.createReferenceItem();
			GenerateBlock gen = new GenerateBlock(refName);
			ReferenceItem genR = gen.createReferenceItemWithLocation(context.createReferenceLocation(this));
			
			child = identNode.getNextASTSibling();
			while (child != null) {
				if (child.getToken() != null && child.getToken().getType() == SystemVerilogTokenTypes.LITERAL_end) break;
				ModuleObject newRef =  (ModuleObject) child.generateModule(context);
				this.addObjects(genR, newRef);
				child = child.getNextASTSibling();
			}
			return genR;
		}
	}
	
	
	
	// generate_module_block_ident : identifier COLON generate_module_block

	public static class Ident extends GenerateBlockNode{
		
		public String getBlockName() {
			return "generate";
		}
		
		public boolean canFold() {
			return true;
		}
	
		public ReferenceItem createObjectSmall(ParseContext context) {
			TopASTNode identNode = this.getFirstASTChild(); // Identifider
			TopASTNode child = identNode.getNextASTSibling(); // Colon  
			TopASTNode blockNode = child.getNextASTSibling(); // Block Node
			
			ReferenceItem blockRef = (ReferenceItem) blockNode.generateModule(context);
			blockRef.changeName(identNode.getRealText());
			return blockRef;
		}
		
	}
	
}
